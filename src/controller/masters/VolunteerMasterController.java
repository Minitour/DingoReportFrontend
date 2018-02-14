package controller.masters;

import controller.UpdatePasswordController;
import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public class VolunteerMasterController extends MasterMenuController{
    @Override
    public UIView viewForIndexAt(int index) {
        switch (index){
            case 2:
                return new UpdatePasswordController().view;
                default:
                    return null;
        }
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"View Reports","New Report","Update Password"};
    }
}
