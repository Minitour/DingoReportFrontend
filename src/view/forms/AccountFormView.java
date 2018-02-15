package view.forms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public class AccountFormView extends UIView implements UIForm {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    public AccountFormView() {
        super("/resources/xml/form_create_account.fxml");
    }

    public String getEmail() {
        return email.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    @Override
    public boolean isValid() {
        //TODO: Check fields
        return true;
    }

    public void reset(){
        email.setText(null);
        password.setText(null);
    }

    @Override
    public void setFormMode(FormMode formMode) {

    }
}
