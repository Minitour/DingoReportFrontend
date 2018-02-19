package controller.masters;

import controller.AddAnyUserController;
import controller.AddUserController;
import controller.UpdatePasswordController;
import model.Account;
import network.APIManager;
import ui.UITableView;
import ui.UIView;
import ui.UIViewController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created By Tony on 14/02/2018
 */
public class AdminMasterController extends MasterMenuController{

    private UIViewController[] controllers = {
            new AddAnyUserController(),
            new UpdatePasswordController()
    };

    private UITableView<Account> accountTableView = new UITableView<Account>() {

        List<Account> accountList;

        @Override
        public int numberOfColumns() {
            return 3;
        }

        @Override
        public Collection<? extends Account> dataSource() {
            return accountList;
        }

        @Override
        public String bundleIdForIndex(int index) {
            switch (index){
                case 0:
                    return "Account ID";
                case 1:
                    return "Email";
                case 2:
                    return "Role";
            }
            return null;
        }

        @Override
        public TableColumnValue<Account> cellValueForColumnAt(int index) {
            switch (index){
                case 0:
                    return Account::getID;
                case 1:
                    return Account::getEMAIL;
                case 2:
                    return Account::getROLE_ID;
            }
            return null;
        }

        @Override
        public void reloadData() {
            APIManager.getInstance().getAccounts((response, accounts, ex) -> {
                if(ex==null){
                    if(response.isOK()){
                        try {
                            accountList = new ArrayList<>(accounts);
                            super.reloadData();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    };

    @Override
    public UIView viewForIndexAt(int index) {
        switch (index){
            case 0:
                return controllers[0].view;
            case 1:
                accountTableView.reloadData();
                return accountTableView;
            case 2:
                return controllers[1].view;
        }
        return null;
    }

    @Override
    public String[] itemsForMenu() {
        return new String[]{"Add User","Active Users","Update Password"};
    }
}
