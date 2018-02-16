package controller.report;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Report;
import model.Vehicle;
import model.VehicleModel;
import model.Violation;
import network.APIManager;
import ui.UIViewController;

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

    public ReportFillController() {
        super("/resources/xml/controller_report_fill.fxml");
        setup();
    }

    private void setup(){
        positive.setText("Submit");
        negative.setText("Clear");
        

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
