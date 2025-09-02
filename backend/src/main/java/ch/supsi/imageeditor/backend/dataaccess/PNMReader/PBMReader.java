package ch.supsi.imageeditor.backend.dataaccess.PNMReader;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

import java.util.Scanner;

public class PBMReader extends PNMReader {

    @Override
    public PNMObject readImage(String filepath) {
        PNMObject obj = new PNMObject(filepath);
        Scanner scanner = readFile(filepath);

        if (!scanner.hasNextLine()) {
            throw new IllegalArgumentException("File is empty or improperly formatted");
        }

        String magicNumber = skipEmptyAndCommentLines(scanner);
        if (!magicNumber.equals("P1")) {
            throw new IllegalArgumentException("Not a valid PBM (P1) file");
        }
        obj.setHeader(magicNumber);

        String line = skipEmptyAndCommentLines(scanner);
        String[] dimensions = line.split("\\s+");
        if (dimensions.length != 2) {
            throw new IllegalArgumentException("Invalid dimensions line in PBM file");
        }

        int width;
        int height;
        try {
            width = Integer.parseInt(dimensions[0]);
            height = Integer.parseInt(dimensions[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Dimensions must be integer values");
        }

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive values");
        }
        obj.setWidth(width);
        obj.setHeight(height);
        obj.setMaxVal(1);

        obj.setPixels(new Pixel[height][width]);
        StringBuilder pixelData = new StringBuilder();

        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }
            if (!line.matches("[01\\s]+")) {
                throw new IllegalArgumentException("Invalid pixel data: only '0' and '1' are allowed");
            }
            pixelData.append(line).append(" ");
        }

        String[] pixelValues = pixelData.toString().trim().split("\\s+");
        if (pixelValues.length != width * height) {
            throw new IllegalArgumentException("Pixel data does not match the specified dimensions");
        }

        for (int i = 0; i < pixelValues.length; i++) {
            boolean isBlack;
            if (pixelValues[i].equals("0")) {
                isBlack = false;
            } else {
                isBlack = true;
            }

            int row = i / width;
            int col = i % width;
            obj.getPixels()[row][col] = new Pixel(isBlack);
        }

        scanner.close();
        return obj;
    }
}
