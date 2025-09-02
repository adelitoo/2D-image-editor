package ch.supsi.imageeditor.backend.business.transformationsOptions;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;

public interface Transformation {
    void doTransformation(PNMObject obj);
}
