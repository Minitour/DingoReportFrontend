package controller;

import com.jfoenix.controls.JFXSnackbar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import model.Report;
import model.Team;
import network.APIManager;
import network.Callbacks;
import network.ServerResponse;
import ui.UIViewController;
import view.cells.ReportCell;
import view.cells.TeamCell;

/**
 * Created By Tony on 17/02/2018
 */
public class AddReportToTeamController extends UIViewController {

    @FXML
    private ListView<Report> from;

    @FXML
    private ListView<Team> to;

    @FXML
    private Button assign;

    @FXML
    private Label titleLabel;

    public AddReportToTeamController() {
        super("/resources/xml/controller_add_report_team.fxml");

        from.setCellFactory(param -> new ReportCell());
        to.setCellFactory(param -> new TeamCell());
        titleLabel.setText("Assign Report to Team");
    }

    public void refresh(){
        APIManager.getInstance().getTeams((response, teams, ex) -> {
            to.getItems().clear();
            to.getItems().addAll(teams);
        });

        APIManager.getInstance().getUnassignedReports((response, reports, exception) -> {
            from.getItems().clear();
            from.getItems().addAll(reports);
        });

        assign.setOnAction(event -> {
            Report r = from.getSelectionModel().getSelectedItem();
            Team t = to.getSelectionModel().getSelectedItem();
            assign(r,t);
        });

    }

    private void assign(Report report,Team toTeam){
        APIManager.getInstance().addReportToTeam(report, toTeam, (response, exception) -> {
            //show toast
            JFXSnackbar bar = new JFXSnackbar(this.view);
            if (response.isOK()) {
                bar.enqueue(new JFXSnackbar.SnackbarEvent("Assigned report #"+report.getReportNum() + " to Team #"+toTeam.getTeamNum()));
                from.getItems().remove(report);
                toTeam.getReports().add(report);
                to.refresh();
            }
            else
                bar.enqueue(new JFXSnackbar.SnackbarEvent("Something went wrong: "+response.getMessage()));
        });
    }


}
