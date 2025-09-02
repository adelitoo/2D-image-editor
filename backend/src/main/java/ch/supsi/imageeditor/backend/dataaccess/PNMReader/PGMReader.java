package ch.supsi.imageeditor.backend.dataaccess.PNMReader;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

import java.util.Scanner;

public class PGMReader extends PNMReader {

    @Override
    public PNMObject readImage(String filepath) {
        PNMObject obj = new PNMObject(filepath);
        Scanner scanner = readFile(filepath);

        if (!scanner.hasNextLine()) {
            throw new IllegalArgumentException("File is empty or improperly formatted");
        }

        String magicNumber = skipEmptyAndCommentLines(scanner);
        if (!magicNumber.equals("P2")) {
            throw new IllegalArgumentException("Not a valid PGM (P2) file");
        }
        obj.setHeader(magicNumber);

        String line = skipEmptyAndCommentLines(scanner);
        String[] dimensions = line.split("\\s+");
        if (dimensions.length != 2) {
            throw new IllegalArgumentException("Invalid dimensions line in PGM file");
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

        int maxVal;
        try {
            maxVal = Integer.parseInt(skipEmptyAndCommentLines(scanner).trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Max grayscale value must be an integer");
        }

        if (maxVal <= 0 || maxVal > 255) {
            throw new IllegalArgumentException("Max grayscale value must be between 1 and 255");
        }
        obj.setMaxVal(maxVal);

        obj.setPixels(new Pixel[height][width]);
        StringBuilder pixelData = new StringBuilder();

        while (scanner.hasNextLine()) {
            line = scanner.nextLine().trim();
            if (line.startsWith("#") || line.isEmpty()) {
                continue;
            }
            if (!line.matches("[0-9\\s]+")) {
                throw new IllegalArgumentException("Invalid pixel data: only integer grayscale values allowed");
            }
            pixelData.append(line).append(" ");
        }

        String[] pixelValues = pixelData.toString().trim().split("\\s+");
        if (pixelValues.length != width * height) {
            throw new IllegalArgumentException("Pixel data does not match the specified dimensions");
        }

        for (int i = 0; i < pixelValues.length; i++) {
            int grayValue;
            grayValue = Integer.parseInt(pixelValues[i]);
            if (grayValue < 0 || grayValue > maxVal) {
                throw new IllegalArgumentException("Pixel value " + grayValue + " is outside the range 0 to " + maxVal);
            }

            int row = i / width;
            int col = i % width;
            obj.getPixels()[row][col] = new Pixel(grayValue);
        }

        scanner.close();
        return obj;
    }
}
