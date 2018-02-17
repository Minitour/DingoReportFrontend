package view.cells;

import model.Team;
import ui.UIListViewCell;
import ui.UIView;

/**
 * Created By Tony on 17/02/2018
 */
public class TeamCell2 extends UIListViewCell<Team,UIView> {

    @Override
    public UIView load(Team item) {
        return new TeamCellView2(item);
    }
}
