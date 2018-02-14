package view.forms;

import ui.UIView;

/**
 * Created By Tony on 15/02/2018
 */
public class VideoViolationForm extends UIView implements UIForm {

    public VideoViolationForm() {
        super("/resources/xml/form_video_violation.fxml");
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void reset() {

    }
}
