package main;

import controller.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public class AppDelegate extends Application {

    public static void main(String... args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginController controller = new LoginController();
        primaryStage.setScene(new Scene(controller.getRoot()));
        primaryStage.show();
    }
}
