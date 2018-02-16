package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import model.Account;
import model.Volunteer;
import network.APIManager;
import ui.UIViewController;
import view.DialogView;

import static controller.AddUserController.UserType.*;

/**
 * Created By Tony on 16/02/2018
 */
public class AddAnyUserController extends UIViewController {

    @FXML
    private ComboBox<AddUserController.UserType> typeSelector;

    @FXML
    private VBox formHolder;

    private AddUserController controller;

    public AddAnyUserController() {
        super("/resources/xml/controller_add_any_user.fxml");
        typeSelector.getItems().addAll(SUPERUSER,HIGHRANK,OFFICER,SECRETARY,VOLUNTEER);
        typeSelector.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> changed(newValue));

    }

    void changed(AddUserController.UserType type){
        formHolder.getChildren().clear();
        controller = new AddUserController(type){
            @Override
            public void onAccountReady(Account account) {
                DialogView dialogView = makeDialog("Creating Account", "Loading...");
                dialogView.show(view);
                APIManager.getInstance().createUser(account, (response, exception) -> {
                    if(exception == null){
                        if(response.isOK()){
                            dialogView.setMessage("The account "+account.getEMAIL()+" was created!");
                            dialogView.setPostiveEventHandler(event -> {
                                dialogView.close();
                                this.reset();
                            });
                        }else{
                            dialogView.setMessage("Error! something went wrong: "+response.getMessage());
                            dialogView.setPostiveEventHandler(event -> dialogView.close());
                        }
                    }else{
                        dialogView.setMessage("Error! something went wrong: "+response.getMessage());
                        dialogView.setPostiveEventHandler(event -> dialogView.close());

                    }
                    dialogView.getPostiveButton().setVisible(true);

                });
            }
        };
        formHolder.getChildren().add(controller.view);
    }

    private DialogView makeDialog(String title,String message){
        DialogView dialogView = new DialogView();
        dialogView.setTitle(title);
        dialogView.setMessage(message);
        dialogView.getPostiveButton().setText("Done");
        dialogView.setNegativeEventHandler(null);
        return dialogView;
    }
}
