package ch.supsi.imageeditor.backend.dataaccess.PNMWriter;


import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PBMWriter extends PNMWriter {

    @Override
    public void saveImage(PNMObject obj) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(obj.getFilePath()))) {
            writeFirstTwoLines(writer, "P1", obj.getWidth(), obj.getHeight());

            for (int i = 0; i < obj.getHeight(); i++) {
                for (int j = 0; j < obj.getWidth(); j++) {
                    writer.write(obj.getPixels()[i][j].getRed() == 0 ? "0 " : "1 ");
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}