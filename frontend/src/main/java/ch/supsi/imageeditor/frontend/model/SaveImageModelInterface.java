package ch.supsi.imageeditor.frontend.model;

import java.io.File;
import java.util.ResourceBundle;

public interface SaveImageModelInterface {
    void saveImage();

    void setBundle(ResourceBundle bundle);

    void saveAsImage(File file);
}
