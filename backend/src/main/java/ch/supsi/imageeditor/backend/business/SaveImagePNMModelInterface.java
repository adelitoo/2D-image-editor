package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;

public interface SaveImagePNMModelInterface {
    void saveImage(PNMObject loadObj);

    void saveAsImage(PNMObject loadObj, String absolutePath);
}
