package view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public class VolunteerFormView extends UIView {

    @FXML
    private TextField name;

    @FXML
    private TextField phone;

    public VolunteerFormView() {
        super("/resources/xml/form_create_volunteer.fxml");
    }

    public String getName() {
        return name.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public void reset(){
        name.setText(null);
        phone.setText(null);
    }
}
