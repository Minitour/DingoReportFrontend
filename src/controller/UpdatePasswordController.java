package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import network.APIManager;
import ui.UIViewController;
import view.DialogView;

/**
 * Created By Tony on 14/02/2018
 */
public class UpdatePasswordController extends UIViewController {

    @FXML
    private TextField currentPassword;

    @FXML
    private TextField newPassword;

    @FXML
    private Button submit;

    public UpdatePasswordController() {
        //TODO: create FXML
        super("");

        submit.setOnAction(event -> {
            String cPassword = currentPassword.getText();
            String nPassword = newPassword.getText();

            currentPassword.setText(null);
            newPassword.setText(null);
            submit.setDisable(true);

            APIManager.getInstance().updatePassword(cPassword,nPassword,(response, exception) -> {
                String title;
                String message;
                if(response.isOK()){
                    //TODO: show popup
                    title = "Success";
                    message = "Password successfully changed.";

                }else{
                    title = "Error";
                    message = "Something went wrong.\n Error: "+exception.getMessage();
                }

                DialogView dialogView = new DialogView();
                dialogView.setTitle(title);
                dialogView.setMessage(message);
                dialogView.show(view);
                
                submit.setDisable(false);
            });
        });
    }
}
