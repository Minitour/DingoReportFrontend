package controller.report;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ui.UIViewController;

/**
 * Created By Tony on 16/02/2018
 */
public class ReportFillController extends UIViewController {

    @FXML
    private Label form_title;

    @FXML
    private Label form_subtitle;

    @FXML
    private TextField lpField;

    @FXML
    private TextField cmField;

    @FXML
    private TextField coField;

    @FXML
    private TextArea description;

    @FXML
    private Button violationAdd;

    @FXML
    private Button positive;

    @FXML
    private Button negative;

    public ReportFillController() {
        super("/resources/xml/controller_report_fill.fxml");
    }
}
