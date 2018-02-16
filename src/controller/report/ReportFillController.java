package controller.report;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import model.*;
import network.APIManager;
import network.Callbacks;
import network.ServerResponse;
import ui.UIViewController;
import view.DynamicDialog;
import view.cells.ViolationCell;
import view.forms.ViolationFormView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        violationListView.setCellFactory(param -> new ViolationCell());

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
                    return true;
                }

                @Override
                public void onCancel(DynamicDialog dialog) {

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
