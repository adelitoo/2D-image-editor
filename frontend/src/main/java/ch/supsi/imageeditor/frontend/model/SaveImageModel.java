package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.backend.application.SaveImagePNMController;
import ch.supsi.imageeditor.backend.application.SaveImagePNMControllerInterface;
import ch.supsi.imageeditor.frontend.observer.InfoBarSubject;

import java.io.File;
import java.util.ResourceBundle;

public class SaveImageModel extends InfoBarSubject implements SaveImageModelInterface {
    private static SaveImageModel instance;
    private ResourceBundle bundle;
    SaveImagePNMControllerInterface saveImagePNMController;

    protected SaveImageModel() {
        saveImagePNMController = SaveImagePNMController.getInstance();
    }

    public static SaveImageModel getInstance() {
        if (instance == null) {
            instance = new SaveImageModel();
        }
        return instance;
    }

    @Override
    public void saveImage() {
        printSavedImageInInfoBar();
        saveImagePNMController.saveImage();
    }

    @Override
    public void setBundle(ResourceBundle bundle){
        this.bundle = bundle;
    }

    @Override
    public void saveAsImage(File file) {
        printSavedImageInInfoBar();
        saveImagePNMController.saveAsImage(file.getAbsolutePath());
    }

    private void printSavedImageInInfoBar() {
        notifyObservers(bundle.getString("label.clickSaveButton"));
    }
}
