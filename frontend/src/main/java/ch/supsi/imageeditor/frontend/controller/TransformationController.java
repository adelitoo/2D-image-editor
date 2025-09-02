package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.TransformationModel;
import ch.supsi.imageeditor.frontend.model.TransformationModelInterface;
public class TransformationController implements TransformationControllerInterface{
    private static TransformationController mySelf;
    private final TransformationModelInterface transformationModel;
    protected TransformationController() {
        transformationModel = TransformationModel.getInstance();
        LanguageControllerInterface languageController = LanguageController.getInstance();
        transformationModel.setResourceBundle(languageController.getResourceBundle());
    }

    public static TransformationController getInstance(){
        if (mySelf == null){
            mySelf = new TransformationController();
        }
        return mySelf;
    }

    @Override
    public void addTransformationToPipeline(String selectedTransformation) {
        transformationModel.addTransformation(selectedTransformation);
    }

    @Override
    public TransformationModel getTransformationModel() {
        return (TransformationModel) transformationModel;
    }

    @Override
    public void clearPipelineListView() {
        transformationModel.clearPipelineListView();
    }

    @Override
    public void applyTransformations() {
        transformationModel.applyTransformations();
    }

}
