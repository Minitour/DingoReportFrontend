package view.forms;

import ui.UIView;

/**
 * Created By Tony on 15/02/2018
 */
public class ImageViolationForm extends UIView implements UIForm {

    public ImageViolationForm() {
        super("/resources/xml/form_image_violation.fxml");
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void reset() {

    }
}
