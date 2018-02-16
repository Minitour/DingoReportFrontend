package view.forms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Created By Tony on 14/02/2018
 */
public class OfficerFormView extends UIFormView {

    @FXML
    private TextField badgeNum;

    @FXML
    private TextField name;

    @FXML
    private TextField phoneExt;

    @FXML
    private TextField rank;

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
        try {
            return Integer.parseInt(rank.getText());
        }catch (NumberFormatException e){
            return -1;
        }
    }

    @Override
    public boolean isValid() {
        return !getName().isEmpty()
                && !getBadgeNum().isEmpty()
                && !getPhoneExt().isEmpty()
                && getRank() > -1;
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
