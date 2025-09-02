package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.transformationsOptions.Transformation;

import java.util.List;

public interface TransformationsModelInterface {
    void applyTransformations(List<Transformation> listOfTransformations, PNMObject obj);
}
