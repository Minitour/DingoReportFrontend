package view.forms;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.Violation;
import model.ViolationType;
import ui.UIView;

import java.util.List;

/**
 * Created By Tony on 15/02/2018
 */
public class ViolationForm extends UIView implements UIForm {

    @FXML
    private ComboBox<ViolationType> violationCombo;

    @FXML
    private RadioButton rd_video;

    @FXML
    private RadioButton rd_image;

    @FXML
    private VBox file_attach;

    private VideoViolationForm videoViolationForm = new VideoViolationForm();
    private ImageViolationForm imageViolationForm = new ImageViolationForm();
    private UIForm currentForm;
    private UIView currentView;
    private List<ViolationType> violationTypes;

    public ViolationForm(List<ViolationType> violationTypeList) {
        super("/resources/xml/form_violation.fxml");
        violationTypes = violationTypeList;
        init();

    }

    private void init(){
        ToggleGroup group = new ToggleGroup();
        rd_video.setToggleGroup(group);
        rd_video.setUserData(1);
        rd_image.setToggleGroup(group);
        rd_image.setUserData(2);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            int userData = (int) group.getSelectedToggle().getUserData();
            if(currentForm!=null)
                currentForm.reset();
            if(userData == 1){
                //video
                currentForm = videoViolationForm;
                currentView = videoViolationForm;
            }else {
                //image
                currentForm = imageViolationForm;
                currentView = imageViolationForm;
            }
            updateView();
        });
        violationCombo.setItems(FXCollections.observableArrayList(violationTypes));

    }

    private void updateView(){
        file_attach.getChildren().clear();
        if(currentView != null)
            file_attach
                    .getChildren()
                    .add(currentView);
    }

    @Override
    public boolean isValid() {
        return currentForm != null && currentForm.isValid();
    }


    @Override
    public void reset() {
        //TODO: implement reset
    }

    @Override
    public void setFormMode(FormMode formMode) {
        //TODO: implement form mode
    }

    public Violation getViolation(){
        //TODO: implement get violation.
        return null;
    }
}
