package ch.supsi.imageeditor.frontend.view;

import ch.supsi.imageeditor.frontend.observer.ObserverAbout;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class AboutView implements ObserverAbout {
    private static AboutView mySelf;
    @FXML
    private Pane aboutPane;
    @FXML
    private Label name;
    @FXML
    private Label version;
    @FXML
    private Label date;
    @FXML
    private Label nameDevelopers;

    protected AboutView() {}

    public static AboutView getInstance() {
        if (mySelf == null)
            mySelf = new AboutView();
        return mySelf;
    }

    @Override
    public void update(String name, String developer, String version, String date) {
        setMessages(name, version, developer, date);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About us");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(this.aboutPane);

        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setId("okButton");

        alert.showAndWait();
    }

    private void setMessages(String name, String version, String nameOfDevelopers, String date) {

        this.name.setText(this.name.getText() + name + "\n");
        this.version.setText(this.version.getText() + version + "\n");
        this.date.setText(this.date.getText() + date + "\n");
        this.nameDevelopers.setText(this.nameDevelopers.getText() + nameOfDevelopers  + "\n");
    }
}
