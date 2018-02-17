package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Officer;
import model.Team;
import network.APIManager;
import ui.UITableView;
import ui.UIView;
import ui.UIViewController;
import view.DynamicDialog;
import view.OfficerPicker;
import view.TeamTableView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created By Tony on 17/02/2018
 */
public class ManageTeamsController extends UIViewController {
    @FXML
    private Pane tableHolder;

    private UITableView<Team> tableView;
    private DynamicDialog dynamicDialog;

    public ManageTeamsController() {
        super("/resources/xml/controller_manage_teams.fxml");
    }

    private void showTeamCreationDialog(){
        dynamicDialog = new DynamicDialog(new OfficerPicker(){
            @Override
            public void layoutSubviews(ResourceBundle bundle) {
                super.layoutSubviews(bundle);

                APIManager.getInstance().getAllOfficers((response, officers,ex)->{
                    if(officers != null)
                        officers_list.getItems().addAll(officers);
                });

                getChildren().add(officers_list);

                officers_list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    didSelectTeamLeader(newValue);
                    dynamicDialog.close();
                });
                //dynamicDialog.close();
            }
        });

        dynamicDialog.remove(dynamicDialog.getNegativeButton());
        dynamicDialog.getPostiveButton().setText("Cancel");
        dynamicDialog.setTitle("Create Team");
        dynamicDialog.setMessage("Select Team Leader");


        dynamicDialog.show(this.view);
    }

    private void didSelectTeamLeader(Officer officer){
        Team team = new Team(null);
        team.setLeader(officer);
        APIManager.getInstance().createTeam(team,(json,res)-> {
            tableView.reloadData();
        });
    }

    @Override
    public void viewWillLoad(ResourceBundle bundle) {
        super.viewWillLoad(bundle);



        tableView = new TeamTableView() {
            List<Team> teamList = new ArrayList<>();

            @Override
            public Collection<? extends Team> dataSource() {
                return teamList;
            }

            @Override
            public void onCreateTeamClicked() {
                showTeamCreationDialog();
            }

            @Override
            public void reloadData() {
                APIManager.getInstance().getTeams((response, teams, ex) -> {
                    if(ex==null){
                        if(response.isOK()){
                            try {
                                teamList = new ArrayList<>(teams);
                                super.reloadData();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };

        AnchorPane.setTopAnchor(tableView, 0.0);
        AnchorPane.setRightAnchor(tableView, 0.0);
        AnchorPane.setLeftAnchor(tableView, 0.0);
        AnchorPane.setBottomAnchor(tableView, 0.0);

        tableHolder.getChildren().add(tableView);
        tableView.reloadData();
    }

    public void refresh(){
        tableView.reloadData();
    }


}
