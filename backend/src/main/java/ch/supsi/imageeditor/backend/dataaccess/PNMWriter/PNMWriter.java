package ch.supsi.imageeditor.backend.dataaccess.PNMWriter;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;

import java.io.BufferedWriter;
import java.io.IOException;


public abstract class PNMWriter{
    public void writeFirstTwoLines(BufferedWriter writer, String p, int width, int height){
        try {
            writer.write(p + "\n");
            writer.write(width + " " + height + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public abstract void saveImage(PNMObject obj);

}
