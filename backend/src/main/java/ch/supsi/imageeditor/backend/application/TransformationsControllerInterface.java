package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

import java.util.List;

public interface TransformationsControllerInterface {

    Pixel[][] applyTransformations(List<String> listOfTransformations);
}
