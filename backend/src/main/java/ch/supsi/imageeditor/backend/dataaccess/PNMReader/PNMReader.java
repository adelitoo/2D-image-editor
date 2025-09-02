package ch.supsi.imageeditor.backend.dataaccess.PNMReader;


import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class PNMReader implements PNMReaderInterface{
    @Override
    public Scanner readFile(String filepath){
        File file = new File(filepath);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return scanner;
    }

    public abstract PNMObject readImage(String filepath);

    protected String skipEmptyAndCommentLines(Scanner scanner) {
        String line;
        do {
            if (!scanner.hasNextLine()) {
                throw new IllegalArgumentException("Unexpected end of file");
            }
            line = scanner.nextLine().trim();
        } while (line.isEmpty() || line.startsWith("#"));
        return line;
    }

}
