package ch.supsi.imageeditor.backend.application;


import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import ch.supsi.imageeditor.backend.business.TransformationsModel;
import ch.supsi.imageeditor.backend.business.TransformationsModelInterface;
import ch.supsi.imageeditor.backend.business.transformationsOptions.ConverterTransformationInterface;
import ch.supsi.imageeditor.backend.business.transformationsOptions.ConverterTransformations;

import java.util.List;
import java.util.ResourceBundle;

public class TransformationsController implements TransformationsControllerInterface {
    private static TransformationsController myself;
    private final ResourceBundle resourceBundle;
    private final LoadImagePNMControllerInterface loadImagePNMController;
    private final PreferencesControllerInterface preferencesController;
    private final TransformationsModelInterface transformationsModel;
    private final ConverterTransformationInterface converterTransformation;

    protected TransformationsController(ConverterTransformations converterTransformations) {
        preferencesController = PreferencesController.getInstance();
        transformationsModel = TransformationsModel.getInstance();
        loadImagePNMController = LoadImagePNMController.getInstance();
        resourceBundle = preferencesController.getPreference();
        this.converterTransformation = converterTransformations;
    }

    public static TransformationsController getInstance() {
        if (myself == null) {
            myself = new TransformationsController(new ConverterTransformations());
        }

        return myself;
    }

    @Override
    public Pixel[][] applyTransformations(List<String> listOfTransformations) {
        PNMObject obj = loadImagePNMController.getLoadObj();
        if (obj != null) {
            transformationsModel.applyTransformations(converterTransformation.convertList(listOfTransformations, resourceBundle), obj);
            return obj.getPixels();
        } else {
            return null;
        }
    }
}
