package view.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import network.APIManager;
import network.Callbacks;

import java.io.File;
import java.io.InputStream;

/**
 * Created By Tony on 15/02/2018
 */
public class ImageViolationForm extends UIFormView {

    @FXML
    private Button attachFile;

    @FXML
    private Label fileName;

    @FXML
    private ImageView imageView;

    @FXML
    private Pane attach_controls;

    @FXML
    private Pane file_attach;

    private FileChooser fileChooser;
    private MediaPlayer player;
    private File file;

    public ImageViolationForm() {
        super("/resources/xml/form_image_violation.fxml");
        init();
    }

    private void init() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("GIF", "*.gif"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );

        //add on click listener
        attachFile.setOnAction(event -> {
            file = fileChooser.showOpenDialog(null);

            if(file == null)
                return;

            fileName.setText(file.getName());

            try{
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
            }catch (Exception ignored){ }

        });
    }

    @Override
    public boolean isValid() {
        //TODO: implement
        return false;
    }

    @Override
    public void reset() {
        //TODO: implement
    }

    @Override
    public void setFormMode(FormMode formMode) {

    }

    //TODO: add getters/setters

    public void setImageFromUrl(String resource){
        APIManager.getInstance().getResource(resource, (stream, e) -> {
            if(e == null){
                Image image = new Image(stream);
                imageView.setImage(image);
            }
        });
    }

    public void removeAttachControls(){
        file_attach.getChildren().remove(attach_controls);
    }

    public File getFile() {
        return file;
    }
}
