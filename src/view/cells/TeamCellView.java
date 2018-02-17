package view.cells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Report;
import model.Team;
import model.VehicleOwner;
import model.Violation;
import ui.UIView;

import java.util.ResourceBundle;

/**
 * Created By Tony on 17/02/2018
 */
public class TeamCellView extends UIView {

    @FXML
    private Label teamId;

    @FXML
    private Label reports;

    public TeamCellView(Team item) {
        super("/resources/xml/list_item_5.fxml");
        teamId.setText("Team #"+item.getTeamNum());
        reports.setText("Assigned reports: "+item.getReports().size());
    }
}
