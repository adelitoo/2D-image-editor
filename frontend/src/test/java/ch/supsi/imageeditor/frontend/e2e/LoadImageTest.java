package ch.supsi.imageeditor.frontend.e2e;

import ch.supsi.imageeditor.frontend.controller.LoadImageController;
import ch.supsi.imageeditor.frontend.model.LoadImageModel;
import ch.supsi.imageeditor.frontend.observer.ObserverInfoBar;
import ch.supsi.imageeditor.frontend.view.DisplayPNMImageViewInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class LoadImageTest extends AbstractMainGUITest {

    private LoadImageController loadImageController;
    private DisplayPNMImageViewInterface mockView;
    private LoadImageModel loadImageModel;
    private ObserverInfoBar mockInfoBar;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() {
        // Initialize controller and model
        loadImageController = LoadImageController.getInstance();
        loadImageModel = LoadImageModel.getInstance();

        // Mock the DisplayPNMImageViewInterface
        mockView = mock(DisplayPNMImageViewInterface.class);

        // Mock the InfoBar observer
        mockInfoBar = mock(ObserverInfoBar.class);

        // Inject mock dependencies
        loadImageController.setDisplayPNMImageView(mockView);
        loadImageModel.registerInfoBarObserver(mockInfoBar);

        // Set a mock ResourceBundle
        ResourceBundle mockBundle = new ResourceBundle() {
            @Override
            protected Object handleGetObject(String key) {
                if (key.equals("label.imageLoaded")) {
                    return "Image loaded from: ";
                }
                return null;
            }

            @Override
            public boolean containsKey(String key) {
                return "label.imageLoaded".equals(key);
            }

            @Override
            public Enumeration<String> getKeys() {
                return Collections.enumeration(Collections.singleton("label.imageLoaded"));
            }
        };

        loadImageModel.setResourceBundle(mockBundle);
    }

    @Test
    public void testLoadImage() throws Exception {
        System.out.println(tempDir.toAbsolutePath());

        // Locate and copy the resource file
        File resourceFile = new File(getClass().getResource("/generated_image.ppm").toURI());
        File tempFile = new File(tempDir.toFile(), "generated_image.ppm");
        Files.copy(resourceFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        // Mock the file selection
        when(mockView.showAndGetFilePath()).thenReturn(tempFile);

        // Act
        boolean result = loadImageController.loadImage();

        // Assert
        assertFalse(result, "Image loading should return true for success");

        // Verify observer updates
        String expectedMessage = "Image loaded from: " + tempFile.getAbsolutePath();
        verify(mockInfoBar).update(expectedMessage);
    }

}
