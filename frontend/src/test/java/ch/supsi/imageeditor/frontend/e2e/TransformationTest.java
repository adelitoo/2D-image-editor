package ch.supsi.imageeditor.frontend.e2e;

import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;
import static org.testfx.matcher.control.ListViewMatchers.isEmpty;

public class TransformationTest extends AbstractMainGUITest {

    @Test
    public void testHelpAndAboutMenus() {
        testNothingToDelete();
        testImageNotInitialized();
        testTransformationsInteraction();
        testDeleteElements();
    }

    private void testImageNotInitialized() {
        step("Testing clicking play button without an image", () -> {
            verifyThat("#playButton", isVisible());
            verifyThat("#playButton", isEnabled());
            verifyThat("#pipelineListView", isVisible());

            sleep(SLEEP_INTERVAL);
            clickOn("#playButton");
            verifyThat("#logTextArea", TextInputControlMatchers.hasText("Image not initialized\nNothing to delete\n"));
        });
    }

    private void testNothingToDelete() {
        step("Testing clicking delete button without transformations selected", () -> {
            clickOn("#scrollPane");
            sleep(SLEEP_INTERVAL);
            verifyThat("#deleteButton", isVisible());
            verifyThat("#deleteButton", isEnabled());
            verifyThat("#pipelineListView", isVisible());

            sleep(SLEEP_INTERVAL);
            clickOn("#deleteButton");
            verifyThat("#logTextArea", TextInputControlMatchers.hasText("Nothing to delete\n"));
        });
    }


    private void testTransformationsInteraction() {
        step("Testing Transformations List Visibility, Click Actions, and Pipeline Updates", () -> {
            verifyThat("#transformationsListView", isVisible());
            verifyThat("#transformationsListView", isEnabled());

            verifyThat("#pipelineListView", isVisible());

            clickOn("#scrollPane");

            clickOn("#upsideDown");
            verifyPipelineContains("Flip Image (Up-Side-Down)");

            sleep(SLEEP_INTERVAL);
            clickOn("#sideToSide");
            verifyPipelineContains("Flip Image (Side-to-Side)");

            sleep(SLEEP_INTERVAL);
            clickOn("#rotateClockWise");
            verifyPipelineContains("Rotate 90° Clockwise");

            sleep(SLEEP_INTERVAL);
            clickOn("#negative");
            verifyPipelineContains("Negative");

            sleep(SLEEP_INTERVAL);
            clickOn("#rotateAntiClockWise");
            verifyPipelineContains("Rotate 90° Anti-Clockwise");
        });
    }

    private void verifyPipelineContains(String expectedText) {
        // Verifying if the expected transformation appears in the pipeline list
        verifyThat("#pipelineListView", hasChild(expectedText));
    }

    private void testDeleteElements() {
        step("Testing Transformations List Visibility, Click Actions, and Pipeline Updates", () -> {
            verifyThat("#deleteButton", isVisible());
            verifyThat("#deleteButton", isEnabled());

            sleep(SLEEP_INTERVAL);
            clickOn("#deleteButton");

            verifyThat("#pipelineListView", isEmpty());
        });
    }
}
