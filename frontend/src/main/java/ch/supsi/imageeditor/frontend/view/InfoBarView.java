package ch.supsi.imageeditor.frontend.view;

import ch.supsi.imageeditor.frontend.observer.ObserverInfoBar;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class InfoBarView implements ObserverInfoBar {
    @FXML
    private TextArea logTextArea;

    public InfoBarView(TextArea logTextArea) {
        this.logTextArea = logTextArea;

        logTextArea.setStyle("-fx-font-size: 16px;");
        logTextArea.setWrapText(true);
        logTextArea.setEditable(false);
    }

    @Override
    public void update(String message) {
        String currentText = logTextArea.getText();
        logTextArea.setText(message + "\n" + currentText);
    }

}
