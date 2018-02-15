package controller.report;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import ui.UIListViewCell;
import ui.UIView;
import ui.UIViewController;
import view.DynamicDialog;
import view.cells.CarOwnerCellView;
import view.cells.ViolationViewCell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Antonio Zaitoun on 18/12/2017.
 */
public class ReportViewController extends UIViewController {

    static Map<Integer, ViolationType> violationTypes;
    static {
        violationTypes = new HashMap<>();
        //TODO: load violations from server
    }

    private Vehicle vehicle;
    private Report report;

    private StackPane parentView;

    @FXML
    private Label vd_licensePlate;

    @FXML
    private Label vd_carModel;

    @FXML
    private Label vd_carColor;

    @FXML
    private ListView<VehicleOwner> carOwnersListView;

    @FXML
    private ListView<Violation> violationListView;

    @FXML
    private Button violationAdd;

    @FXML
    private Button positive;

    @FXML
    private Button negative;

    @FXML
    private TextArea description;

    @FXML
    private VBox violationHbox;

    private CancelCallback canceCallback;


    public ReportViewController() {
        super("/resources/xml/controller_report.fxml");
    }

    public ReportViewController(Report report){
        this();
        setReport(report);
    }

    public ReportViewController(Vehicle vehicle){
        this();
        setVehicle(vehicle);
    }

