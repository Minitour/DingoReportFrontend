package controller.masters;

import controller.UpdatePasswordController;
import controller.report.ViewReportsController;
import ui.UIView;
import ui.UIViewController;

/**
 * Created By Tony on 14/02/2018
 */
public class OfficerMasterController extends MasterMenuController{

    private UIViewController[] controllers = {new ViewReportsController(),new UpdatePasswordController()};

    @Override
    public UIView viewForIndexAt(int index) {
        UIViewController controller = controllers[index];
        if(controller instanceof ViewReportsController){
            ((ViewReportsController)controller).refreshList();
        }
        return controller.view;
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"View Reports","Update Password"};
    }
}
