package ch.supsi.imageeditor.frontend.view;

import ch.supsi.imageeditor.frontend.observer.ObserverHelp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpView implements ObserverHelp {
    private static HelpView mySelf;
    @FXML
    private TextArea rulesTextArea;
    @FXML
    private Button closeButton;

    @FXML
    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void update(String title, String rules, String closeButtonText) {
        try {
            FXMLLoader loader = new FXMLLoader(HelpView.class.getResource("/help.fxml"));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(loader.load()));
            HelpView helpView = loader.getController();
            helpView.updateView(rules, closeButtonText);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateView(String rules, String closeButtonText) {
        rulesTextArea.setText(rules);
        closeButton.setText(closeButtonText);
    }
}
