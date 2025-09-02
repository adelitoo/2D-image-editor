package ch.supsi.imageeditor.backend.business.exportImages;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;

public interface ExportInterface {
    void export(PNMObject obj);
}
