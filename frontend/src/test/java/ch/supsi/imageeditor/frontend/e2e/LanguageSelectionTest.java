package ch.supsi.imageeditor.frontend.e2e;

import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class LanguageSelectionTest extends AbstractMainGUITest {
    @Test
    public void testHelpAndAboutMenus() {
        testLanguageItalian();
        testLanguageFrench();
        testLanguageGerman();
        testLangaugeEspanol();
        testLanguageEnglish();
    }

    private void testLanguageItalian() {
        step("Testing Italian Language Selection", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#editMenu");
            sleep(SLEEP_INTERVAL);

            verifyThat("#preferencesMenu", isVisible());
            clickOn("#preferencesMenu");
            sleep(SLEEP_INTERVAL);

            clickOn("#languageMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#italianMenuItem");

            sleep(SLEEP_INTERVAL);

            verifyThat("#logTextArea", TextInputControlMatchers.hasText("Lingua salvata!\n"));
        });
    }

    private void testLanguageFrench() {
        step("Testing French Language Selection", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#editMenu");
            sleep(SLEEP_INTERVAL);

            verifyThat("#preferencesMenu", isVisible());
            clickOn("#preferencesMenu");
            sleep(SLEEP_INTERVAL);

            clickOn("#languageMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#frenchMenuItem");

            sleep(SLEEP_INTERVAL);

            verifyThat("#logTextArea", TextInputControlMatchers.hasText("Langue enregistrée!\nLingua salvata!\n"));
        });
    }

    private void testLanguageGerman() {
        step("Testing German Language Selection", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#editMenu");
            sleep(SLEEP_INTERVAL);

            verifyThat("#preferencesMenu", isVisible());
            clickOn("#preferencesMenu");
            sleep(SLEEP_INTERVAL);

            clickOn("#languageMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#germanMenuItem");

            sleep(SLEEP_INTERVAL);

            verifyThat("#logTextArea", TextInputControlMatchers.hasText("Sprache gespeichert!\nLangue enregistrée!\nLingua salvata!\n"));
        });
    }

    private void testLangaugeEspanol() {
        step("Testing Spanish Language Selection", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#editMenu");
            sleep(SLEEP_INTERVAL);

            verifyThat("#preferencesMenu", isVisible());
            clickOn("#preferencesMenu");
            sleep(SLEEP_INTERVAL);

            clickOn("#languageMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#espanolMenuItem");

            sleep(SLEEP_INTERVAL);

            verifyThat("#logTextArea", TextInputControlMatchers.hasText("Idioma guardado!\nSprache gespeichert!\nLangue enregistrée!\nLingua salvata!\n"));
        });
    }

    private void testLanguageEnglish() {
        step("Testing English Language Selection", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#editMenu");
            sleep(SLEEP_INTERVAL);

            verifyThat("#preferencesMenu", isVisible());
            clickOn("#preferencesMenu");
            sleep(SLEEP_INTERVAL);

            clickOn("#languageMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#englishMenuItem");

            sleep(SLEEP_INTERVAL);

            verifyThat("#logTextArea", TextInputControlMatchers.hasText("Language saved!\nIdioma guardado!\nSprache gespeichert!\nLangue enregistrée!\nLingua salvata!\n"));
        });
    }

}
