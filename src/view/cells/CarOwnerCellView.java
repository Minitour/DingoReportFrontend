package view.cells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.VehicleOwner;
import ui.UIView;

import java.util.ResourceBundle;

public class CarOwnerCellView extends UIView {

    private VehicleOwner vehicleOwner;

    @FXML
    private Label id;

    @FXML
    private Label licenseNumber;

    @FXML
    private Label fullName;

    @FXML
    private Label address;


    public CarOwnerCellView setVehicleOwner(VehicleOwner owner) {

        id.setText(owner.getId().toUpperCase());
        licenseNumber.setText(owner.getDrivingLicense().toUpperCase());
        fullName.setText(owner.getName());
        address.setText(owner.getAddress());

        id.setTextFill(Color.WHITE);
        licenseNumber.setTextFill(Color.WHITE);
        fullName.setTextFill(Color.WHITE);
        address.setTextFill(Color.WHITE);

        return this;
    }


    @Override
    public void layoutSubviews(ResourceBundle bundle) {

    }

    @Override
    public void layoutBundle(ResourceBundle bundle) {

    }

    @Override
    public String resource() {
        return "/resources/xml/list_item_4.fxml";
    }
}
