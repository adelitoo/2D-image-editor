package ch.supsi.imageeditor.backend.dataaccess.PNMWriter;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PGMWriter extends PNMWriter{
    @Override
    public void saveImage(PNMObject obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(obj.getFilePath()))) {
            writeFirstTwoLines(writer, "P2", obj.getWidth(), obj.getHeight());
            writer.write(getMaxVal(obj) + "\n");

            for (int i = 0; i < obj.getHeight(); i++) {
                for (int j = 0; j < obj.getWidth(); j++) {
                    writer.write(obj.getPixels()[i][j].getRed() + " ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    int getMaxVal(PNMObject obj) {
        int maxVal = 0;
        for (int i = 0; i < obj.getHeight(); i++) {
            for (int j = 0; j < obj.getWidth(); j++) {
                Pixel pixel = obj.getPixels()[i][j];
                maxVal = Math.max(maxVal, pixel.getRed());
                maxVal = Math.max(maxVal, pixel.getGreen());
                maxVal = Math.max(maxVal, pixel.getBlue());
            }
        }
        return maxVal;
    }

}
