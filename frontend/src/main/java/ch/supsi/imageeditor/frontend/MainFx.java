package ch.supsi.imageeditor.frontend;

import ch.supsi.imageeditor.frontend.controller.*;
import ch.supsi.imageeditor.frontend.view.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainFx extends Application {
    private final LanguageControllerInterface languageController = LanguageController.getInstance();
    private final SaveImageController saveImageController = SaveImageController.getInstance();
    private final LoadImageControllerInterface loadImageController = LoadImageController.getInstance();
    private final HelpController helpController = HelpController.getInstance();
    private final TransformationController transformationController = TransformationController.getInstance();

    @Override
    public void start(Stage stage) throws IOException {
        ResourceBundle resourceBundle = languageController.getResourceBundle();
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("/menubar.fxml"), resourceBundle);
        FXMLLoader transformationsLoader = new FXMLLoader(getClass().getResource("/availabletransformations.fxml"), resourceBundle);
        FXMLLoader pipelineLoader = new FXMLLoader(getClass().getResource("/pipelinetransformations.fxml"), resourceBundle);
        FXMLLoader infoBarLoader = new FXMLLoader(getClass().getResource("/infobar.fxml"), resourceBundle);
        FXMLLoader aboutLoader = new FXMLLoader(getClass().getResource("/about.fxml"), resourceBundle);
        FXMLLoader displayImageLoader = new FXMLLoader(getClass().getResource("/imagebox.fxml"));

        saveImageController.setBundle(resourceBundle);
        helpController.setBundle(resourceBundle);
        DisplayPNMImageView displayPNMImageView = DisplayPNMImageView.getInstance();
        displayImageLoader.setController(displayPNMImageView);
        VBox centerImageBox = displayImageLoader.load();

        DisplayPipelineView displayPipelineView = DisplayPipelineView.getInstance();
        transformationController.getTransformationModel().registerObserver(displayPipelineView);


        loadImageController.setDisplayPNMImageView(displayPNMImageView);
        loadImageController.getLoadImageModel().registerObserver(displayPNMImageView);

        transformationController.getTransformationModel().setObserverImageDisplay(displayPNMImageView);

        BorderPane root = new BorderPane();
        root.setTop(menuLoader.load());

        HBox centerBox = new HBox();
        centerBox.setSpacing(0);

        VBox transformationsBox = transformationsLoader.load();
        VBox pipelineBox = pipelineLoader.load();
        displayPipelineView.setListView((ListView<String>) pipelineBox.lookup("#pipelineListView"));


        // Set fixed width for the boxes
        transformationsBox.setPrefWidth(200);
        transformationsBox.setMaxWidth(200);
        pipelineBox.setPrefWidth(200);
        pipelineBox.setMaxWidth(200);

        // Add sections to the center box
        centerBox.getChildren().addAll(transformationsBox, centerImageBox, pipelineBox);

        // Ensure the center image box expands to take up remaining space
        HBox.setHgrow(centerImageBox, Priority.ALWAYS); // Allow centerImageBox to grow
        HBox.setHgrow(transformationsBox, Priority.NEVER); // Prevent transformationsBox from growing
        HBox.setHgrow(pipelineBox, Priority.NEVER); // Prevent pipelineBox from growing

        // Set the center content to the horizontal box
        root.setCenter(centerBox);

        // Load and set the InfoBar at the bottom
        VBox infoBar = infoBarLoader.load(); // Load the InfoBar FXML as VBox
        infoBar.setPrefHeight(75); // Set the preferred height of the InfoBar if needed

        // Get the TextArea directly from the loaded FXML
        TextArea logTextArea = (TextArea) infoBar.lookup("#logTextArea"); // Lookup the TextArea

        // Create an instance of InfoBarView with the logTextArea
        InfoBarView infoBarView = new InfoBarView(logTextArea); // Pass the TextArea directly

        // Register the InfoBarView as an observer of the SaveImageModel
        loadImageController.getLoadImageModel().registerInfoBarObserver(infoBarView);
        //loadImageController.getLoadImageModel().registerTransformationObserver(displayTransformations);
        saveImageController.getSaveImageModel().registerObserver(infoBarView);
        languageController.getLanguageModel().registerObserver(infoBarView);
        transformationController.getTransformationModel().setObserverInfoBar(infoBarView);


        AboutView aboutView = AboutView.getInstance();
        aboutLoader.setController(aboutView);
        aboutLoader.load();
        HelpView helpView = new HelpView();
        helpController.getAboutModel().registerObserver(aboutView);
        helpController.getHelpModel().registerObserver(helpView);

        // Set the InfoBar to the bottom of the BorderPane
        root.setBottom(infoBar); // Add the InfoBar to the bottom of the BorderPane

        // Create the scene
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Image Editor");

        stage.setMinWidth(800);  // Set the minimum width
        stage.setMinHeight(600); // Set the minimum height
        stage.show();
        stage.toFront();
    }

    public static void main(String[] args) {
        launch();
    }
}
