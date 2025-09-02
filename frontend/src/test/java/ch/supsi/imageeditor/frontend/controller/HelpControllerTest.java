package ch.supsi.imageeditor.frontend.controller;

import ch.supsi.imageeditor.frontend.model.AboutModel;
import ch.supsi.imageeditor.frontend.model.HelpModel;
import ch.supsi.imageeditor.frontend.view.AboutView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class HelpControllerTest {

    @Mock
    private AboutModel aboutModelMock;

    @Mock
    private HelpModel helpModelMock;

    private HelpController helpController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Field instanceField = null;
        try {
            instanceField = HelpController.class.getDeclaredField("mySelf");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        try (MockedStatic<AboutModel> mockedStaticAbout = mockStatic(AboutModel.class);
             MockedStatic<HelpModel> mockedStaticHelp = mockStatic(HelpModel.class);) {
            mockedStaticAbout.when(AboutModel::getInstance).thenReturn(aboutModelMock);
            mockedStaticHelp.when(HelpModel::getInstance).thenReturn(helpModelMock);
            helpController = HelpController.getInstance();
        }
    }

    @Test
    public void constructor() {
        HelpController helpController1 = new HelpController();
        Assertions.assertNotNull(helpController1);
    }

    @Test
    public void testInstanceNull() {
        HelpController helpController1 = HelpController.getInstance();
        Assertions.assertNotNull(helpController1);
    }
    @Test
    public void testGetInstance() {
        try (MockedStatic<HelpController> mockedStatic = mockStatic(HelpController.class)) {
            HelpController instanceMock = mock(HelpController.class);
            mockedStatic.when(HelpController::getInstance).thenReturn(instanceMock);

            HelpController instance1 = HelpController.getInstance();
            HelpController instance2 = HelpController.getInstance();

            assertSame(instance1, instance2, "getInstance() should return the same mocked instance.");
        }
    }

    @Test
    public void testSetBundle() {
        ResourceBundle mockBundle = mock(ResourceBundle.class);

        helpController.setBundle(mockBundle);

        verify(helpModelMock,times(1)).setBundle(mockBundle);
    }

    @Test
    public void testShowAbout() {
        helpController.showAbout();
        verify(aboutModelMock,times(1)).getAboutData();
    }

    @Test
    public void TestGetAboutModel(){
        assertEquals(aboutModelMock, helpController.getAboutModel());
    }

    @Test
    public void TestGetHelpModel(){
        assertEquals(helpModelMock, helpController.getHelpModel());
    }
    @Test
    public void testShowHelp() {
        helpController.showHelp();
        verify(helpModelMock,times(1)).getHelpData();
    }
}
