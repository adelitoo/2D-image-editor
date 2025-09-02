package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.LoadImagePNMModel;
import ch.supsi.imageeditor.backend.business.LoadImagePNMModelInterface;
import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

public class LoadImagePNMController implements LoadImagePNMControllerInterface{
    private static LoadImagePNMController myself;
    private final LoadImagePNMModelInterface loadImagePNMModel;

    protected LoadImagePNMController(){ loadImagePNMModel = LoadImagePNMModel.getInstance(); }

    public static LoadImagePNMController getInstance() {
        if (myself == null) {
            myself = new LoadImagePNMController();
        }

        return myself;
    }

    @Override
    public Pixel[][] readImage(String filePath) {
        return loadImagePNMModel.readImage(filePath);
    }

    @Override
    public PNMObject getLoadObj() {
        return loadImagePNMModel.getLoadObj();
    }
}
