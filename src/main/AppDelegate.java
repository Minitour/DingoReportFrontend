package main;

import controller.WebViewController;
import controller.masters.MasterMenuController;
import controller.masters.SecretaryMasterController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created By Tony on 14/02/2018
 */
public class AppDelegate extends Application {

    public static void main(String... args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //MasterMenuController menuController = new SecretaryMasterController();
        WebViewController controller = new WebViewController("https://www.youtube.com/embed/VYUQV4jMuyA?autoplay=1");
        primaryStage.setScene(new Scene(controller.view));

//        LoginController controller = new LoginController();
//        primaryStage.setScene(new Scene(controller.view));
        primaryStage.show();

//        controller.setOnExit((event)-> primaryStage.close());
//        controller.setOnAuth(role -> {
//            //TODO: if role is not -1, launch the next controller.
//            MasterMenuController menuController = new MasterMenuController();
//            primaryStage.setScene(new Scene(menuController.view));
//        });
    }
}
