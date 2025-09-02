package ch.supsi.imageeditor.frontend.view;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class InfoBarViewTest {
    @BeforeAll
    public static void beforeClass() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @Test
    public void constructor() {
        InfoBarView infoBarView = new InfoBarView(new TextArea());
        Assertions.assertNotNull(infoBarView);
    }
}