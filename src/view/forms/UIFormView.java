package view.forms;

import ui.UIView;

/**
 * Created By Tony on 14/02/2018
 */
public abstract class UIFormView extends UIView{

    public UIFormView(String fxml) {
        super(fxml);
    }

    /**
     * A function that checks if all the form fields has valid input.
     * @return true if all the fields have valid input else false.
     */
    public abstract boolean isValid();

    /**
     * A function used to clear the fields and reset them.
     */
    public abstract void reset();

    /**
     * Set the form mode to read only (view) or read+write (editable)
     * @param formMode
     */
    public abstract void setFormMode(FormMode formMode);

    public enum FormMode{
        READ_WRITE,READ_ONLY
    }
}
