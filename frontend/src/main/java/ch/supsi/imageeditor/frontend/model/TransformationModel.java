package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.backend.application.TransformationsController;
import ch.supsi.imageeditor.backend.application.TransformationsControllerInterface;
import ch.supsi.imageeditor.backend.business.PNMObject.Pixel;
import ch.supsi.imageeditor.frontend.model.dto.PixelView;
import ch.supsi.imageeditor.frontend.observer.ImageSubject;
import ch.supsi.imageeditor.frontend.observer.InfoBarSubject;
import ch.supsi.imageeditor.frontend.observer.TransformationsSubject;
import ch.supsi.imageeditor.frontend.view.DisplayPNMImageView;
import ch.supsi.imageeditor.frontend.view.InfoBarView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TransformationModel extends TransformationsSubject implements TransformationModelInterface {
    private static TransformationModel mySelf;
    private final List<String> listOfTransformations = new ArrayList<>();
    private final TransformationsControllerInterface transformationsController;
    private ResourceBundle resourceBundle;
    private final ImageSubject imageSubject;
    private final InfoBarSubject infoBarSubject;

    protected TransformationModel() {
        transformationsController = TransformationsController.getInstance();
        imageSubject = new ImageSubject();
        infoBarSubject = new InfoBarSubject();
    }

    public static TransformationModel getInstance() {
        if (mySelf == null) {
            mySelf = new TransformationModel();
        }
        return mySelf;
    }

    @Override
    public void addTransformation(String selectedTransformation) {
        if (selectedTransformation != null) {
            try {
                listOfTransformations.add(selectedTransformation);
                notifyObservers(listOfTransformations);
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown transformation selected: " + selectedTransformation);
            }
        } else {
            System.out.println("No transformation selected.");
        }
    }

    @Override
    public void clearPipelineListView() {
        if(listOfTransformations.isEmpty()){
            infoBarSubject.notifyObservers(resourceBundle.getString("label.nothingToDelete"));
        } else {
            listOfTransformations.clear();
            notifyObservers(listOfTransformations);
        }
    }

    @Override
    public void applyTransformations() {
        Pixel[][] pixels = transformationsController.applyTransformations(listOfTransformations);
        if(pixels != null) {
            if(listOfTransformations.isEmpty()){
                infoBarSubject.notifyObservers(resourceBundle.getString("label.addTransformation"));
            } else {
                PixelView[][] pixelViews = convertToPixelView(pixels);
                imageSubject.notifyObservers(pixelViews);
                clearPipelineListView();
                infoBarSubject.notifyObservers(resourceBundle.getString("label.transformationCompleted"));
            }
        } else {
            infoBarSubject.notifyObservers(resourceBundle.getString("label.imageNotInitialized"));
        }
    }

    @Override
    public void setObserverImageDisplay(DisplayPNMImageView displayPNMImageView) {
        imageSubject.registerObserver(displayPNMImageView);
    }

    @Override
    public void setObserverInfoBar(InfoBarView infoBarView) {
        infoBarSubject.registerObserver(infoBarView);
    }

    private PixelView[][] convertToPixelView(Pixel[][] pixels) {
        if(pixels != null) {
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
        }else {
            throw new IllegalArgumentException("Cannot read the image");
        }
    }

    @Override
    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle;
    }
}
