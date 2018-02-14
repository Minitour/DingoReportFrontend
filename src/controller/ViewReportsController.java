package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Report;
import model.Vehicle;
import model.VehicleModel;
import ui.UIViewController;
import view.DynamicDialog;
import view.cells.ReportCell;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created By Tony on 14/02/2018
 */
public class ViewReportsController extends UIViewController implements DynamicDialog.DialogDelegate {
    /**
     * Create a UIViewController instance from any fxml file.
     *
     * @param path The path for the fxml.
     */
    public ViewReportsController(String path) {
        super(path);
    }

    @FXML
    private ListView<Report> listView;

    @FXML
    private JFXButton add;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private SplitPane splitPane;

    @Override
    public void viewWillLoad(ResourceBundle bundle) {
        splitPane.setStyle("-fx-background-color: white;");
        leftPane.setStyle("-fx-background-color: white;");
        rightPane.setStyle("-fx-background-color: white;");
        listView.setCellFactory(param -> new ReportCell());

        new Thread(()->{
            List<Report> reports = getReports();
            Platform.runLater(()-> listView.getItems().addAll(reports));
        }).start();

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
                updateView(newValue);
        });

        add.setOnAction(event -> DynamicDialog.load("/resources/xml/VerifyCarDetailsView.fxml")
                .connect()
                .delegate(this)
                .prepare()
                .show(getRoot()));
    }

    void updateView(Report report){
        ReportViewController vc = ReportViewController.instance(report);
        vc.setCanceCallback(this::cancel);
        vc.setParentView(getRootView());
        showView(vc);
    }

    void showView(UIViewController controller){
        rightPane.getChildren().clear();
        if(controller != null){
            Pane reportView = controller.getRoot();
            AnchorPane.setTopAnchor(reportView,8.0);
            AnchorPane.setBottomAnchor(reportView,8.0);
            AnchorPane.setRightAnchor(reportView,8.0);
            AnchorPane.setLeftAnchor(reportView,8.0);
            rightPane.getChildren().add(reportView);
        }

    }

    @Override
    public Stage getStage() {
        return null;
    }

    @Override
    public void onHook(Map<String, Node> views, DynamicDialog dialog) {
        dialog.getTitleLabel().setText("Create New Report");
        dialog.getMessageLabel().setText("");
        dialog.getPostiveButton().setText("Submit");
        dialog.getNegativeButton().setText("Cancel");
    }

    /**
     * This method is called when user selects `submit` in `New Report Dialog`.
     * @param dialog The dialog object
     * @return true to dismiss.
     */
    @Override
    public boolean onDone(DynamicDialog dialog) {
        try {
            //create reference to fields
            JFXTextField licensePlate = dialog.findViewById("lpField");
            JFXTextField carModel = dialog.findViewById("cmField");
            JFXTextField color = dialog.findViewById("coField");

            //fetch data
            String licencePlateText = licensePlate.getText();
            String carModelText = carModel.getText();
            String colorText = color.getText();

            //Validate data length
            if(licencePlateText.length() == 0 || carModelText.length() == 0 || colorText.length() == 0)
                throw new Exception("Invalid Input");

            //submitCarDetails(licencePlateText, carModelText, colorText);

            return true;
        }catch (Exception e){
            Label label = dialog.findViewById("errorLabel");
            label.setText("Error");
            System.err.println(e.getMessage());
            return false;
        }

    }

    private void cancel(){
        add.setVisible(true);
        setMenuOpen(true);
        showView(null);
        listView.getSelectionModel().clearSelection();
    }

    private void setMenuOpen(boolean open){

        double endValue = open ? 300.0 : 0.0;
        final Timeline timelineOpenB = new Timeline();

        final KeyValue kvb1 = new KeyValue(leftPane.maxWidthProperty(),endValue);
        final KeyValue kvb2 = new KeyValue(leftPane.minWidthProperty(),endValue);
        final KeyFrame kfb1 = new KeyFrame(Duration.valueOf("100ms"), kvb1, kvb2);
        timelineOpenB.getKeyFrames().add(kfb1);
        timelineOpenB.play();
    }

    /**
     * Control methods
     */

    public void createNewReport(Report report){
        //TODO: implement report creation

        //add entry to DB and get report id.

        //for each violation in report
        //add to DB where report id = id and get violation id.
        // for each evidence in violation
        //add to db where violation id = id.
    }

    public void submitCarDetails(String license, VehicleModel model, String color){
        //create new vehicle from data fetched
        Vehicle vehicle = new Vehicle(license, model, color);

        //create `New Report View Controller`
        ReportViewController newReportVC = ReportViewController.instance(vehicle);
        newReportVC.setParentView(getRootView());

        newReportVC.setCanceCallback(this::cancel);
        showView(newReportVC);

        add.setVisible(false);

        listView.getSelectionModel().clearSelection();

        setMenuOpen(false);
    }

    private static List<Report> getReports(){
        List<Report> list = new ArrayList<>();

        //TODO: load reports from database.
        return list;
    }
}
