package view.forms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public class OfficerFormView extends UIView implements UIForm{

    @FXML
    private TextField badgeNum;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneExt;

    @FXML
    private TextField rank;

    //TODO: add combo box for role selection.

    public OfficerFormView() {
        super("/resources/xml/form_create_officer.fxml");
    }

    public String getName() {
        return name.getText();
    }

    public String getBadgeNum() {
        return badgeNum.getText();
    }

    public String getPhoneExt() {
        return phoneExt.getText();
    }

    public int getRank(){
        return Integer.parseInt(rank.getText());
    }

    @Override
    public boolean isValid() {
        //TODO: Check fields
        return true;
    }

    public void reset(){
        name.setText(null);
        badgeNum.setText(null);
        phoneExt.setText(null);
        rank.setText(null);
    }

    @Override
    public void setFormMode(FormMode formMode) {

    }
}
