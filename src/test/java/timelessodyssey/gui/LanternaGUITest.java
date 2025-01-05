package timelessodyssey.gui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.*;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LanternaGUITest {
    @Mock
    private KeyEvent keyEvent;

    private LanternaGUI lanternaGUI;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException, FontFormatException, NoSuchFieldException, IllegalAccessException {
        ScreenCreator screenCreator = spy(new LanternaScreenCreator(
            new DefaultTerminalFactory(),
            new TerminalSize(80, 24),
            new Rectangle(0, 0, 800, 600)
        ));

        lanternaGUI = spy(new LanternaGUI(screenCreator, "test"));
    }

    @Test
    public void testGetNextActionNONE() throws NoSuchFieldException, IllegalAccessException {
        setKeyPressed(null);

        assertEquals(GUI.Action.NONE, lanternaGUI.getNextAction());
    }

    @Test
    public void testGetNextActionLEFT() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_LEFT).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.LEFT, action);
    }

    @Test
    public void testGetNextActionRIGHT() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_RIGHT).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.RIGHT, action);
    }

    @Test
    public void testGetNextActionUP() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_UP).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.UP, action);
    }

    @Test
    public void testGetNextActionDOWN() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_DOWN).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.DOWN, action);
    }

    @Test
    public void testGetNextActionQUIT() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_ESCAPE).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.QUIT, action);
    }

    @Test
    public void testGetNextActionSELECT() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_ENTER).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.SELECT, action);
    }

    @Test
    public void testGetNextActionJUMP() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_SPACE).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.JUMP, action);
    }

    @Test
    public void testGetNextActionDASH() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_X).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.DASH, action);
    }

    @Test
    public void testGetNextActionDEFAULT() throws NoSuchFieldException, IllegalAccessException {
        doReturn(KeyEvent.VK_D).when(keyEvent).getKeyCode();
        setKeyPressed(keyEvent);

        GUI.Action action = lanternaGUI.getNextAction();

        assertEquals(GUI.Action.NONE, action);
    }

    @Test
    public void testKeyAdapterPRESSED_INVALIDKEY() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        lanternaGUI.setKeySpam(true);

        KeyAdapter keyAdapter = createKeyAdapter();

        when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_0);


        keyAdapter.keyPressed(keyEvent);


        KeyEvent actualKeyPressed = getKeyPressed();

        assertEquals(keyEvent, actualKeyPressed);
    }

    @Test
    public void testKeyAdapterPRESSED_NO_KEYSPAM() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        KeyAdapter keyAdapter = createKeyAdapter();


        keyAdapter.keyPressed(keyEvent);


        KeyEvent actualKeyPressed = getKeyPressed();

        assertEquals(keyEvent, actualKeyPressed);
    }

    @Test
    public void testKeyAdapterPRESSED_KEYSPAM() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        lanternaGUI.setKeySpam(true);

        KeyAdapter keyAdapter = createKeyAdapter();

        when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);


        keyAdapter.keyPressed(keyEvent);


        KeyEvent actualKeyPressed = getKeyPressed();
        KeyEvent actualPriorityKeyPressed = getPriorityKeyPressed();

        assertEquals(keyEvent, actualKeyPressed);
        assertEquals(keyEvent, actualPriorityKeyPressed);
    }

    @Test
    public void testKeyAdapterRELEASED_INVALIDKEY() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        lanternaGUI.setKeySpam(true);

        KeyAdapter keyAdapter = createKeyAdapter();

        when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_0);


        keyAdapter.keyReleased(keyEvent);


        KeyEvent actualKeyPressed = getKeyPressed();
        KeyEvent actualPriorityKeyPressed = getPriorityKeyPressed();

        assertEquals(actualPriorityKeyPressed, actualKeyPressed);
    }

    @Test
    public void testKeyAdapterRELEASED_NO_KEYSPAM() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        KeyAdapter keyAdapter = createKeyAdapter();


        keyAdapter.keyReleased(keyEvent);


        KeyEvent actualKeyPressed = getKeyPressed();
        KeyEvent actualPriorityKeyPressed = getPriorityKeyPressed();

        assertEquals(actualPriorityKeyPressed, actualKeyPressed);
    }

    @Test
    public void testKeyAdapterRELEASED_KEYSPAM() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, NoSuchFieldException {
        lanternaGUI.setKeySpam(true);

        KeyAdapter keyAdapter = createKeyAdapter();

        when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);


        keyAdapter.keyReleased(keyEvent);


        KeyEvent actualKeyPressed = getKeyPressed();
        KeyEvent actualPriorityKeyPressed = getPriorityKeyPressed();

        assertNull(actualKeyPressed);
        assertNull(actualPriorityKeyPressed);
    }

    @Test
    public void testSetKeySpam() throws NoSuchFieldException, IllegalAccessException {
        lanternaGUI.setKeySpam(false);

        assertEquals(null, getPriorityKeyPressed());


        lanternaGUI.setKeySpam(true);

        assertEquals(true, getKeySpam());
    }

    public void setPriorityKeyPressed(KeyEvent keyEvent) throws NoSuchFieldException, IllegalAccessException {
        Field keyField = LanternaGUI.class.getDeclaredField("priorityKeyPressed");
        keyField.setAccessible(true);
        keyField.set(lanternaGUI, keyEvent);
    }

    public KeyEvent getPriorityKeyPressed() throws NoSuchFieldException, IllegalAccessException {
        Field keyField = LanternaGUI.class.getDeclaredField("priorityKeyPressed");
        keyField.setAccessible(true);
        return (KeyEvent) keyField.get(lanternaGUI);
    }


    public void setKeyPressed(KeyEvent keyEvent) throws NoSuchFieldException, IllegalAccessException {
        Field keyField = LanternaGUI.class.getDeclaredField("keyPressed");
        keyField.setAccessible(true);
        keyField.set(lanternaGUI, keyEvent);
    }

    public KeyEvent getKeyPressed() throws NoSuchFieldException, IllegalAccessException {
        Field keyField = LanternaGUI.class.getDeclaredField("keyPressed");
        keyField.setAccessible(true);
        return (KeyEvent) keyField.get(lanternaGUI);
    }


    public boolean getKeySpam() throws NoSuchFieldException, IllegalAccessException {
        Field keySpamField = LanternaGUI.class.getDeclaredField("keySpam");
        keySpamField.setAccessible(true);
        return (boolean) keySpamField.get(lanternaGUI);
    }

    public KeyAdapter createKeyAdapter() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method createKeyAdapterMethod = LanternaGUI.class.getDeclaredMethod("createKeyAdapter");
        createKeyAdapterMethod.setAccessible(true);
        return (KeyAdapter) createKeyAdapterMethod.invoke(lanternaGUI);
    }
}
