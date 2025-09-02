package ch.supsi.imageeditor.backend.business.exportImages;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

public class ExportFromPGMToPBM implements ExportInterface {
    @Override
    public void export(PNMObject obj) {
        if ("P2".equals(obj.getHeader())) {
            convertToBinary(obj);
        }
    }

    private void convertToBinary(PNMObject obj) {
        Pixel[][] pixels = obj.getPixels();
        for (int i = 0; i < obj.getHeight(); i++) {
            for (int j = 0; j < obj.getWidth(); j++) {
                Pixel pixel = pixels[i][j];
                int grayValue = pixel.getRed();  // Grayscale: Red, Green, and Blue are the same
                int binaryValue = (grayValue >= 128) ? 255 : 0; // 255 for white, 0 for black
                pixels[i][j] = new Pixel(binaryValue, binaryValue, binaryValue); // Binary in RGB format
            }
        }
        obj.setMaxVal(1);
        obj.setHeader("P1");
    }

}
