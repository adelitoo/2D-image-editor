package ch.supsi.imageeditor.backend.application;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

public interface LoadImagePNMControllerInterface {

    Pixel[][] readImage(String filePath) ;

    PNMObject getLoadObj();
}
