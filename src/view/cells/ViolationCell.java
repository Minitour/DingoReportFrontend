package view.cells;

import model.Violation;
import ui.UIListViewCell;
import ui.UIView;

/**
 * Created By Tony on 16/02/2018
 */
public class ViolationCell extends UIListViewCell<Violation,UIView> {

    @Override
    public UIView load(Violation item) {
        return new ViolationViewCell().setViolation(item);
    }
}