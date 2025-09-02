package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

public interface LoadImagePNMModelInterface {
    Pixel[][] readImage(String filePath) ;

    PNMObject getLoadObj();

    void setLoadObj(PNMObject obj);
}
