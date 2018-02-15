package main;

import controller.AuthenticatedWebViewController;
import controller.LoginController;
import controller.WebViewController;
import controller.masters.MasterMenuController;
import controller.masters.SecretaryMasterController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import network.APIManager;
import network.Constants;
import view.DynamicDialog;
import view.forms.UIFormView;
import view.forms.VideoViolationFormView;

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
//        WebViewController controller = new WebViewController("https://www.youtube.com/embed/VYUQV4jMuyA?autoplay=1");
//        primaryStage.setScene(new Scene(controller.view));

        LoginController controller = new LoginController();
        primaryStage.setScene(new Scene(controller.view));
        primaryStage.show();

        controller.setOnExit((event)-> primaryStage.close());
        controller.setOnAuth(role -> {
            //TODO: if role is not -1, launch the next controller.
            if(role != -1) {
                VideoViolationFormView videoViolationFormView = new VideoViolationFormView();
                videoViolationFormView.setFormMode(UIFormView.FormMode.READ_ONLY);
                videoViolationFormView.setVideoFromUrl("https://www.youtube.com/embed/VYUQV4jMuyA?autoplay=1&showinfo=0&controls=0&loop=1");
                DynamicDialog dynamicDialog = new DynamicDialog(videoViolationFormView);
                dynamicDialog.show(controller.view);
            }
        });
    }
}
