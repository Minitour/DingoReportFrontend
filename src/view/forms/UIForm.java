package view.forms;

/**
 * Created By Tony on 14/02/2018
 */
public interface UIForm {
    /**
     * A function that checks if all the form fields has valid input.
     * @return true if all the fields have valid input else false.
     */
    boolean isValid();

    /**
     * A function used to clear the fields and reset them.
     */
    void reset();
}
