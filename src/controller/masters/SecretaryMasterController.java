package controller.masters;

import controller.AddUserController;
import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public class SecretaryMasterController extends MasterMenuController{
    @Override
    public UIView viewForIndexAt(int index) {
        switch (index){
            case 0:
                return new AddUserController(AddUserController.UserType.VOLUNTEER).view;
            default:
                return null;
        }
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"Add Volunteer","Update Password"};
    }
}
