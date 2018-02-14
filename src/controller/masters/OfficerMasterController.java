package controller.masters;

import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public class OfficerMasterController extends MasterMenuController{
    @Override
    public UIView viewForIndexAt(int index) {
        return null;
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"View Reports","Update Password"};
    }
}
