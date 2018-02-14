package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    /**
     * Create a UIViewController instance from any fxml file.
     */
    public LoginController() {
        super("/resources/xml/controller_login.fxml");

        loginButton.setOnAction(event -> {

        });
    }
}
