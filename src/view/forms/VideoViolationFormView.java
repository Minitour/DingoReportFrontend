package view.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;


/**
 * Created By Tony on 15/02/2018
 */
public class VideoViolationFormView extends UIFormView {

    @FXML
    private Button attachFile;

    @FXML
    private Label fileName;

    @FXML
    private Pane attach_controls;

    @FXML
    private Pane file_attach;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField startTime;

    @FXML
    private TextField endTime;

    @FXML
    private TextArea description;

    @FXML
    private MediaView mediaView;

    @FXML
    private WebView webView;

    private FileChooser fileChooser;
    private MediaPlayer player;
    private File file;

    public VideoViolationFormView() {
        super("/resources/xml/form_video_violation.fxml");
        init();
    }

    private void init() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4", "*.mp4")
        );

        //add on click listener
        attachFile.setOnAction(event -> {
            file = fileChooser.showOpenDialog(null);

            if(file == null)
                return;

            fileName.setText(file.getName());

            Media media = new Media(file.toURI().toString());

            if(player != null)
                player.stop();

            player = new MediaPlayer(media);
            mediaView.setMediaPlayer(player);
            player.play();
            player.setOnEndOfMedia(()-> player.seek(Duration.ZERO));

        });
    }

    @Override

    public boolean isValid() {
        int from = getFrom();
        return file != null && file.exists() && from >= 0 && getTo() > from;
    }

    @Override
    public void reset() {
        webView.getEngine().load(null);
        if(player != null){
            try {
                player.stop();
                player.dispose();
            }catch (NullPointerException ignored){ }
        }

        file = null;
        fileName.setText("File Name");

    }

    @Override
    public void setFormMode(FormMode formMode) {
        boolean mode = formMode != FormMode.READ_ONLY;
        attachFile.setVisible(mode);
        fileName.setVisible(mode);
        imageView.setVisible(mode);
        //mediaView.setVisible(mode);

//        startTime.setEditable(mode);
//        endTime.setEditable(mode);
       description.setEditable(mode);

        startTime.setDisable(!mode);
        endTime.setDisable(!mode);


        webView.setVisible(!mode);
    }

    //TODO: add getters/setters

    public void setVideoFromUrl(String url){
        webView.setVisible(true);
        mediaView.setVisible(false);
        webView.getEngine().load(url);
    }

    public void setVideoFromLocalFile(File file){
        webView.setVisible(false);
        mediaView.setVisible(true);
        Media media = new Media(file.toURI().toString());

        if(player != null)
            player.stop();

        player = new MediaPlayer(media);
        mediaView.setMediaPlayer(player);
        player.play();
        player.setOnEndOfMedia(()-> player.seek(Duration.ZERO));
    }

    public File getFile(){
        return file;
    }

    public void removeAttachControls(){
        file_attach.getChildren().remove(attach_controls);
    }

    public int getFrom(){
        return stampToSeconds(startTime.getText());
    }

    public int getTo(){
        return stampToSeconds(endTime.getText());
    }

    public void setFrom(int from){
        startTime.setText(secondsToString(from));
    }

    public void setTo(int to){
        endTime.setText(secondsToString(to));
    }

    private String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }


    private int stampToSeconds(String stamp){
        try{
            String[] times= stamp.split(":");
            return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
        }catch (IndexOutOfBoundsException | NumberFormatException e){
            e.printStackTrace();
            return -1;
        }
    }

    public void setDescription(String text) {
        description.setText(text);
    }

    public String getDescription(){
        return description.getText();
    }
}
