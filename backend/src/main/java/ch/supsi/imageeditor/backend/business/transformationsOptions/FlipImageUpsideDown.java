package ch.supsi.imageeditor.backend.business.transformationsOptions;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

public class FlipImageUpsideDown implements Transformation{
    @Override
    public void doTransformation(PNMObject obj) {
        if (obj.getPixels() == null || obj.getPixels().length == 0) {
            return;
        }

        int height = obj.getHeight();
        int width = obj.getWidth();
        Pixel[][] pixels = obj.getPixels();

        for (int i = 0; i < height / 2; i++) {
            for (int j = 0; j < width; j++) {
                Pixel temp = pixels[i][j];
                pixels[i][j] = pixels[height - 1 - i][j];
                pixels[height - 1 - i][j] = temp;
            }
        }

        obj.setPixels(pixels);
    }
}
