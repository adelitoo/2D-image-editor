package ch.supsi.imageeditor.backend.business.PNMObject;


import ch.supsi.imageeditor.backend.business.exportImages.ExportInterface;

public class PNMObject {
    private String header;
    private int width;
    private int height;
    private int maxVal;
    private Pixel[][] pixels;
    private String filePath;
    private ExportInterface exportStrategy;

    public void export() {
        exportStrategy.export(this);
    }


    public void setExportStrategy(ExportInterface exportStrategy) {
        this.exportStrategy = exportStrategy;
    }

    public ExportInterface getExportStrategy() {
        return exportStrategy;
    }

    public PNMObject(String filePath) {
        this.filePath = filePath;
    }

    public PNMObject(PNMObject obj) {
        this.maxVal = obj.maxVal;
        this.filePath = obj.filePath;
        this.exportStrategy = obj.exportStrategy;
        this.width = obj.width;
        this.header = obj.header;
        this.height = obj.height;

        this.pixels = new Pixel[obj.pixels.length][];
        for (int i = 0; i < obj.pixels.length; i++) {
            this.pixels[i] = obj.pixels[i].clone();
        }
    }

    public int getMaxVal() {
        return maxVal;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pixel[][] getPixels() {
        return pixels;
    }

    public void setPixels(Pixel[][] pixels) {
        this.pixels = pixels;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilepath(String absolutePath) {
        filePath = absolutePath;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getHeader() {
        return header;
    }
}
