package main;

import controller.LoginController;
import controller.masters.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import network.AutoSignIn;
import ui.UIViewController;
import view.DialogView;

/**
 * Created By Tony on 14/02/2018
 */
public class AppDelegate extends Application {

    public static void main(String... args){
        launch(args);
    }

    private final LoginController loginController = initLoginController();
    private Stage loginStage;
    private Stage mainStage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.loginStage = primaryStage;
        loginStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(loginController.view));
        primaryStage.show();

        primaryStage.setOnCloseRequest((ae) -> {
            Platform.exit();
            System.exit(0);
        });

        mainStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

    }

    public LoginController initLoginController(){
        LoginController controller = new LoginController();
        controller.setOnExit(event -> loginStage.close());
        controller.setOnAuth((role,ex)-> {
            if(role != -1) {
                onLoginSuccess(role);
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
            mainStage.close();
            AutoSignIn.reset();
            loginStage.show();
        });

        //loginStage.getScene().setRoot(controller.view);
        loginStage.close();
        showLoggedInStage(controller);
    }

    void showLoggedInStage(UIViewController controller){
        mainStage.setScene(new Scene(controller.view,1200,800));
        mainStage.show();
    }
}
