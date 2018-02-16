package controller.masters;

import controller.AddAnyUserController;
import controller.AddUserController;
import ui.UIView;
import ui.UIViewController;

/**
 * Created By Tony on 14/02/2018
 */
public class AdminMasterController extends MasterMenuController{

    private UIViewController[] controllers = {new AddAnyUserController()};

    @Override
    public UIView viewForIndexAt(int index) {
        return controllers[index].view;
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"Add User","Manage Users"};
    }
}
