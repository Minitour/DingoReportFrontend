package view.cells;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Officer;
import ui.UIView;

/**
 * Created By Tony on 17/02/2018
 */
public class OfficerCellView extends UIView{
    @FXML
    private Label menu;

    public OfficerCellView(Officer item) {
        super("/resources/xml/list_item.fxml");
        menu.setText(item.getName());
    }
}
