package ch.supsi.imageeditor.backend.dataaccess.PNMReader;

import java.util.Scanner;

public interface PNMReaderInterface {
    Scanner readFile(String filepath);
}
