package controller.masters;

import controller.AddUserController;
import controller.UpdatePasswordController;
import javafx.scene.control.TextField;
import model.Account;
import model.Volunteer;
import network.APIManager;
import ui.UIView;
import view.DialogView;

/**
 * Created By Tony on 14/02/2018
 */
public class SecretaryMasterController extends MasterMenuController{
    @Override
    public UIView viewForIndexAt(int index) {
        switch (index){
            case 0:
                AddUserController addUserController =  new AddUserController(AddUserController.UserType.VOLUNTEER){
                    TextField pwField = getAccountFormView().getPasswordField();
                    @Override
                    public void onAccountReady(Account account) {
                        DialogView dialogView = makeDialog("Creating Account", "Loading...");
                        dialogView.show(view);
                        APIManager.getInstance().createVolunteer((Volunteer) account, (response, exception) -> {
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
                        pwField.setText("****************");
                        pwField.setDisable(true);
                    }
                };
                TextField pwField = addUserController.getAccountFormView().getPasswordField();
                pwField.setText("****************");
                pwField.setDisable(true);
                return addUserController.view;
            default:
                return new UpdatePasswordController().view;
        }
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"Add Volunteer","Update Password"};
    }
}
