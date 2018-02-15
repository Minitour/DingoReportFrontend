package controller.masters;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import network.AutoSignIn;
import ui.UIListViewCell;
import ui.UIView;
import ui.UIViewController;

import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created By Tony on 14/02/2018
 */
public abstract class MasterMenuController extends UIViewController{

    @FXML
    private ListView<String> listView;

    @FXML
    private AnchorPane rightMenu;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Button logout;

    public MasterMenuController() {
        super("/resources/xml/controller_master.fxml");
    }

    @Override
    public void viewWillLoad(ResourceBundle bundle) {
        super.viewWillLoad(bundle);

        usernameLabel.setText(AutoSignIn.EMAIL);

        listView.setCellFactory(param -> new Cell());

        listView.getItems().addAll(Arrays.asList(itemsForMenu()));

        listView.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue)
                -> onListItemChanged(newValue.intValue()));

    }

    public void setOnLogout(EventHandler<ActionEvent> eventHandler){
        logout.setOnAction(eventHandler);
    }

    private void onListItemChanged(int index){
        showView(viewForIndexAt(index));
    }

    public abstract UIView viewForIndexAt(int index);

    public abstract String[] itemsForMenu();

    private void showView(UIView view){
        rightMenu.getChildren().clear();
        if(view != null){
            AnchorPane.setTopAnchor(view,8.0);
            AnchorPane.setBottomAnchor(view,8.0);
            AnchorPane.setRightAnchor(view,8.0);
            AnchorPane.setLeftAnchor(view,8.0);
            rightMenu.getChildren().add(view);
            view.setDelegate(this);
        }
    }

    static class Cell extends UIListViewCell<String, UIView> {


        private String defStyle;
        private String highlighted;

        @Override
        public UIView load(String item) {

            defStyle = getStyle();
            highlighted = "-fx-background-color:  #ecf0f1 !important;-fx-background-radius: 5";
            //setStyle(":selected{-fx-background-color:  #97ff8e !important;}");
            changeBackgroundOnHoverUsingBinding(this);
            return new CellView().setData(item);
        }

        void changeBackgroundOnHoverUsingBinding(Node node) {
            node.styleProperty().bind(
                    Bindings
                            .when(Bindings.or(node.hoverProperty(),node.focusedProperty()))
                            .then(
                                    new SimpleStringProperty(highlighted)
                            )
                            .otherwise(
                                    new SimpleStringProperty(defStyle)
                            )
            );

        }
    }

    static class CellView extends UIView {

        @FXML
        private Label menu;

        public CellView setData(String str) {
            this.menu.setText(str);
            return this;
        }


        @Override
        public void layoutSubviews(ResourceBundle bundle) {
            menu.setTextFill(Color.BLACK);
        }

        @Override
        public void layoutBundle(ResourceBundle bundle) {

        }

        @Override
        public String resource() {
            return "/resources/xml/list_item.fxml";
        }
    }
}
