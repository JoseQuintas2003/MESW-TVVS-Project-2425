package timelessodyssey.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class LanternaGUITest {
    private LanternaGUI lanternaGUI;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException, FontFormatException {
        lanternaGUI = spy(new LanternaGUI(mock(ScreenCreator.class), "test"));
    }

    @Test
    public void testSetKeySpam() throws NoSuchFieldException, IllegalAccessException {
        lanternaGUI.setKeySpam(false);

        assertEquals(null, getPriorityKeyPressed());


        lanternaGUI.setKeySpam(true);

        assertEquals(true, getPriorityKeyPressed());
    }

    public KeyEvent getPriorityKeyPressed() throws NoSuchFieldException, IllegalAccessException {
        Field keyField = LanternaGUI.class.getDeclaredField("priorityKeyPressed");
        keyField.setAccessible(true);
        return (KeyEvent) keyField.get(lanternaGUI);
    }
}
