package view;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import ui.UIView;

import java.util.ResourceBundle;

/**
 * Created by Antonio Zaitoun on 28/07/2017.
 */
public class DialogView extends UIView {

    /**
     * The title label.
     */
    @FXML
    private Label titleLabel;

    /**
     * The message label.
     */
    @FXML
    private Label messageLabel;

    /**
     * The natural button - on the far left.
     */
    @FXML
    private JFXButton naturalButton;

    /**
     * The positive button - on the left side of the right edge.
     */
    @FXML
    private JFXButton positiveButton;

    /**
     * The negative button - on the far right.
     */
    @FXML
    private JFXButton negativeButton;

    /**
     * The event handler for the positive button.
     */
    private EventHandler postiveEventHandler;

    /**
     * The event handler for the negative button.
     */
    private EventHandler negativeEventHandler;

    /**
     * The event handler for the natural button.
     */
    private EventHandler naturalEventHandler;

    /**
     * The actual JFX Dialog
     */
    protected JFXDialog dialog = new JFXDialog();


    /**
     * Default constructor
     */
    public DialogView(){
        super("/resources/xml/res_dialog_view.fxml");
    }

    /**
     * Show the delegate in a specified view
     * @param root The view in which you wish to display the dialog.
     * @return the instance.
     */
    public DialogView show(StackPane root){
        dialog = new JFXDialog();
        dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
        dialog.setContent(this);
        dialog.setOverlayClose(false);
        dialog.show(root);
        return this;
    }

    @Override
    public void layoutSubviews(ResourceBundle bundle) {
        if (postiveEventHandler == null) {
            positiveButton.setVisible(false);
        }

        if (negativeEventHandler == null) {
            negativeButton.setVisible(false);
        }

        if (naturalEventHandler == null) {
            naturalButton.setVisible(false);
        }
    }


    public void setMessage(String message){
        messageLabel.setText(message);
    }

    public void setTitle(String title){
        titleLabel.setText(title);
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Label getMessageLabel() {
        return messageLabel;
    }

    public JFXButton getNaturalButton() {
        return naturalButton;
    }

    public JFXButton getPostiveButton() {
        return positiveButton;
    }

    public JFXButton getNegativeButton() {
        return negativeButton;
    }

    public void setPostiveEventHandler(EventHandler postiveEventHandler) {
        this.postiveEventHandler = postiveEventHandler;
        positiveButton.setOnAction(postiveEventHandler);
        positiveButton.setVisible(postiveEventHandler != null);
    }

    public void setNegativeEventHandler(EventHandler negativeEventHandler) {
        this.negativeEventHandler = negativeEventHandler;
        negativeButton.setOnAction(negativeEventHandler);
        negativeButton.setVisible(negativeEventHandler != null);
    }

    public void setNaturalEventHandler(EventHandler naturalEventHandler) {
        this.naturalEventHandler = naturalEventHandler;
        naturalButton.setOnAction(naturalEventHandler);
        naturalButton.setVisible(naturalEventHandler != null);
    }
}
