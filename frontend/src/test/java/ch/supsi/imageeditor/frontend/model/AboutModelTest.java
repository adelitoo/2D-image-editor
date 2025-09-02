package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.frontend.observer.ObserverAbout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AboutModelTest {

    private AboutModel aboutModel;

    @BeforeEach
    public void setup() {
        try {
            Field instanceField = AboutModel.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        aboutModel = AboutModel.getInstance();
    }

    @Test
    public void constructor() {
        AboutModel aboutModel1 = new AboutModel();
        Assertions.assertNotNull(aboutModel1);
    }

    @Test
    public void testInstanceNull() {
        AboutModel aboutModel1 = AboutModel.getInstance();
        Assertions.assertNotNull(aboutModel1);
    }

    @Test
    public void testGetInstance() {
        AboutModel instance1 = AboutModel.getInstance();
        AboutModel instance2 = AboutModel.getInstance();

        assertSame(instance1, instance2, "getInstance() deve restituire sempre la stessa istanza.");
    }

    @Test
    public void testGetAboutData() throws Exception {
        String mockPropertiesContent = """
            name=Test Name
            developers=Test Developers
            version=1.0.0
            dateBuild=2024-01-01
            """;

        Field aboutUsFileField = AboutModel.class.getDeclaredField("aboutUsFile");
        aboutUsFileField.setAccessible(true);

        Properties mockProperties = new Properties();
        try (InputStream inputStream = new ByteArrayInputStream(mockPropertiesContent.getBytes())) {
            mockProperties.load(inputStream);
        }

        aboutUsFileField.set(aboutModel, mockProperties);

        ObserverAbout observerMock = mock(ObserverAbout.class);
        aboutModel.registerObserver(observerMock);

        aboutModel.getAboutData();

        verify(observerMock, times(1)).update(
                eq("Test Name"),
                eq("Test Developers"),
                eq("1.0.0"),
                eq("2024-01-01")
        );
    }

}
