package controller;

import com.jfoenix.controls.JFXSnackbar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Officer;
import model.Report;
import model.Team;
import network.APIManager;
import ui.UIViewController;
import view.cells.OfficerCell;
import view.cells.ReportCell;
import view.cells.TeamCell;
import view.cells.TeamCell2;

/**
 * Created By Tony on 17/02/2018
 */
public class AddOfficerToTeamController extends UIViewController {

    @FXML
    private ListView<Officer> from;

    @FXML
    private ListView<Team> to;

    @FXML
    private Button assign;

    @FXML
    private Label titleLabel;

    public AddOfficerToTeamController() {
        super("/resources/xml/controller_add_report_team.fxml");

        from.setCellFactory(param -> new OfficerCell());
        to.setCellFactory(param -> new TeamCell2());
        titleLabel.setText("Assign Officer to Team");
    }

    public void refresh(){
        APIManager.getInstance().getTeams((response, teams, ex) -> {
            to.getItems().clear();
            to.getItems().addAll(teams);
        });


        APIManager.getInstance().getUnassignedOfficers((response, officers, exception) -> {
            from.getItems().clear();
            from.getItems().addAll(officers);
        });

        assign.setOnAction(event -> {
            Officer o = from.getSelectionModel().getSelectedItem();
            Team t = to.getSelectionModel().getSelectedItem();
            assign(o,t);
        });

    }

    private void assign(Officer officer,Team toTeam){
        APIManager.getInstance().addOfficerToTeam(officer, toTeam, (response, exception) -> {
            //show toast
            JFXSnackbar bar = new JFXSnackbar(this.view);
            if (response.isOK()) {
                bar.enqueue(new JFXSnackbar.SnackbarEvent("Assigned Officer "+officer.getName() + " to Team #"+toTeam.getTeamNum()));
                from.getItems().remove(officer);
                toTeam.getOfficers().add(officer);
                to.refresh();
            }
            else
                bar.enqueue(new JFXSnackbar.SnackbarEvent("Something went wrong: "+response.getMessage()));
        });
    }


}
