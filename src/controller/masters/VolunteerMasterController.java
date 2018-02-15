package controller.masters;

import controller.UpdatePasswordController;
import controller.report.ViewReportsController;
import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public class VolunteerMasterController extends MasterMenuController{
    @Override
    public UIView viewForIndexAt(int index) {
        switch (index){
            case 0:
                return new ViewReportsController().view;
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
