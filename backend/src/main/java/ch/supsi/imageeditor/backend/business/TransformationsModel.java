package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.transformationsOptions.Transformation;

import java.util.List;

public class TransformationsModel implements TransformationsModelInterface{
    private static TransformationsModel myself;
    private LoadImagePNMModelInterface loadImagePNMModel;
    protected TransformationsModel(){
        loadImagePNMModel = LoadImagePNMModel.getInstance();
    }

    public static TransformationsModel getInstance() {
        if (myself == null) {
            myself = new TransformationsModel();
        }

        return myself;
    }

    @Override
    public void applyTransformations(List<Transformation> listOfTransformations, PNMObject obj) {
        if (listOfTransformations != null) {
            for (Transformation listOfTransformation : listOfTransformations) {
                listOfTransformation.doTransformation(obj);
            }
        }
        loadImagePNMModel.setLoadObj(obj);
    }
}
