package ch.supsi.imageeditor.backend.business.PNMObject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PixelTest {

    @Test
    public void constructor() {
        Pixel pixel = new Pixel(1);
        Assertions.assertNotNull(pixel);
    }

    @Test
    void testPixelWithBlack() {
        Pixel blackPixel = new Pixel(true);

        assertEquals(0, blackPixel.getRed(), "Red value should be 0 for black pixel");
        assertEquals(0, blackPixel.getGreen(), "Green value should be 0 for black pixel");
        assertEquals(0, blackPixel.getBlue(), "Blue value should be 0 for black pixel");
    }

    @Test
    void testPixelWithWhite() {
        Pixel whitePixel = new Pixel(false);

        assertEquals(255, whitePixel.getRed(), "Red value should be 255 for white pixel");
        assertEquals(255, whitePixel.getGreen(), "Green value should be 255 for white pixel");
        assertEquals(255, whitePixel.getBlue(), "Blue value should be 255 for white pixel");
    }

    @Test
    void testPixelWithGray() {
        int grayValue = 128;
        Pixel grayPixel = new Pixel(grayValue);

        assertEquals(grayValue, grayPixel.getRed(), "Red value should be 128 for gray pixel");
        assertEquals(grayValue, grayPixel.getGreen(), "Green value should be 128 for gray pixel");
        assertEquals(grayValue, grayPixel.getBlue(), "Blue value should be 128 for gray pixel");
    }

    @Test
    void testPixelWithRGB() {
        int red = 100, green = 150, blue = 200;
        Pixel rgbPixel = new Pixel(red, green, blue);

        assertEquals(red, rgbPixel.getRed(), "Red value should be 100 for RGB pixel");
        assertEquals(green, rgbPixel.getGreen(), "Green value should be 150 for RGB pixel");
        assertEquals(blue, rgbPixel.getBlue(), "Blue value should be 200 for RGB pixel");
    }

    @Test
    void testSetters() {
        Pixel pixel = new Pixel(255);

        pixel.setRed(100);
        pixel.setGreen(150);
        pixel.setBlue(200);

        assertEquals(100, pixel.getRed(), "Red value should be 100 after setting");
        assertEquals(150, pixel.getGreen(), "Green value should be 150 after setting");
        assertEquals(200, pixel.getBlue(), "Blue value should be 200 after setting");
    }

    @Test
    void testToString() {
        Pixel pixel = new Pixel(100, 150, 200);

        assertEquals("(100, 150, 200)", pixel.toString(), "toString should return the correct RGB values");
    }
}
