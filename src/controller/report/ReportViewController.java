package controller.report;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
import network.APIManager;
import network.AutoSignIn;
import network.Callbacks;
import network.ServerResponse;
import ui.UIViewController;
import view.DialogView;
import view.DynamicDialog;
import view.cells.CarOwnerCell;
import view.cells.ViolationCell;
import view.forms.ViolationFormView;

import javax.security.auth.callback.Callback;
import java.util.*;

/**
 * Created by Antonio Zaitoun on 18/12/2017.
 */
public class ReportViewController extends UIViewController {

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
    private Label v_name;

    @FXML
    private Label v_email;

    @FXML
    private Label v_number;

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
        dialogView.delegate(new DynamicDialog.DialogDelegate() {
            @Override
            public boolean onDone(DynamicDialog dialog) {
                violationFormView.reset();
                return true;
            }
        });


        //If current user is officer, show "make decision" option
        if(AutoSignIn.ROLE_ID == 1 || AutoSignIn.ROLE_ID == 2){
            dialogView.getNaturalButton().setText("Make Decision");
            dialogView.setNaturalEventHandler(event -> makeDecisionClicked(event,violation));
            Officer officer = new Officer(AutoSignIn.ID);
            if(violation.getDecisions().contains(new Decision(officer))){
                dialogView.getNaturalButton().setDisable(true);
                dialogView.getNaturalButton().setText("Voted");
            }
        }

        dialogView.show(this.view);

    }

    private void makeDecisionClicked(Event event1,Violation violation){

        Button button = (Button) event1.getTarget();


        DialogView dialogView = new DialogView();
        dialogView.setTitle("Make Decision");
        dialogView.setMessage("Do you find this violation to be valid?");

        Decision decision = new Decision(0);
        decision.setViolation(violation);
        Callbacks.General callback = (response, exception) -> {
            button.setDisable(true);
            dialogView.close();
            violation.getDecisions().add(new Decision(new Officer(AutoSignIn.ID)));
        };

        dialogView.setPostiveEventHandler(event -> {
            decision.setDecision(1);
            APIManager.getInstance().makeDecision(decision,callback);
            dialogView.getNaturalButton().setDisable(true);
            dialogView.getPostiveButton().setDisable(true);
            dialogView.getNegativeButton().setDisable(true);
        });

        dialogView.setNegativeEventHandler(event -> {
            decision.setDecision(0);
            APIManager.getInstance().makeDecision(decision,callback);
            dialogView.getNaturalButton().setDisable(true);
            dialogView.getPostiveButton().setDisable(true);
            dialogView.getNegativeButton().setDisable(true);
        });

        dialogView.setNaturalEventHandler(event -> {
            dialogView.close();
        });

        dialogView.getPostiveButton().setText("Yes");
        dialogView.getNegativeButton().setText("No");
        dialogView.getNaturalButton().setText("Cancel");

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

        Volunteer volunteer = report.getVolunteer();
        if(volunteer != null){
            v_name.setText(volunteer.getName());
            v_email.setText(volunteer.getEMAIL());
            v_number.setText(volunteer.getPhone());
        }

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
}
