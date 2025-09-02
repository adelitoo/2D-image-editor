package ch.supsi.imageeditor.frontend.e2e;


import ch.supsi.imageeditor.frontend.MainFx;
import ch.supsi.imageeditor.frontend.view.DisplayPNMImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Logger;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

abstract public class AbstractMainGUITest extends ApplicationTest {

    protected static final int SLEEP_INTERVAL = 1;

    protected static final Logger LOGGER = Logger.getAnonymousLogger();

    protected int stepNo;

    private String testImage = this.getClass().getResource("/generated_image.ppm").getFile();

    @BeforeAll
    public static void setupSpec() {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    protected void step(final String step, final Runnable runnable) {
        ++stepNo;
        LOGGER.info("STEP" + stepNo + ":" + step);
        runnable.run();
        LOGGER.info("STEP" + stepNo + ":" + "end");
    }

    public void start(final Stage stage) throws Exception {
        try (MockedStatic<DisplayPNMImageView> mockedStaticDisplayPNMImageView = Mockito.mockStatic(DisplayPNMImageView.class)) {
            DisplayPNMImageView mockedDisplayPNMImageView = mock(DisplayPNMImageView.class);
            when(mockedDisplayPNMImageView.showAndGetFilePath()).thenReturn(new File(testImage));
            mockedStaticDisplayPNMImageView.when(DisplayPNMImageView::getInstance).thenReturn(mockedDisplayPNMImageView);

            final MainFx main = new MainFx();
            stage.toFront();
            main.start(stage);
        }
    }

}
