package view.forms;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import model.ImageViolation;
import model.VideoViolation;
import model.Violation;
import model.ViolationType;

import java.util.List;

/**
 * Created By Tony on 15/02/2018
 */
public class ViolationFormView extends UIFormView {

    @FXML
    private ComboBox<ViolationType> violationCombo;

    @FXML
    private RadioButton rd_video;

    @FXML
    private RadioButton rd_image;

    @FXML
    private VBox file_attach;

    @FXML
    private Pane parent;

    @FXML
    private Pane type_selector;

    private int type = 0;

    private VideoViolationFormView videoViolationForm = new VideoViolationFormView();
    private ImageViolationForm imageViolationForm = new ImageViolationForm();
    private UIFormView currentForm;
    private List<ViolationType> violationTypes;

    public ViolationFormView(List<ViolationType> violationTypeList) {
        this();
        violationTypes = violationTypeList;
        init();

    }

    public ViolationFormView(Violation violation){
        this();
        init(violation);
    }

    public ViolationFormView(){
        super("/resources/xml/form_violation.fxml");

    }

    private void init(Violation violation){
        int type;
        if(violation instanceof ImageViolation)
            type = 0;
        else
            type = 1;

        violationCombo.getItems().add(violation.getType());
        violationCombo.getSelectionModel().select(0);
        violationCombo.setEditable(false);

        parent.getChildren().remove(type_selector);

        currentForm = (type == 0 ? imageViolationForm : videoViolationForm);
        updateView();

        if(type == 0){
            //image
            imageViolationForm.removeAttachControls();
            imageViolationForm.setFormMode(FormMode.READ_ONLY);
        }else{
            //video
            VideoViolation v = (VideoViolation) violation;

            videoViolationForm.removeAttachControls();
            videoViolationForm.setFormMode(FormMode.READ_ONLY);
            videoViolationForm.setTo(v.getTo());
            videoViolationForm.setFrom(v.getFrom());
            videoViolationForm.setDescription(v.getDescription());

            String url = violation.getEvidenceLink();
            if(url.contains("youtube")){
                videoViolationForm.setVideoFromUrl(url);
            }
        }











    }

    private void init(){
        ToggleGroup group = new ToggleGroup();
        rd_video.setToggleGroup(group);
        rd_video.setUserData(1);
        rd_image.setToggleGroup(group);
        rd_image.setUserData(0);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            int userData = (int) group.getSelectedToggle().getUserData();
            if(currentForm!=null)
                currentForm.reset();
            if(userData == 1){
                //video
                currentForm = videoViolationForm;
            }else {
                //image
                currentForm = imageViolationForm;
            }
            type = userData;
            updateView();
        });
        violationCombo.setItems(FXCollections.observableArrayList(violationTypes));

    }

    private void updateView(){
        file_attach.getChildren().clear();
        if(currentForm != null)
            file_attach
                    .getChildren()
                    .add(currentForm);
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

    private ViolationType getSelectedViolation(){
        return violationCombo.getSelectionModel().getSelectedItem();
    }

    private String getFilePath(){
        return type == 0 ? imageViolationForm.getFile().getPath() :videoViolationForm.getFile().getPath();
    }

    public Violation getViolation(){
        Violation violation;
        if(type == 0){
            violation = new ImageViolation(
                    null,
                    getFilePath(),
                    getSelectedViolation()
            );
        }else{
            violation = new VideoViolation(
                    null,
                    getFilePath(),
                    getSelectedViolation(),
                    videoViolationForm.getFrom(),
                    videoViolationForm.getTo(),
                    videoViolationForm.getDescription()
            );
        }
        return violation;
    }
}
