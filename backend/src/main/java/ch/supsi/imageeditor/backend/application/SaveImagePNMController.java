package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.SaveImagePNMModel;
import ch.supsi.imageeditor.backend.business.SaveImagePNMModelInterface;

public class SaveImagePNMController implements SaveImagePNMControllerInterface{
    private static SaveImagePNMController myself;
    private final SaveImagePNMModelInterface saveImagePNMModel;
    private final LoadImagePNMControllerInterface loadImagePNMController;

    protected SaveImagePNMController(){
        saveImagePNMModel = SaveImagePNMModel.getInstance();
        loadImagePNMController = LoadImagePNMController.getInstance();
    }

    public static SaveImagePNMController getInstance() {
        if (myself == null) {
            myself = new SaveImagePNMController();
        }

        return myself;
    }

    @Override
    public void saveImage() {
        saveImagePNMModel.saveImage(loadImagePNMController.getLoadObj());
    }

    @Override
    public void saveAsImage(String absolutePath) {
        saveImagePNMModel.saveAsImage(loadImagePNMController.getLoadObj(), absolutePath);
    }
}
