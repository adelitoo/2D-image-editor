package ch.supsi.imageeditor.frontend.dispatcher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class MenuBarDispatcherTest {
    @Test
    public void constructor() {
        MenuBarDispatcher menuBarDispatcher = new MenuBarDispatcher();
        Assertions.assertNotNull(menuBarDispatcher);
    }

}
