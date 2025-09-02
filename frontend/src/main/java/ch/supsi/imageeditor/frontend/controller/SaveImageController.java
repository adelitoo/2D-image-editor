package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.SaveImageModel;
import ch.supsi.imageeditor.frontend.view.SavePNMImageView;
import ch.supsi.imageeditor.frontend.view.SavePNMImageViewInterface;

import java.io.File;
import java.util.ResourceBundle;

public class SaveImageController implements SavePNMImageControllerInterface {
    private static SaveImageController mySelf;
    private final SaveImageModel saveImageModel;
    private final SavePNMImageViewInterface savePNMImageView;

    protected SaveImageController() {
        saveImageModel = SaveImageModel.getInstance();
        savePNMImageView = new SavePNMImageView();
    }

    public static SaveImageController getInstance() {
        if (mySelf == null) {
            mySelf = new SaveImageController();
        }
        return mySelf;
    }

    @Override
    public SaveImageModel getSaveImageModel() {
        return saveImageModel;
    }

    @Override
    public void saveAsImage() {
        File file = savePNMImageView.showSavedPath();
        if (file != null)
            saveImageModel.saveAsImage(file);
    }

    @Override
    public void setBundle(ResourceBundle bundle) {
        saveImageModel.setBundle(bundle);
    }

    @Override
    public void saveImage() {
        saveImageModel.saveImage();
    }

}
