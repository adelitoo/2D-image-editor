package ch.supsi.imageeditor.frontend.controller;


import ch.supsi.imageeditor.frontend.model.LoadImageModel;
import ch.supsi.imageeditor.frontend.view.DisplayPNMImageViewInterface;

public interface LoadImageControllerInterface {
    boolean loadImage();
    void setDisplayPNMImageView(DisplayPNMImageViewInterface displayPNMImageView);
    LoadImageModel getLoadImageModel();
}
