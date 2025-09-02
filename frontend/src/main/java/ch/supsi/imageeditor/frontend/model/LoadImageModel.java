package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.backend.application.LoadImagePNMController;
import ch.supsi.imageeditor.backend.application.LoadImagePNMControllerInterface;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import ch.supsi.imageeditor.frontend.model.dto.PixelView;
import ch.supsi.imageeditor.frontend.observer.ImageSubject;
import ch.supsi.imageeditor.frontend.observer.InfoBarSubject;
import ch.supsi.imageeditor.frontend.observer.ObserverInfoBar;

import java.util.ResourceBundle;


public class LoadImageModel extends ImageSubject implements LoadImageModelInterface {
    private static LoadImageModel instance;
    private ResourceBundle resourceBundle;
    private final LoadImagePNMControllerInterface loadImagePNMController;
    private final InfoBarSubject infoBarSubject;

    protected LoadImageModel() {
        loadImagePNMController = LoadImagePNMController.getInstance();
        infoBarSubject = new InfoBarSubject();
    }

    public static LoadImageModel getInstance() {
        if (instance == null) {
            instance = new LoadImageModel();
        }
        return instance;
    }

    @Override
    public boolean readImage(String filePath) {
        try {
            Pixel[][] pixels = loadImagePNMController.readImage(filePath);
            PixelView[][] pixelViews = convertToPixelView(pixels);
            notifyObservers(pixelViews);
            infoBarSubject.notifyObservers(resourceBundle.getString("label.imageLoaded") + filePath);
            return false;
        } catch (IllegalArgumentException e) {
            infoBarSubject.notifyObservers(e.getMessage() + ": " + filePath);
            return true;
        }
    }

    public PixelView[][] convertToPixelView(Pixel[][] pixels) {
        if (pixels != null) {
            int height = pixels.length;
            int width = pixels[0].length;
            PixelView[][] pixelViews = new PixelView[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    pixelViews[y][x] = new PixelView(pixels[y][x].getRed(),
                            pixels[y][x].getGreen(),
                            pixels[y][x].getBlue());
                }
            }
            return pixelViews;
        } else {
            throw new IllegalArgumentException("Cannot read the image");
        }
    }

    @Override
    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public void registerInfoBarObserver(ObserverInfoBar observerInfoBar) {
        infoBarSubject.registerObserver(observerInfoBar);
    }

}

