package ch.supsi.imageeditor.frontend.view;

import javafx.stage.FileChooser;

import java.io.File;

public class SavePNMImageView implements SavePNMImageViewInterface{
    @Override
    public File showSavedPath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.pbm", "*.pgm", "*.ppm"));
        return fileChooser.showSaveDialog(null);
    }

}
