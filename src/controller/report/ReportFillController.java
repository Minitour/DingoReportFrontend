package controller.report;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import network.APIManager;
import ui.UIViewController;
import view.DialogView;
import view.DynamicDialog;
import view.cells.ViolationCell;
import view.forms.ViolationFormView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created By Tony on 16/02/2018
 */
public class ReportFillController extends UIViewController {

    @FXML
    private Label form_title;

    @FXML
    private Label form_subtitle;

    @FXML
    private TextField lpField;

    @FXML
    private TextField coField;

    @FXML
    private ComboBox<VehicleModel> modelCombo;

    @FXML
    private TextArea description;

    @FXML
    private Button violationAdd;

    @FXML
    private Button positive;

    @FXML
    private Button negative;

    @FXML
    private ListView<Violation> violationListView;

    private List<ViolationType> violationTypes = new ArrayList<>();

    public ReportFillController() {
        super("/resources/xml/controller_report_fill.fxml");
        setup();
    }

    private void setup(){
        positive.setText("Submit");
        negative.setText("Clear");

        negative.setOnAction(event -> clear());
        positive.setOnAction(event -> submit());

        form_subtitle.setText(new Date().toLocaleString());

        violationListView.setCellFactory(param -> new ViolationCell());

        violationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                Violation current = violationListView.getItems().get(violationListView.getSelectionModel().getSelectedIndex());
                createViolationViewDialog(current);
                Platform.runLater(()->violationListView.getSelectionModel().clearSelection());
            }
        });

        violationAdd.setOnAction(event -> {
            final ViolationFormView violationFormView = new ViolationFormView(violationTypes);
            DynamicDialog dialog = new DynamicDialog(violationFormView);
            dialog.setTitle("Add Violation");
            dialog.setMessage("Add a new violation to the report.");
            dialog.getPostiveButton().setText("Add");
            dialog.getNegativeButton().setText("Cancel");
            DynamicDialog.DialogDelegate dialogDelegate = new DynamicDialog.DialogDelegate() {
                @Override
                public boolean onDone(DynamicDialog dialog) {
                    if(!violationFormView.isValid())
                        return false;
                    //add violation to listview
                    Violation violation = violationFormView.getViolation();
                    violationListView.getItems().add(violation);

                    violationFormView.reset();
                    return true;
                }

                @Override
                public void onCancel(DynamicDialog dialog) {
                    violationFormView.reset();
                }
            };
            dialog.delegate(dialogDelegate);
            dialog.show(view);
        });

        APIManager.getInstance().getVehicleModels((response, vehicleModels, exception) ->
                modelCombo.getItems().addAll(vehicleModels));

        APIManager.getInstance().getViolationTypes((response, violationsTypes, exception) ->
                violationTypes.addAll(violationsTypes));

    }

    private void clear() {
        lpField.setText(null);
        coField.setText(null);
        modelCombo.getSelectionModel().clearSelection();
        description.setText(null);
        violationListView.getItems().clear();
    }

    private void submit(){
        if(isValid()){
            Report report = makeReport();
            DialogView dialogView = new DialogView();
            dialogView.setTitle("Submitting Report");
            dialogView.setMessage("Uploading files...");
            dialogView.setPostiveEventHandler(null);
            dialogView.setNegativeEventHandler(null);

            dialogView.show(view);

            APIManager.getInstance().submitReport(report,(res,ex)->{
                if(ex == null){
                    if(res.isOK()){
                        //all good
                        dialogView.setMessage("Report Successfully Submitted.");
                        dialogView.setPostiveEventHandler(event -> {
                            dialogView.close();
                            clear();
                        });
                        dialogView.getPostiveButton().setText("Done");

                    }else{
                        dialogView.setMessage("Something went wrong: "+res.getMessage());
                        dialogView.setPostiveEventHandler(event -> dialogView.close());
                        dialogView.getPostiveButton().setText("Close");
                    }
                }else{
                    dialogView.setMessage("Something went wrong: "+ex.getMessage());
                    dialogView.setPostiveEventHandler(event -> dialogView.close());
                    dialogView.getPostiveButton().setText("Close");
                }
            });
        }

    }

    boolean isValid(){
        VehicleModel model = modelCombo.getSelectionModel().getSelectedItem();
        String licensePlate = lpField.getText();
        String color = coField.getText();
        String desc = description.getText();
        int count = violationListView.getItems().size();

        return model != null &&
                licensePlate.length() > 0 &&
                color.length() > 0 &&
                desc.length() > 0 &&
                count > 0;
    }

    private void createViolationViewDialog(Violation violation){
        ViolationFormView violationFormView = new ViolationFormView(violation);
        DynamicDialog dialogView = new DynamicDialog(violationFormView);
        dialogView.setTitle("Evidence View");
        dialogView.setMessage("The violation");
        dialogView.getPostiveButton().setText("Close");
        dialogView.getNegativeButton().setText("Delete");
        dialogView.delegate(new DynamicDialog.DialogDelegate() {

            @Override
            public boolean onDone(DynamicDialog dialog) {
                violationFormView.reset();
                return true;
            }

            @Override
            public void onCancel(DynamicDialog dialog) {
                violationListView.getItems().remove(violation);
                violationFormView.reset();
            }
        });
        dialogView.show(this.view);

    }

    private Report makeReport(){
        //make report which includes: vehicle,violations and description
        Vehicle vehicle = makeVehicle();
        Report report = new Report(null,description.getText(),null,null,vehicle);
        report.setViolations(violationListView.getItems());
        return report;
    }

    private Vehicle makeVehicle(){
        VehicleModel model = modelCombo.getSelectionModel().getSelectedItem();
        String licensePlate = lpField.getText();
        String color = coField.getText();
        return new Vehicle(licensePlate,model,color);
    }

    private void reset(){
        lpField.setText(null);
        coField.setText(null);
        modelCombo.getSelectionModel().clearSelection();
        description.setText(null);
        violationListView.getItems().clear();
    }
}
