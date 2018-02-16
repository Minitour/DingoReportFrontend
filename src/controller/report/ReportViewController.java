package controller.report;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import network.APIManager;
import ui.UIListViewCell;
import ui.UIView;
import ui.UIViewController;
import view.DialogView;
import view.DynamicDialog;
import view.cells.CarOwnerCellView;
import view.cells.ViolationViewCell;
import view.forms.ViolationFormView;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Antonio Zaitoun on 18/12/2017.
 */
public class ReportViewController extends UIViewController {

    static Map<Integer, ViolationType> violationTypes;
    static {
        violationTypes = new HashMap<>();
        //TODO: load violations from server
    }

    private Vehicle vehicle;
    private Report report;

    private StackPane parentView;

    @FXML
    private Label vd_licensePlate;

    @FXML
    private Label vd_carModel;

    @FXML
    private Label vd_carColor;

    @FXML
    private ListView<VehicleOwner> carOwnersListView;

    @FXML
    private ListView<Violation> violationListView;

    @FXML
    private Button violationAdd;

    @FXML
    private Button positive;

    @FXML
    private Button negative;

    @FXML
    private TextArea description;

    @FXML
    private VBox violationHbox;

    @FXML
    private Label form_title;

    @FXML
    private Label form_subtitle;

    @FXML
    private Pane car_owners_container;

    @FXML
    private Pane hbox_parent;

    private CancelCallback canceCallback;


    public ReportViewController() {
        super("/resources/xml/controller_report.fxml");
    }

    public ReportViewController(Report report){
        this();
        setReport(report);
    }

    public ReportViewController(Vehicle vehicle){
        this();
        setVehicle(vehicle);
    }

    @Override
    public void viewWillLoad(ResourceBundle bundle) {
        super.viewWillLoad(bundle);

        carOwnersListView.setCellFactory(param-> new CarOwnerCell());
        violationListView.setCellFactory(param -> new ViolationCell());

        negative.setOnAction(event->{
            if(canceCallback != null)
                canceCallback.onCancel();
        });

        violationAdd.setOnAction(event -> createDialog(null));

        violationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                Violation current = violationListView.getItems().get(violationListView.getSelectionModel().getSelectedIndex());
                createDialog(current);
                Platform.runLater(()->violationListView.getSelectionModel().clearSelection());
            }
        });
    }

    private void createDialog(Violation violation){
        ViolationFormView violationFormView = new ViolationFormView(violation);
        DynamicDialog dialogView = new DynamicDialog(violationFormView);
        dialogView.setTitle("Evidence View");
        dialogView.setMessage("The violation");
        dialogView.getPostiveButton().setText("Close");
        dialogView.remove(dialogView.getNegativeButton());
        dialogView.show(this.view);

    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;

        vd_licensePlate.setText(vehicle.getLicensePlate());
        vd_carColor.setText(vehicle.getColorHEX());
        vd_carModel.setText(vehicle.getModel().getName());

        hbox_parent.getChildren().remove(car_owners_container);
    }

    public void setReport(Report report) {

        if(report == null)
            return;

        this.report = report;
        //load data from report to UI.

        form_title.setText("Report #"+report.getReportNum() );
        form_subtitle.setText(report.getIncidentDate().toLocaleString());

        description.setEditable(false);

        String desc = report.getDescription();
        if (desc != null) {
            description.setText(desc);
            description.setWrapText(true);
        }

        positive.setVisible(false);
        violationHbox.getChildren().remove(violationAdd);
        negative.setText("Close");

        Vehicle vehicle = report.getVehicle();
        if(vehicle != null){
            vd_licensePlate.setText(vehicle.getLicensePlate());
            vd_carColor.setText(vehicle.getColorHEX());
            vd_carModel.setText(vehicle.getModel().getName());

            Collection<VehicleOwner> owners = vehicle.getOwners();
            if(owners != null)
                carOwnersListView.getItems().addAll(owners);
            else
                hbox_parent.getChildren().remove(car_owners_container);
        }

        List<Violation> violations = report.getViolations();

        if(violations != null)
            violationListView.getItems().addAll(violations);

        violationAdd.setVisible(false);

    }

    public StackPane getParentView() {
        return parentView;
    }

    public void setParentView(StackPane parentView) {
        this.parentView = parentView;
    }

    public void setCanceCallback(CancelCallback canceCallback) {
        this.canceCallback = canceCallback;
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @FunctionalInterface
    public interface CancelCallback {
        void onCancel();
    }

    private void onViolationAdded(Violation violation){
        if(violation != null)
            violationListView
                    .getItems()
                    .add(violation);
    }


    /**
     * Custom inner Classes
     */
    static class CarOwnerCell extends UIListViewCell<VehicleOwner,UIView>{

        @Override
        public UIView load(VehicleOwner item) {
            return new CarOwnerCellView().setVehicleOwner(item);
        }
    }

    static class ViolationCell extends UIListViewCell<Violation,UIView>{

        @Override
        public UIView load(Violation item) {
            return new ViolationViewCell().setViolation(item);
        }
    }
}
