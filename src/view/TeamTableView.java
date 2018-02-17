package view;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import model.Team;
import ui.UITableView;

import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created By Tony on 17/02/2018
 */
public class TeamTableView extends UITableView<Team> {

    final static String[] columns = {"Team ID","Team Leader","Members"};

    private Button button;

    @Override
    public void layoutSubviews(ResourceBundle bundle) {
        super.layoutSubviews(bundle);

        Pane toolbar = getToolBar();
        button = new JFXButton("Create Team");
        button.getStyleClass().add("button-raised");
        button.setOnAction(event -> onCreateTeamClicked());

        toolbar.getChildren().add(button);

    }

    @Override
    public int numberOfColumns() {
        return columns.length;
    }

    public void onCreateTeamClicked(){

    }

    @Override
    public Collection<? extends Team> dataSource() {
        return null;
    }

    @Override
    public String bundleIdForIndex(int index) {
        return columns[index];
    }

    @Override
    public UITableView.TableColumnValue<Team> cellValueForColumnAt(int index) {
        switch (index){
            case 0:
                return Team::getTeamNum;
            case 1:
                return team -> team.getLeader().getName();
            case 2:
                return teams -> teams.getOfficers().size();
        }
        return null;
    }

}


