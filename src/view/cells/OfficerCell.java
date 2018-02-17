package view.cells;

import model.Officer;
import model.VehicleOwner;
import ui.UIListViewCell;
import ui.UIView;

/**
 * Created By Tony on 17/02/2018
 */
public class OfficerCell extends UIListViewCell<Officer,UIView> {

    @Override
    public UIView load(Officer item) {
        return new OfficerCellView(item);
    }
}
