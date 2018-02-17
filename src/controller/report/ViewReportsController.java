package controller.report;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import controller.report.ReportViewController;
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
import network.APIManager;
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
public class ViewReportsController extends UIViewController {

    public ViewReportsController() {
        super("/resources/xml/controller_report_view.fxml");
    }

    @FXML
    private ListView<Report> listView;

    @FXML
    private AnchorPane rightPane;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private SplitPane splitPane;

    @Override
    public void viewWillLoad(ResourceBundle bundle) {
        //splitPane.setStyle("-fx-background-color: white;");
        leftPane.setStyle("-fx-background-color: white;");
        rightPane.setStyle("-fx-background-color: white;");
        listView.setCellFactory(param -> new ReportCell());

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null)
                updateView(newValue);
        });
    }

    void updateView(Report report){
        ReportViewController vc = new ReportViewController(report);
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

    public void refreshList(){
        APIManager.getInstance().getReports((response, reports, exception) -> {
            if(exception == null && response.isOK()){
                Report selectedItem = listView.getSelectionModel().getSelectedItem();
                listView.getItems().clear();
                listView.getItems().addAll(reports);
                if(selectedItem != null){
                    int num = selectedItem.getReportNum();
                    listView.getSelectionModel().select(indexOfItem(num));
                }
            }
        });
    }

    @Override
    public Stage getStage() {
        return null;
    }

    private void cancel(){
        showView(null);
        listView.getSelectionModel().clearSelection();
    }

    private int indexOfItem(int reportNum){
        List<Report> reports = listView.getItems();
        for (int i = 0; i < reports.size(); i++) {
            if(reports.get(i).getReportNum() == reportNum)
                return i;
        }
        return 0;
    }


}
