package ch.supsi.imageeditor.frontend.view;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AboutViewTest {

    @BeforeAll
    public static void beforeClass() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @Test
    public void constructor() {
        AboutView aboutView = new AboutView();
        Assertions.assertNotNull(aboutView);
    }

    @Test
    public void testInstanceNull() {
        AboutView aboutView = AboutView.getInstance();
        Assertions.assertNotNull(aboutView);
    }
    @Test
    public void testSingletonInstance() {
        AboutView instance1 = AboutView.getInstance();
        AboutView instance2 = AboutView.getInstance();

        Assertions.assertSame(instance1, instance2, "getInstance() deve restituire la stessa istanza.");
    }
}
