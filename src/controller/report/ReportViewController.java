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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
        APIManager.getInstance().getViolationTypes((res,violationTypes,ex)->{
            if(ex == null && res.isOK()){
                DialogView dialogView = new DynamicDialog(new ViolationFormView(violationTypes));
                dialogView.show(this.view);
            }
        });

    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;

        vd_licensePlate.setText(vehicle.getLicensePlate());
        vd_carColor.setText(vehicle.getColorHEX());
        vd_carModel.setText(vehicle.getModel().getName());

        //TODO: get vehicle owners from dingo pro
        new Thread(()->{
            List<VehicleOwner> owners = null;//getOwners(vehicle);
            Platform.runLater(()->{
                if(owners != null){
                    for(VehicleOwner owner : owners){
                        carOwnersListView.getItems().add(owner);
                    }
                }
            });
        }).start();

//        Violation violation = new Violation();
//        violation.setDecision(Decision.UNDETERMINED);
//        violation.setDescription("Just testing");
//        violation.setType(new ViolationType("testing","testing2",0,0,false));
//        violationListView.getItems().add(violation);


    }

    public void setReport(Report report) {

        if(report == null)
            return;

        this.report = report;
        //load data from report to UI.

        String desc = report.getDescription();
        if (desc != null) {
            description.setText(desc);
            description.setWrapText(true);
        }

        violationListView.getItems().addAll(report.getViolations());

        positive.setVisible(false);
        violationHbox.getChildren().remove(violationAdd);
        negative.setText("Close");

        List<VehicleOwner> owners = null;//getOwners(report);
        if(owners != null)
            carOwnersListView.getItems().addAll(owners);

        List<Violation> violations = null;//getViolations(report);
        if(violations != null)
            violationListView.getItems().addAll(violations);

        Vehicle vehicle = null;//getVehicle(report);

        if(vehicle != null){
            vd_licensePlate.setText(vehicle.getLicensePlate());
            vd_carColor.setText(vehicle.getColorHEX());
            vd_carModel.setText(vehicle.getModel().getName());
        }
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
