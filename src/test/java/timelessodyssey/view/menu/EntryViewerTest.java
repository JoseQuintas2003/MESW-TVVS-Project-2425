package timelessodyssey.view.menu;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.menu.Entry;
import timelessodyssey.view.text.TextViewer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EntryViewerTest {
    private TextViewer textViewer;
    private EntryViewer entryViewer;
    private ResizableGUI gui;

    @BeforeEach
    public void setUp() {
        textViewer = mock(TextViewer.class);

        entryViewer = new EntryViewer(textViewer);

        gui = mock(ResizableGUI.class);
    }

    @Test
    public void testDrawSTART_GAME() {
        Entry entry = new Entry(0, 0, Entry.Type.START_GAME);

        entryViewer.draw(entry, gui, null);

        verify(textViewer, times(1)).draw("Start", 0, 0, null, gui);
    }

    @Test
    public void testDrawSETTINGS() {
        Entry entry = new Entry(0, 0, Entry.Type.SETTINGS);

        entryViewer.draw(entry, gui, null);

        verify(textViewer, times(1)).draw("Settings", 0, 0, null, gui);
    }

    @Test
    public void testDrawEXIT() {
        Entry entry = new Entry(0, 0, Entry.Type.EXIT);

        entryViewer.draw(entry, gui, null);

        verify(textViewer, times(1)).draw("Exit", 0, 0, null, gui);
    }

    @Test
    public void testDrawRESOLUTION() {
        Entry entry = new Entry(0, 0, Entry.Type.RESOLUTION);

        entryViewer.draw(entry, gui, null);

        verify(textViewer, times(1)).draw("Resolution:   Automatic >", 0, 0, null, gui);
    }

    @Test
    public void testDrawTO_MAIN_MENU() {
        Entry entry = new Entry(0, 0, Entry.Type.TO_MAIN_MENU);

        entryViewer.draw(entry, gui, null);

        verify(textViewer, times(1)).draw("Go Back", 0, 0, null, gui);
    }

    @Test
    public void testGetResolutionLabelNULL() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        when(gui.getResolution()).thenReturn(null);


        String result1 = invokeGetResolutionLabel(gui);


        assertEquals("Resolution:   Automatic >", result1);
    }

    @Test
    public void testGetResolutionLabelMIDDLE() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        when(gui.getResolution()).thenReturn(ResizableGUI.Resolution.WXGA);


        String result = invokeGetResolutionLabel(gui);


        assertEquals("Resolution: < 1280X720 > ", result);
    }

    @Test
    public void testGetResolutionLabelEND() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        when(gui.getResolution()).thenReturn(ResizableGUI.Resolution.FOUR_K);


        String result = invokeGetResolutionLabel(gui);


        assertEquals("Resolution: < 3840X2160   ", result);
    }

    public String invokeGetResolutionLabel(ResizableGUI gui) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getResolutionLabelMethod = EntryViewer.class.getDeclaredMethod("getResolutionLabel", ResizableGUI.class);
        getResolutionLabelMethod.setAccessible(true);
        return (String) getResolutionLabelMethod.invoke(entryViewer, gui);
    }
}
