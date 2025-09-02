package ch.supsi.imageeditor.frontend.view;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DisplayPNMImageViewTest {
    @BeforeAll
    public static void beforeClass() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @Test
    public void constructor() {
        DisplayPNMImageView displayPNMImageView = DisplayPNMImageView.getInstance();
        Assertions.assertNotNull(displayPNMImageView);
    }

}