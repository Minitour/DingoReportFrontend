package view;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Officer;
import ui.UIView;
import view.cells.OfficerCell;
import view.cells.ReportCell;

/**
 * Created By Tony on 17/02/2018
 */
public class OfficerPicker extends UIView {

    @FXML
    protected ListView<Officer> officers_list;

    public OfficerPicker() {
        super("/resources/xml/form_officer_picker.fxml");

        officers_list.setCellFactory(param -> new OfficerCell());
    }


}
