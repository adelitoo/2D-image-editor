package ch.supsi.imageeditor.frontend.e2e;

import javafx.scene.control.TextArea;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class HelpAboutTest extends AbstractMainGUITest {

    @Test
    public void testHelpAndAboutMenus() {
        testAboutWindow();
        testHelpWindow();
    }

    private void testHelpWindow() {
        step("Testing Help Window", () -> {
            clickOn("#scrollPane");
            clickOn("#helpMenu");

            sleep(SLEEP_INTERVAL);
            clickOn("#helpMenuItem");

            verifyThat("#rulesTextArea", isVisible());
            verifyThat("#rulesTextArea", isEnabled());
            verifyThat("#closeButton", isEnabled());

            TextArea rulesTextArea = lookup("#rulesTextArea").query();
            Assertions.assertFalse(rulesTextArea.getText().isEmpty(), "Help content should not be empty");

            clickOn("#closeButton");
            sleep(SLEEP_INTERVAL);

            if (lookup("#closeButton").tryQuery().isPresent() && lookup("#closeButton").query().isVisible()) {
                clickOn("#closeButton");
                clickOn("#closeButton");
                sleep(SLEEP_INTERVAL);
            }
        });
    }

    private void testAboutWindow() {
        step("Testing About Window", () -> {
            clickOn("#scrollPane");
            clickOn("#helpMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#aboutMenuItem");

            verifyThat("#name", isVisible());
            verifyThat("#version", isVisible());
            verifyThat("#date", isVisible());
            verifyThat("#nameDevelopers", isVisible());

            verifyThat("#okButton", isVisible());
            clickOn("#okButton");
            sleep(SLEEP_INTERVAL);
        });
    }
}
