package main;

import controller.LoginController;
import controller.masters.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.AutoSignIn;
import view.DialogView;

/**
 * Created By Tony on 14/02/2018
 */
public class AppDelegate extends Application {

    public static void main(String... args){
        launch(args);


        //String ID,String EMAIL, String password, String name, String phone

    }

    private final LoginController controller = initLoginController();
    private Stage primaryStage;
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setScene(new Scene(controller.view));
        primaryStage.show();
    }

    public LoginController initLoginController(){
        LoginController controller = new LoginController();
        controller.setOnExit(event -> primaryStage.close());
        controller.setOnAuth(role -> {
            if(role != -1) {
                onLoginSuccess(role);
            }else{
                DialogView dialog = new DialogView();
                dialog.setTitle("Invalid Credentials");
                dialog.setMessage("Incorrect email or password. Please try again.");
                dialog.getPostiveButton().setText("Close");
                dialog.setPostiveEventHandler(event -> dialog.close());
                dialog.show(controller.view);
            }
        });
        return controller;
    }

    void onLoginSuccess(int role){
        MasterMenuController controller = null;
        switch (role){
            case 0:
                controller = new AdminMasterController();
                break;
            case 1:
                controller = new HeadOfficerMasterController();
                break;
            case 2:
                controller = new OfficerMasterController();
                break;
            case 3:
                controller = new SecretaryMasterController();
                break;
            case 4:
                controller = new VolunteerMasterController();
                break;
        }

        assert controller != null;
        controller.setOnLogout(event -> {
            AutoSignIn.reset();
            primaryStage.getScene().setRoot(this.controller.view);
        });

        primaryStage.getScene().setRoot(controller.view);

    }
}
