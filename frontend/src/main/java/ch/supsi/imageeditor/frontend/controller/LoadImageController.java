package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.LoadImageModel;
import ch.supsi.imageeditor.frontend.model.LoadImageModelInterface;
import ch.supsi.imageeditor.frontend.view.DisplayPNMImageViewInterface;

import java.io.File;

public class LoadImageController implements LoadImageControllerInterface {
    private static LoadImageController mySelf;
    private final LoadImageModelInterface loadImageModel;
    private final LanguageControllerInterface languageController;
    private DisplayPNMImageViewInterface displayPNMImageView;

    protected LoadImageController() {
        loadImageModel = LoadImageModel.getInstance();
        languageController = LanguageController.getInstance();
        loadImageModel.setResourceBundle(languageController.getResourceBundle());
    }

    public static LoadImageController getInstance() {
        if (mySelf == null) {
            mySelf = new LoadImageController();
        }
        return mySelf;
    }

    @Override
    public LoadImageModel getLoadImageModel() {return (LoadImageModel) loadImageModel;}

    @Override
    public boolean loadImage() {
        File file = displayPNMImageView.showAndGetFilePath();
        if (file != null) {
            String filePath = file.getAbsolutePath();
            return loadImageModel.readImage(filePath);
        }
        return true;
    }

    @Override
    public void setDisplayPNMImageView(DisplayPNMImageViewInterface displayPNMImageView) {
        this.displayPNMImageView = displayPNMImageView;
    }


    public DisplayPNMImageViewInterface getDisplayPNMImageView() {
        return displayPNMImageView;
    }
}
