package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.TransformationModel;

public interface TransformationControllerInterface {
    void addTransformationToPipeline(String selectedTransformation);

    TransformationModel getTransformationModel();

    void clearPipelineListView();

    void applyTransformations();
}
