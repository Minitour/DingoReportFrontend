package view.cells;

import model.VehicleOwner;
import ui.UIListViewCell;
import ui.UIView;

/**
 * Created By Tony on 16/02/2018
 */
public class CarOwnerCell extends UIListViewCell<VehicleOwner,UIView> {

    @Override
    public UIView load(VehicleOwner item) {
        return new CarOwnerCellView().setVehicleOwner(item);
    }
}