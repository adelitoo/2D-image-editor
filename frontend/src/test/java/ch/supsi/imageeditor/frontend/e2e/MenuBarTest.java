package ch.supsi.imageeditor.frontend.e2e;

import com.sun.javafx.scene.control.ContextMenuContent;
import javafx.scene.control.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class MenuBarTest extends AbstractMainGUITest {

    @Test
    public void testMenuBar() {
        testFileMenu();
        testEditMenu();
        testHelpMenu();
    }

    private void testFileMenu() {
        step("Testing File Menu", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#scrollPane");
            sleep(SLEEP_INTERVAL);

            clickOn("#fileMenu");

            sleep(SLEEP_INTERVAL);



            MenuItem openMenuItem = lookup("#openMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(openMenuItem.isVisible());
            Assertions.assertFalse(openMenuItem.isDisable());

            MenuItem saveMenuItem = lookup("#saveMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(saveMenuItem.isVisible());
            Assertions.assertTrue(saveMenuItem.isDisable());

            MenuItem saveAsMenuItem = lookup("#saveAsMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(saveAsMenuItem.isVisible());
            Assertions.assertTrue(saveAsMenuItem.isDisable());

            MenuItem quitMenuItem = lookup("#quitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            Assertions.assertTrue(quitMenuItem.isVisible());

            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");
        });
    }

    private void testEditMenu() {
        step("Testing Edit Menu", () -> {
            clickOn("#editMenu");
            sleep(SLEEP_INTERVAL);

            verifyThat("#preferencesMenu", isVisible());
            clickOn("#preferencesMenu");
            sleep(SLEEP_INTERVAL);

            clickOn("#languageMenu");
            verifyThat("#englishMenuItem", isEnabled());
            verifyThat("#frenchMenuItem", isEnabled());
            verifyThat("#germanMenuItem", isEnabled());
            verifyThat("#italianMenuItem", isEnabled());
            verifyThat("#espanolMenuItem", isEnabled());

            clickOn("#languageMenu");
            clickOn("#editMenu");
        });
    }

    private void testHelpMenu() {
        step("Testing Help Menu", () -> {
            clickOn("#helpMenu");
            sleep(SLEEP_INTERVAL);

            verifyThat("#aboutMenuItem", isEnabled());
            verifyThat("#helpMenuItem", isEnabled());

            clickOn("#helpMenu");
        });
    }
}
