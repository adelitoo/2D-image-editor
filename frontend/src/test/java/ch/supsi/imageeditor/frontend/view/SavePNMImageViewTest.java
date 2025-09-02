package ch.supsi.imageeditor.frontend.view;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class SavePNMImageViewTest {
    @BeforeAll
    public static void beforeClass() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @Test
    public void constructor() {
        SavePNMImageView savePNMImageView = new SavePNMImageView();
        Assertions.assertNotNull(savePNMImageView);
    }
}