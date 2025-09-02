package ch.supsi.imageeditor.backend.dataaccess.PNMReader;

import ch.supsi.imageeditor.backend.business.PNMObject.PNMObject;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;

import java.util.Scanner;

public class PPMReader extends PNMReader {

    @Override
    public PNMObject readImage(String filepath) {
        PNMObject obj = new PNMObject(filepath);
        Scanner scanner = readFile(filepath);

        if (!scanner.hasNextLine()) {
            throw new IllegalArgumentException("File is empty or improperly formatted");
        }

        String magicNumber = skipEmptyAndCommentLines(scanner);
        if (!magicNumber.equals("P3")) {
            throw new IllegalArgumentException("Not a valid PPM (P3) file");
        }
        obj.setHeader(magicNumber);

        String line = skipEmptyAndCommentLines(scanner);
        String[] dimensions = line.split("\\s+");
        if (dimensions.length != 2) {
            throw new IllegalArgumentException("Invalid dimensions line in PPM file");
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
            throw new IllegalArgumentException("Max color value must be an integer");
        }

        if (maxVal <= 0 || maxVal > 255) {
            throw new IllegalArgumentException("Max color value must be between 1 and 255");
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
                throw new IllegalArgumentException("Invalid pixel data: only integer color values allowed");
            }
            pixelData.append(line).append(" ");
        }

        String[] pixelValues = pixelData.toString().trim().split("\\s+");
        if (pixelValues.length != width * height * 3) {
            throw new IllegalArgumentException("Pixel data does not match the specified dimensions");
        }

        for (int i = 0; i < pixelValues.length; i++) {
            int colorValue = Integer.parseInt(pixelValues[i]);
            if (colorValue < 0 || colorValue > maxVal) {
                throw new IllegalArgumentException("Color value " + colorValue + " is outside the range 0 to " + maxVal);
            }

            int index = i % 3;
            int pixelIndex = i / 3;
            int row = pixelIndex / width;
            int col = pixelIndex % width;

            if (index == 0) {
                obj.getPixels()[row][col] = new Pixel(colorValue, 0, 0);
            } else if (index == 1) {
                obj.getPixels()[row][col] = new Pixel(obj.getPixels()[row][col].getRed(), colorValue, 0);
            } else {
                obj.getPixels()[row][col] = new Pixel(obj.getPixels()[row][col].getRed(), obj.getPixels()[row][col].getGreen(), colorValue);
            }
        }

        scanner.close();


        return obj;
    }

}
