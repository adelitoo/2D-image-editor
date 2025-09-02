package ch.supsi.imageeditor.frontend.dispatcher;

import ch.supsi.imageeditor.frontend.controller.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.util.ResourceBundle;

public class MenuBarDispatcher {

    @FXML
    private ListView<Label> transformationsListView;

    private final HelpControllerInterface helpController;
    private final LanguageControllerInterface languageController;
    private final SavePNMImageControllerInterface saveImageController;
    private final LoadImageControllerInterface loadImageController;
    private final TransformationControllerInterface transformationController;
    private final ResourceBundle resourceBundle;

    public MenuItem saveMenuItem;
    public MenuItem saveAsMenuItem;

    public MenuBarDispatcher() {
        this.saveImageController = SaveImageController.getInstance();
        this.helpController = HelpController.getInstance();
        this.languageController = LanguageController.getInstance();
        this.loadImageController = LoadImageController.getInstance();
        this.transformationController = TransformationController.getInstance();
        this.resourceBundle = languageController.getResourceBundle();
    }


    @FXML
    public void openFile() {
        if (!loadImageController.loadImage()) {
            saveMenuItem.setDisable(false);
            saveAsMenuItem.setDisable(false);
        }
    }

    @FXML
    public void saveFile() {
        saveImageController.saveImage();
    }

    @FXML
    public void saveFileAs() {
        saveImageController.saveAsImage();
    }

    @FXML
    public void quitApplication() {
        System.exit(0);
    }

    @FXML
    public void showHelp() {
        helpController.showHelp();
    }

    @FXML
    public void showAbout() {
        helpController.showAbout();
    }

    @FXML
    public void setLanguageEnglish() {
        languageController.setLanguageTagInsidePreferences("en-US");
    }

    @FXML
    public void setLanguageFrench() {
        languageController.setLanguageTagInsidePreferences("fr-CH");
    }

    @FXML
    public void setLanguageGerman() {
        languageController.setLanguageTagInsidePreferences("de-CH");
    }

    @FXML
    public void setLanguageItalian() {
        languageController.setLanguageTagInsidePreferences("it-CH");
    }

    @FXML
    public void setLanguageEspanol() {
        languageController.setLanguageTagInsidePreferences("es-ES");
    }

    @FXML
    public void handleNegativeClick(MouseEvent actionEvent) {
        String selectedLabel = transformationsListView.getSelectionModel().getSelectedItem().getText();
        if (selectedLabel != null) {
            transformationController.addTransformationToPipeline(resourceBundle.getString("label.negative"));
        }

    }

    @FXML
    public void handleRotateClockwiseClick(MouseEvent actionEvent) {
        String selectedLabel = transformationsListView.getSelectionModel().getSelectedItem().getText();

        if (selectedLabel != null) {
            transformationController.addTransformationToPipeline(resourceBundle.getString("label.rotateClockwise"));
        }
    }

    @FXML
    public void handleRotateAntiClockwiseClick(MouseEvent actionEvent) {
        String selectedLabel = transformationsListView.getSelectionModel().getSelectedItem().getText();
        if (selectedLabel != null) {
            transformationController.addTransformationToPipeline(resourceBundle.getString("label.rotateAntiClockwise"));
        }
    }

    @FXML
    public void handleFlipSideToSideClick(MouseEvent actionEvent) {
        String selectedLabel = transformationsListView.getSelectionModel().getSelectedItem().getText();

        if (selectedLabel != null) {
            transformationController.addTransformationToPipeline(resourceBundle.getString("label.flipSideToSide"));
        }
    }

    @FXML
    public void handleFlipUpsideDownClick(MouseEvent actionEvent) {
        String selectedLabel = transformationsListView.getSelectionModel().getSelectedItem().getText();

        if (selectedLabel != null) {
            transformationController.addTransformationToPipeline(resourceBundle.getString("label.flipUpsideDown"));
        }
    }

    @FXML
    public void doTransformations() {
        transformationController.applyTransformations();
    }

    @FXML
    public void deleteAllElements() {
        transformationController.clearPipelineListView();
    }
}
