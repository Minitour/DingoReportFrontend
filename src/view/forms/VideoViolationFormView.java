package view.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
        return file != null && file.exists();
    }

    @Override
    public void reset() {
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
        mediaView.setVisible(mode);
        startTime.setEditable(mode);
        endTime.setEditable(mode);
        description.setEditable(mode);

        webView.setVisible(!mode);
    }

    //TODO: add getters/setters

    public void setVideoFromUrl(String url){
        webView.getEngine().load(url);
    }

    public File getFile(){
        return file;
    }


}