    @Override
    public void viewWillLoad(ResourceBundle bundle) {
        super.viewWillLoad(bundle);

        carOwnersListView.setCellFactory(param-> new CarOwnerCell());
        violationListView.setCellFactory(param -> new ViolationCell());

        negative.setOnAction(event->{
            if(canceCallback != null)
                canceCallback.onCancel();
        });

        violationAdd.setOnAction(event -> createDialog(null));

        violationListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                Violation current = violationListView.getItems().get(violationListView.getSelectionModel().getSelectedIndex());
                createDialog(current);
                Platform.runLater(()->violationListView.getSelectionModel().clearSelection());
            }
        });
    }

    private void createDialog(Violation violation){
        DynamicDialog
                .load("/resources/xml/new_violation_view.fxml")
                .connect()
                .delegate(new ViolationDialogAdapter(violation,this::onViolationAdded))
                .prepare()
                .show(parentView);
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;

        vd_licensePlate.setText(vehicle.getLicensePlate());
        vd_carColor.setText(vehicle.getColorHEX());
        vd_carModel.setText(vehicle.getModel().getName());

        //TODO: get vehicle owners from dingo pro
        new Thread(()->{
            List<VehicleOwner> owners = null;//getOwners(vehicle);
            Platform.runLater(()->{
                if(owners != null){
                    for(VehicleOwner owner : owners){
                        carOwnersListView.getItems().add(owner);
                    }
                }
            });
        }).start();

//        Violation violation = new Violation();
//        violation.setDecision(Decision.UNDETERMINED);
//        violation.setDescription("Just testing");
//        violation.setType(new ViolationType("testing","testing2",0,0,false));
//        violationListView.getItems().add(violation);


    }

    public void setReport(Report report) {

        if(report == null)
            return;

        this.report = report;
        //load data from report to UI.

        String desc = report.getDescription();
        if (desc != null) {
            description.setText(desc);
            description.setWrapText(true);
        }

        violationListView.getItems().addAll(report.getViolations());

        positive.setVisible(false);
        violationHbox.getChildren().remove(violationAdd);
        negative.setText("Close");

        List<VehicleOwner> owners = null;//getOwners(report);
        if(owners != null)
            carOwnersListView.getItems().addAll(owners);

        List<Violation> violations = null;//getViolations(report);
        if(violations != null)
            violationListView.getItems().addAll(violations);

        Vehicle vehicle = null;//getVehicle(report);

        if(vehicle != null){
            vd_licensePlate.setText(vehicle.getLicensePlate());
            vd_carColor.setText(vehicle.getColorHEX());
            vd_carModel.setText(vehicle.getModel().getName());
        }
    }

    public StackPane getParentView() {
        return parentView;
    }

    public void setParentView(StackPane parentView) {
        this.parentView = parentView;
    }

    public void setCanceCallback(CancelCallback canceCallback) {
        this.canceCallback = canceCallback;
    }

    @Override
    public Stage getStage() {
        return null;
    }

    @FunctionalInterface
    public interface CancelCallback {
        void onCancel();
    }

    private void onViolationAdded(Violation violation){
        if(violation != null)
            violationListView
                    .getItems()
                    .add(violation);
    }


    /**
     * Custom inner Classes
     */
    static class CarOwnerCell extends UIListViewCell<VehicleOwner,UIView>{

        @Override
        public UIView load(VehicleOwner item) {
            return new CarOwnerCellView().setVehicleOwner(item);
        }
    }

    static class ViolationCell extends UIListViewCell<Violation,UIView>{

        @Override
        public UIView load(Violation item) {
            return new ViolationViewCell().setViolation(item);
        }
    }




    static class ViolationDialogAdapter implements DynamicDialog.DialogDelegate {

        private TextArea description;
        private ComboBox<ViolationType> violationCombo;
        private Violation violation;
        private JFXRadioButton rd_none;
        private JFXRadioButton rd_video;
        private JFXRadioButton rd_image;
        private VBox file_attach;
        private VBox timestamp;
        private JFXButton attachFile;
        private MediaView mediaView;
        private ImageView imageView;
        private Label fileName;
        private TextField startTime;
        private TextField endTime;
        private HBox attach_controls;

        private FileChooser fileChooser;
        private MediaPlayer player;
        private File file;
        private ViolationCallback callback;
        private int mode = 0;

        private DynamicDialog dialog;

        ViolationDialogAdapter(Violation violation,ViolationCallback callback){
            this.violation = violation;
            this.callback = callback;
        }

        @Override
        public void onHook(Map<String, Node> views, DynamicDialog dialog) {
            this.dialog = dialog;
            //set messages
            dialog.getTitleLabel().setText(violation == null ? "Add Violation" : "View Violation");
            dialog.getNegativeButton().setText(violation == null ? "Cancel" : "Close");
            dialog.getPostiveButton().setText(violation == null ? "Submit" : "Done");
            dialog.getMessageLabel().setText("");

            //bind views
            initViews();

            //setup data sets
            setupFunc();

            //present data
            loadData();
        }

        private void initViews(){
            violationCombo = dialog.findViewById("violationCombo");
            rd_none = dialog.findViewById("rd_none");
            rd_video = dialog.findViewById("rd_video");
            rd_image = dialog.findViewById("rd_image");
            file_attach = dialog.findViewById("file_attach");
            attachFile = dialog.findViewById("attachFile");
            mediaView = dialog.findViewById("mediaView");
            imageView = dialog.findViewById("imageView");
            timestamp = dialog.findViewById("timestamp");
            fileName = dialog.findViewById("fileName");
            description = dialog.findViewById("description");
            startTime = dialog.findViewById("startTime");
            endTime = dialog.findViewById("endTime");
            attach_controls = dialog.findViewById("attach_controls");
        }

        private void setupFunc(){
            violationCombo.setItems(FXCollections.observableArrayList(violationTypes.values()));
            ToggleGroup group = new ToggleGroup();
            rd_none.setToggleGroup(group);
            rd_none.setUserData(0);
            rd_video.setToggleGroup(group);
            rd_video.setUserData(1);
            rd_image.setToggleGroup(group);
            rd_image.setUserData(2);

            group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                int userData = (int) group.getSelectedToggle().getUserData();
                file_attach.setVisible(userData != 0);
                mode = userData;

                imageView.setImage(null);
                mediaView.setMediaPlayer(null);
                fileName.setText("File Name");

                if(userData != 0){
                    timestamp.setVisible(userData == 1);

                    fileChooser.getExtensionFilters().clear();
                    if(userData == 1){
                        //video
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("MP4", "*.mp4")
                        );
                    }else{
                        //image
                        fileChooser.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                                new FileChooser.ExtensionFilter("PNG", "*.png")
                        );
                    }

                }
            });

            //setup file chooser
            fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            //add on click listener
            attachFile.setOnAction(event -> {
                file = fileChooser.showOpenDialog(null);

                if(file == null)
                    return;

                fileName.setText(file.getName());

                if (mode == 1){
                    Media media = new Media(file.toURI().toString());

                    if(player != null)
                        player.stop();

                    player = new MediaPlayer(media);
                    mediaView.setMediaPlayer(player);
                    player.play();
                    player.setOnEndOfMedia(()->{
                        player.seek(Duration.ZERO);
                    });
                }else {
                    try{
                        Image image = new Image(file.toURI().toString());
                        imageView.setImage(image);
                    }catch (Exception ignored){ }
                }

            });
        }

        private void loadData() {
            if(violation == null)
                return;

            dialog.getContentView().setMouseTransparent(true);

            dialog.remove(dialog.getNegativeButton());

            violationCombo.getSelectionModel().select(violationTypes.get(violation.getType().getTypeNum()));

            //description.setText(violation.getDescription());

            //Evidence evidence = getEvidence(violation);

            file_attach.getChildren().remove(attach_controls);
        }



        @Override
        public boolean onDone(DynamicDialog dialog) {
            stopPlayer();

            if(violation == null) {
                //TODO: fetch data from fields
                ComboBox vt = dialog.findViewById("violationCombo");
                String violationType = vt.getSelectionModel().getSelectedItem().toString();

                TextArea desc = dialog.findViewById("description");
                String description = desc.getText();

                //TODO: Create Violation Object
                //Violation newViolation = new Violation();

               // newViolation.setDescription(description);
                //newViolation.setType(violationTypes.get(violationType));

                //TODO: Insert to SQL?

                //callback.onViolationAdded(newViolation);
            }else{
                return true;
            }
            return false;
        }

        @Override
        public void onCancel(DynamicDialog dialog) {
            stopPlayer();
            callback.onViolationAdded(null);
        }

        void stopPlayer(){
            if(player != null)
                player.stop();
        }

        interface ViolationCallback{
            void onViolationAdded(Violation violation);
        }
    }
}
