package ch.supsi.imageeditor.backend.business;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.exportImages.ExportFromPGMToPBM;
import ch.supsi.imageeditor.backend.business.exportImages.ExportFromPPMToPGM;
import ch.supsi.imageeditor.backend.dataaccess.PNMWriter.PBMWriter;
import ch.supsi.imageeditor.backend.dataaccess.PNMWriter.PGMWriter;
import ch.supsi.imageeditor.backend.dataaccess.PNMWriter.PNMWriter;
import ch.supsi.imageeditor.backend.dataaccess.PNMWriter.PPMWriter;

public class SaveImagePNMModel implements SaveImagePNMModelInterface {
    private static SaveImagePNMModel myself;
    private PNMWriter pnmWriter;

    protected SaveImagePNMModel() {
    }

    public static SaveImagePNMModel getInstance() {
        if (myself == null) {
            myself = new SaveImagePNMModel();
        }

        return myself;
    }

    @Override
    public void saveImage(PNMObject loadObj) {
        if (loadObj.getFilePath().endsWith(".pbm")) {
            pnmWriter = new PBMWriter();
        } else if (loadObj.getFilePath().endsWith(".pgm")) {
            pnmWriter = new PGMWriter();
        } else if (loadObj.getFilePath().endsWith(".ppm")) {
            pnmWriter = new PPMWriter();
        }
        assert pnmWriter != null;
        pnmWriter.saveImage(loadObj);
    }

    @Override
    public void saveAsImage(PNMObject loadObj, String absolutePath) {
        PNMObject objectTemp = new PNMObject(loadObj);
        pnmWriter = null;
        if (objectTemp.getFilePath().endsWith(".pbm") && absolutePath.endsWith(".pbm")) {
            pnmWriter = new PBMWriter();
        } else if ((objectTemp.getFilePath().endsWith(".pgm") || objectTemp.getFilePath().endsWith(".pbm")) && absolutePath.endsWith(".pgm")) {
            pnmWriter = new PGMWriter();
        } else if ((objectTemp.getFilePath().endsWith(".ppm") || objectTemp.getFilePath().endsWith(".pbm") || objectTemp.getFilePath().endsWith(".pgm")) && absolutePath.endsWith(".ppm")) {
            pnmWriter = new PPMWriter();
        }

        if (pnmWriter != null) {
            objectTemp.setFilepath(absolutePath);
            pnmWriter.saveImage(objectTemp);
        } else {
            if (objectTemp.getFilePath().endsWith(".pgm")) {
                objectTemp.setExportStrategy(new ExportFromPGMToPBM());
            } else {
                objectTemp.setExportStrategy(new ExportFromPPMToPGM());
                if (absolutePath.endsWith(".pbm")) {
                    objectTemp.export();
                    objectTemp.setExportStrategy(new ExportFromPGMToPBM());
                }
            }
            objectTemp.setFilepath(absolutePath);
            objectTemp.export();
            saveImage(objectTemp);
        }
    }

}
