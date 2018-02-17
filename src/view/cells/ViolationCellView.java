package view.cells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.VehicleOwner;
import model.Violation;
import ui.UIView;

import java.util.ResourceBundle;

public class ViolationCellView extends UIView {

    private VehicleOwner vehicleOwner;

    @FXML
    private Label violation;

    public ViolationCellView setViolation(Violation violation) {
        String label = "[" + violation.getClass().getSimpleName().replace("Violation","") +"]";
        this.violation.setText(label + " #" +violation.getType().getTypeNum() + " " + violation.getType().getName() );
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
        return "/resources/xml/list_item_3.fxml";
    }
}
