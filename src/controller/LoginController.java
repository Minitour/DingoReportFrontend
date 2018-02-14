package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import network.APIManager;
import ui.UIViewController;


/**
 * Created By Tony on 14/02/2018
 */
public class LoginController extends UIViewController {

    @FXML
    private Label copyright;

    @FXML
    private TextField userInputField;

    @FXML
    private TextField passwordInputField;

    @FXML
    private Button loginButton;

    @FXML
    private Button exitButton;

    private Authentication authentication;

    /**
     * Create a UIViewController instance from any fxml file.
     */
    public LoginController() {
        super("/resources/xml/controller_login.fxml");

        loginButton.setOnAction(event -> {
            String email = userInputField.getText();
            String password = passwordInputField.getText();

            //TODO check email regex and password.length > x

            APIManager.getInstance().login(email, password, (response, id, token,roleId, exception) -> {
                System.out.println(response + " id: "+id + ", token: "+token);
                if(authentication != null)
                    authentication.onAuth(roleId);
            });
        });
    }

    public void setOnExit(EventHandler<ActionEvent> eventHandler){
        exitButton.setOnAction(eventHandler);
    }

    @FunctionalInterface
    public interface Authentication{
        void onAuth(int role);
    }

    public void setOnAuth(Authentication authentication) {
        this.authentication = authentication;
    }
}
