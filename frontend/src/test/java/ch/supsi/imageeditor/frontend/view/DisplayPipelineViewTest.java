package ch.supsi.imageeditor.frontend.view;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DisplayPipelineViewTest {
    @BeforeAll
    public static void beforeClass() {
        JFXPanel fxPanel = new JFXPanel();
    }

    @Test
    public void constructor() {
        DisplayPipelineView displayPipelineView = new DisplayPipelineView();
        Assertions.assertNotNull(displayPipelineView);
    }

    @Test
    public void testInstanceNull() {
        DisplayPipelineView displayPipelineView = DisplayPipelineView.getInstance();
        Assertions.assertNotNull(displayPipelineView);
    }
    @Test
    public void testSingletonInstance() {
        DisplayPipelineView instance1 = DisplayPipelineView.getInstance();
        DisplayPipelineView instance2 = DisplayPipelineView.getInstance();

        Assertions.assertSame(instance1, instance2, "getInstance() deve restituire la stessa istanza.");
    }
}