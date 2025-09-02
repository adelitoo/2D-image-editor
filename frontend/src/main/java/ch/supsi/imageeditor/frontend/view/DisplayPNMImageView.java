package ch.supsi.imageeditor.frontend.view;

import ch.supsi.imageeditor.frontend.model.dto.PixelView;
import ch.supsi.imageeditor.frontend.observer.ObserverImage;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;


public class DisplayPNMImageView implements ObserverImage, DisplayPNMImageViewInterface {
    private static DisplayPNMImageView mySelf;

    @FXML
    private StackPane stackPane;

    private DisplayPNMImageView() {}

    public static DisplayPNMImageView getInstance() {
        if(mySelf == null)
            mySelf = new DisplayPNMImageView();
        return mySelf;
    }

    @Override
    public File showAndGetFilePath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open PBM File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.pbm", "*.pgm", "*.ppm"));
        return fileChooser.showOpenDialog(null);
    }

    @Override
    public void update(PixelView[][] pixels) {
        int height = pixels.length;
        int width = pixels[0].length;
        stackPane.getChildren().clear();

        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();


        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                gc.setFill(javafx.scene.paint.Color.rgb(
                        Math.min(255, Math.max(0, pixels[y][x].red())),
                        Math.min(255, Math.max(0, pixels[y][x].green())),
                        Math.min(255, Math.max(0, pixels[y][x].blue()))
                ));
                gc.fillRect(x, y, 1, 1);
            }
        }
        stackPane.getChildren().add(canvas);
    }
}
