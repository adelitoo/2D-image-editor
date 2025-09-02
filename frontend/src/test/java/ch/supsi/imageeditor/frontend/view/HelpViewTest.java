package ch.supsi.imageeditor.frontend.view;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HelpViewTest {
    @BeforeAll
    public static void beforeClass() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @Test
    public void constructor() {
        HelpView helpView = new HelpView();
        Assertions.assertNotNull(helpView);
    }
}