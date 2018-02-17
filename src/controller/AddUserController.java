package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import model.Account;
import model.HighRankOfficer;
import model.Officer;
import model.Volunteer;
import ui.UIView;
import ui.UIViewController;
import view.forms.AccountFormView;
import view.forms.OfficerFormView;
import view.forms.UIFormView;
import view.forms.VolunteerFormView;

/**
 * Created By Tony on 14/02/2018
 */
public class AddUserController extends UIViewController{

    @FXML
    private Label form_title;

    @FXML
    private VBox formHolder;

    @FXML
    private Button submit;

    private AccountFormView accountFormView = new AccountFormView();

    private VolunteerFormView volunteerFormView = new VolunteerFormView();

    private OfficerFormView officerFormView = new OfficerFormView();

    private UserType type;

    private UIFormView[] forms;

    public AddUserController(UserType type) {
        super("/resources/xml/controller_add_user.fxml");
        init(type);

        submit.setOnAction(event -> submit());
        form_title.setText("Create "+type.value.charAt(0)+type.value.substring(1,type.value.length()).toLowerCase());
    }

    private void init(UserType type){
        this.type = type;
        cleanFormHolder();
        addViewToStack(accountFormView);

        switch (type){
            case VOLUNTEER:
                addViewToStack(volunteerFormView);
                break;
            case OFFICER:
            case HIGHRANK:
                addViewToStack(officerFormView);
                break;
        }

        forms = new UIFormView[]{accountFormView,officerFormView,volunteerFormView};

    }

    private void submit(){

        for(UIFormView form : getRelevantFroms()){
            if (!form.isValid())
                return;
        }

        Account account = null;

        //account
        String email = accountFormView.getEmail();
        String password = accountFormView.getPassword();

        //officer
        String badgeNum;
        String phoneExt;
        String officer_name;
        int rank;

        //volunteer
        String volName;
        String volPhone;

        switch (type){
            case SECRETARY:
                account = new Account(email,3,password);
                break;
            case SUPERUSER:
                account = new Account(email,0,password);
                break;
            case OFFICER:
                badgeNum = officerFormView.getBadgeNum();
                phoneExt = officerFormView.getPhoneExt();
                officer_name = officerFormView.getName();
                rank = officerFormView.getRank();
                account = new Officer(null,email,password,badgeNum,officer_name,phoneExt,rank);
                break;
            case HIGHRANK:
                badgeNum = officerFormView.getBadgeNum();
                phoneExt = officerFormView.getPhoneExt();
                officer_name = officerFormView.getName();
                rank = officerFormView.getRank();
                account = new HighRankOfficer(null,email,password,badgeNum,officer_name,phoneExt,rank);
                break;
            case VOLUNTEER:
                volName = volunteerFormView.getName();
                volPhone = volunteerFormView.getPhone();
                account = new Volunteer(null,email,password,volName,volPhone);
                break;

        }
        onAccountReady(account);
    }

    private UIFormView[] getRelevantFroms(){
        switch (type){
            case HIGHRANK:
            case OFFICER:
                return new UIFormView[]{officerFormView,accountFormView};
            case SECRETARY:
            case SUPERUSER:
                return new UIFormView[]{accountFormView};
            case VOLUNTEER:
                return new UIFormView[]{volunteerFormView,accountFormView};
        }
        return null;
    }

    public void onAccountReady(Account account){

    }

    private void addViewToStack(UIView view){
        if(view == null)
            return;
        formHolder.getChildren().add(view);
    }

    private void cleanFormHolder(){
        formHolder.getChildren().clear();
    }

    public boolean isValid() {
        switch (type){
            case HIGHRANK:
            case OFFICER:
                return accountFormView.isValid() && officerFormView.isValid();
            case VOLUNTEER:
                return accountFormView.isValid() && volunteerFormView.isValid();
            case SUPERUSER:
            case SECRETARY:
                return accountFormView.isValid();
        }
        return false;
    }

    public void reset() {
        accountFormView.reset();
        volunteerFormView.reset();
        officerFormView.reset();
    }


    public enum UserType{
        SUPERUSER("SUPERUSER",0),
        SECRETARY("SECRETARY",3),
        OFFICER("OFFICER",2),
        HIGHRANK("HIGH RANK OFFICER",1),
        VOLUNTEER("VOLUNTEER",4);

        private String value;
        private int role;

        UserType(String value, int role) {
            this.value = value;
            this.role = role;
        }

        @Override
        public String toString() {
            return value;
        }

        public int getRole() {
            return role;
        }
    }

    public AccountFormView getAccountFormView() {
        return accountFormView;
    }
}
