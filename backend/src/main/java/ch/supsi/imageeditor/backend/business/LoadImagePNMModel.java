package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PBMReader;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PGMReader;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PNMReader;
import ch.supsi.imageeditor.backend.dataaccess.PNMReader.PPMReader;

public class LoadImagePNMModel implements LoadImagePNMModelInterface {
    private static LoadImagePNMModel myself;
    private PNMObject pnmObject;

    protected LoadImagePNMModel() {
    }

    public static LoadImagePNMModel getInstance() {
        if (myself == null) {
            myself = new LoadImagePNMModel();
        }

        return myself;
    }


    @Override
    public Pixel[][] readImage(String filePath) {
        PNMReader reader;
        if (filePath.endsWith(".pbm")) {
            reader = new PBMReader();
        } else if (filePath.endsWith(".pgm")) {
            reader = new PGMReader();
        } else if (filePath.endsWith(".ppm")) {
            reader = new PPMReader();
        } else {
            throw new IllegalArgumentException("Unsupported file type.");
        }
        pnmObject = reader.readImage(filePath);
        return pnmObject.getPixels();
    }

    @Override
    public PNMObject getLoadObj() {
        return pnmObject;
    }

    @Override
    public void setLoadObj(PNMObject obj) {
        pnmObject = obj;
    }
}
