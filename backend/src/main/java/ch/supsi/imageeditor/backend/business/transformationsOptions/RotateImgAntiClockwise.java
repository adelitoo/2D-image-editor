package ch.supsi.imageeditor.backend.business.transformationsOptions;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

public class RotateImgAntiClockwise implements Transformation{
    @Override
    public void doTransformation(PNMObject obj) {
        if (obj.getPixels() == null || obj.getPixels().length == 0) {
            return;
        }

        int originalWidth = obj.getWidth();
        int originalHeight = obj.getHeight();

        Pixel[][] rotatedPixels = new Pixel[originalWidth][originalHeight];
        for (int i = 0; i < originalHeight; i++) {
            for (int j = 0; j < originalWidth; j++) {
                rotatedPixels[originalWidth - 1 - j][i] = obj.getPixels()[i][j];
            }
        }
        obj.setPixels(rotatedPixels);

    }
}
