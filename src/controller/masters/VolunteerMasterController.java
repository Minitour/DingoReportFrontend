package controller.masters;

import controller.UpdatePasswordController;
import controller.report.ReportFillController;
import controller.report.ViewReportsController;
import ui.UIView;
import ui.UIViewController;

/**
 * Created By Tony on 14/02/2018
 */
public class VolunteerMasterController extends MasterMenuController{

    private UIViewController[] controllers = {
            new ViewReportsController(),
            new ReportFillController(),
            new UpdatePasswordController()
    };

    @Override
    public UIView viewForIndexAt(int index) {
        return controllers[index].view;
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"View Reports","New Report","Update Password"};
    }
}
