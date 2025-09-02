package ch.supsi.imageeditor.backend.business.exportImages;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

public class ExportFromPPMToPGM implements ExportInterface{
    @Override
    public void export(PNMObject obj) {
        if ("P3".equals(obj.getHeader())) {
            convertToGrayscale(obj);
        }
    }

    private void convertToGrayscale(PNMObject obj) {
        Pixel[][] pixels = obj.getPixels();
        for (int i = 0; i < obj.getHeight(); i++) {
            for (int j = 0; j < obj.getWidth(); j++) {
                Pixel pixel = pixels[i][j];
                int grayValue = (int) (0.299 * pixel.getRed() + 0.587 * pixel.getGreen() + 0.114 * pixel.getBlue());
                grayValue = Math.max(0, Math.min(255, grayValue));
                pixels[i][j] = new Pixel(grayValue);
            }
        }
        obj.setHeader("P2");
    }
}
