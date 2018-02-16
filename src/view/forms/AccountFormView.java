package view.forms;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import main.EmailValidator;

/**
 * Created By Tony on 14/02/2018
 */
public class AccountFormView extends UIFormView {

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
        return EmailValidator.validate(getEmail()) && getPassword().length() >= 8;
    }

    public void reset(){
        email.setText(null);
        password.setText(null);
    }

    @Override
    public void setFormMode(FormMode formMode) {

    }

    public TextField getPasswordField(){
        return password;
    }
}
