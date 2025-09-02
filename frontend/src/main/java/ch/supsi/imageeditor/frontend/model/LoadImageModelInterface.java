package ch.supsi.imageeditor.frontend.model;

import java.util.ResourceBundle;

public interface LoadImageModelInterface {
    boolean readImage(String filePath);


    void setResourceBundle(ResourceBundle resourceBundle);
}
