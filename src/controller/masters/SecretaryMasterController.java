package controller.masters;

import controller.AddUserController;
import ui.UIView;
import view.forms.VideoViolationForm;
import view.forms.ViolationForm;

import java.util.ArrayList;

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
                return new ViolationForm(new ArrayList<>());
        }
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"Add Volunteer","Update Password"};
    }
}
