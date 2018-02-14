package view;

import com.jfoenix.controls.JFXDialog;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ui.UIView;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Antonio Zaitoun on 20/08/2017.
 */

/**
 * Dynamic Dialog is a class which extends DialogView to assist in re-using existing views and displaying them in a pop-up like dialog.
 *
 *
 * `LOAD`
 *      There are 2 ways to create an instance:
 *      - Using FXML
 *      - Using UIView
 *
 * `CONNECT`
 *      The second step would be specifying the view which we would like to auto-load.
 *      This step is optional and is only valid if you choose to use FXML in the first step.
 *
 * `DELEGATE`
 *      The third step would be setting the delegate that is in-charge of the behavior of the dialog.
 *      The delegate is in charge of the business.
 *
 * `PREPARE`
 *      Call this to prepare the dialog. This will activate the `setOnHook` method in the Delegate
 *
 * `SHOW`
 *      Finally the dialog is ready, all is left is to call the show function.
 *
 */
public class DynamicDialog extends DialogView {

    /**
     * The content view
     */
    private final UIView content;

    /**
     * FXML (Nullable)
     */
    private String fxml = null;

    /**
     * Pre-defined keys
     */
    private String[] viewKeys;

    /**
     * Loaded views
     */
    private Map<String,Node> views;

    /**
     * The callback delegate
     */
    private DialogDelegate delegate;

    /**
     * Create Dynamic Dialog from FXML
     *
     * @param fxml
     * @return a new instance from FXML.
     */
    public static DynamicDialog load(String fxml){
        return new DynamicDialog(fxml);
    }

    /**
     * Create Dynamic Dialog with initialized view
     *
     * @param view
     * @return a new instance with view.
     */
    public static DynamicDialog load(UIView view){
        return new DynamicDialog(view);
    }

    /**
     * Connect with pre-defined IDs from the FXML
     * @param viewKeys a list of FXML ids you wish to pre-load.
     * @return the instance.
     */
    public DynamicDialog connect(String... viewKeys){
        this.viewKeys = viewKeys;
        return this;
    }

    /**
     *
     * @param callback
     * @return the instance.
     */
    public DynamicDialog setDelegate(DialogDelegate callback){
        delegate = callback;
        return this;
    }



    /**
     * Create instance from FXML
     * @param fxml the fxml which you would like to load.
     */
    public DynamicDialog(String fxml){
        super();
        this.fxml = fxml;
        content = new UIView() {
            @Override
            public void layoutSubviews(ResourceBundle bundle) {}

            @Override
            public void layoutBundle(ResourceBundle bundle) {}

            @Override
            public String resource() {
                return fxml;
            }
        };

        ((VBox) findViewById("contentView")).getChildren().add(content);
    }

    @Override
    public DialogView show(StackPane root) {
        super.show(root);
        setPostiveEventHandler(event -> {
            boolean shouldClose = DynamicDialog.this.delegate.onDone(DynamicDialog.this);
            if (shouldClose)
                dialog.close();
        });

        setNegativeEventHandler(event -> {
            DynamicDialog.this.delegate.onCancel(DynamicDialog.this);
            dialog.close();
        });
        return this;
    }

    /**
     * Create instance from existing view.
     * @param view the view from which you would like to create the instance.
     */
    public DynamicDialog(UIView view){
        super();
        content = view;
        ((VBox) findViewById("contentView")).getChildren().add(content);
    }

    /**
     * Get the content view.
     * @return The loaded view.
     */
    public UIView getContentView() {
        return content;
    }

    @Override
    public void layoutBundle(ResourceBundle bundle) {
        super.layoutBundle(bundle);
        delegate.onLayoutBundle(bundle,this);
    }

    /**
     * Prepare the dialog.
     */
    public DynamicDialog prepare(){
        views = new HashMap<>();
        if(viewKeys != null)
            for (String key : viewKeys) {
                views.put(key,getElementById(key));
            }
        if(delegate != null)
            delegate.onHook(views,this);
        return this;
    }

    /**
     * Close the dialog.
     */
    public void close(){
        dialog.close();
    }

    /**
     * Fetch the view by it's fxml id.
     * @param identifier The FXML id.
     * @param <E> Node subclass
     * @return The Node if found else null.
     */
    public <E extends Node> E getElementById(String identifier){
        return super.findViewById(identifier);
    }

    /**
     * Dialog Delegate - Used to control the dialog's behavior.
     */
    public interface DialogDelegate {
        void onHook(Map<String, Node> views, DynamicDialog dialog);

        boolean onDone(DynamicDialog dialog);

        default void onCancel(DynamicDialog dialog){};

        default void onLayoutBundle(ResourceBundle bundle, DynamicDialog dialog){}
    }
}
