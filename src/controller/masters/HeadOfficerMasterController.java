package controller.masters;

import controller.*;
import controller.report.ViewReportsController;
import model.Account;
import network.APIManager;
import ui.UIView;
import ui.UIViewController;
import view.DialogView;

/**
 * Created By Tony on 14/02/2018
 */
public class HeadOfficerMasterController extends MasterMenuController{

    private UIViewController[] controllers = {
            new ViewReportsController(),
            new ExportReportsController(),
            new AddReportToTeamController(),
            new AddOfficerToTeamController(),
            new ManageTeamsController(),
            getAddUserController(),
            new UpdatePasswordController()

    };

    private UIViewController getAddUserController() {
        return new AddUserController(AddUserController.UserType.OFFICER){
            @Override
            public void onAccountReady(Account account) {
                super.onAccountReady(account);
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
    }

    @Override
    public UIView viewForIndexAt(int index) {
        UIViewController controller = controllers[index];
        if(controller instanceof ViewReportsController){
            ((ViewReportsController)controller).refreshList();
        }

        if(controller instanceof ManageTeamsController){
            ((ManageTeamsController)controller).refresh();
        }

        if(controller instanceof AddReportToTeamController){
            ((AddReportToTeamController)controller).refresh();
        }

        if(controller instanceof AddOfficerToTeamController){
            ((AddOfficerToTeamController)controller).refresh();
        }

        return controller.view;
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{
                "View Reports",
                "Export Reports",
                "Assign Reports",
                "Assign Officer",
                "Team Management",
                "Add Officer",
                "Update Password"};
    }
}
