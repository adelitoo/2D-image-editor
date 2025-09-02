package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.SaveImageModel;

import java.util.ResourceBundle;

public interface SavePNMImageControllerInterface {
    SaveImageModel getSaveImageModel();

    void saveAsImage();

    void setBundle(ResourceBundle bundle);

    void saveImage();
}
