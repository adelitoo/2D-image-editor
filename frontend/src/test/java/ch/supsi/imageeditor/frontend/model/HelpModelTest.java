package ch.supsi.imageeditor.frontend.model;

import ch.supsi.imageeditor.frontend.observer.ObserverHelp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class HelpModelTest {

    private HelpModel helpModel;

    @BeforeEach
    public void setup() {
        try {
            Field instanceField = HelpModel.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        helpModel = HelpModel.getInstance();
    }

    @Test
    public void constructor() {
        HelpModel helpModel1 = new HelpModel();
        Assertions.assertNotNull(helpModel1);
    }

    @Test
    public void testInstanceNull() {
        HelpModel helpModel1 = HelpModel.getInstance();
        Assertions.assertNotNull(helpModel1);
    }
    @Test
    public void testGetInstance() {
        HelpModel instance1 = HelpModel.getInstance();
        HelpModel instance2 = HelpModel.getInstance();

        assertSame(instance1, instance2, "getInstance() deve restituire sempre la stessa istanza.");
    }

    @Test
    public void testGetHelpData() {
        ResourceBundle mockBundle = mock(ResourceBundle.class);

        when(mockBundle.getString("label.helpTitle")).thenReturn("Help Title");
        when(mockBundle.getString("label.howToUse")).thenReturn("How to use the application");
        when(mockBundle.getString("label.ok")).thenReturn("OK");

        helpModel.setBundle(mockBundle);

        ObserverHelp observerMock = mock(ObserverHelp.class);
        helpModel.registerObserver(observerMock);

        helpModel.getHelpData();

        verify(observerMock, times(1)).update(
                eq("Help Title"),
                eq("How to use the application"),
                eq("OK")
        );
    }

    @Test
    public void testSetBundle() {
        ResourceBundle mockBundle = mock(ResourceBundle.class);

        helpModel.setBundle(mockBundle);

        assertSame(mockBundle, helpModel.getBundle(), "Il bundle dovrebbe essere impostato correttamente.");
    }
    @Test
    public void TestGetBundle(){
        ResourceBundle mockBundle = mock(ResourceBundle.class);

        helpModel.setBundle(mockBundle);

        assertSame(mockBundle, helpModel.getBundle(), "Il bundle dovrebbe essere impostato correttamente.");
    }
}
