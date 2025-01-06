package timelessodyssey.view.screens;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.menu.Entry;
import timelessodyssey.model.menu.Menu;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.menu.EntryViewer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.*;

public class MenuViewerTest {
    private MenuViewer menuViewer;
    private EntryViewer entryViewer;
    private Menu menu;
    private ViewerProvider viewerProvider;
    private SpriteLoader spriteLoader;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        menu = spy(new Menu() {
            @Override
            protected List<Entry> createEntries() {
                return List.of(
                    new Entry(1, 1, Entry.Type.START_GAME),
                    new Entry(1, 2, Entry.Type.SETTINGS),
                    new Entry(1, 3, Entry.Type.EXIT),
                    new Entry(1, 4, Entry.Type.RESOLUTION),
                    new Entry(1, 5, Entry.Type.TO_MAIN_MENU)
                );
            }
        });

        spriteLoader = spriteFilepath -> mock(Sprite.class);

        entryViewer = mock(EntryViewer.class);

        viewerProvider = spy(new ViewerProvider(spriteLoader));
        when(viewerProvider.getEntryViewer()).thenReturn(entryViewer);

        menuViewer = new MenuViewer(menu, viewerProvider);
    }

    @Test
    public void testDrawEntriesEMPTY_LIST() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ResizableGUI gui = mock(ResizableGUI.class);
        List<Entry> entries = List.of();

        invokeDrawEntries(gui, entries);

        verify(entryViewer, never()).draw(any(), any(), any());
    }

    @Test
    public void testDrawEntriesFIRST_POSITION() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
        ResizableGUI gui = mock(ResizableGUI.class);
        List<Entry> entries = menu.getEntries();

        invokeDrawEntries(gui, entries);

        verify(entryViewer, times(entries.size())).draw(any(), any(), any());
    }

    @Test
    public void testDrawEntriesSECOND_POSITION() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        ResizableGUI gui = mock(ResizableGUI.class);
        List<Entry> entries = menu.getEntries();
        menu.moveDown();

        invokeDrawEntries(gui, entries);

        verify(entryViewer, times(entries.size())).draw(any(), any(), any());
    }

    public void invokeDrawEntries(ResizableGUI gui, List<Entry> entries) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method drawEntriesMethod = MenuViewer.class.getDeclaredMethod("drawEntries", ResizableGUI.class, List.class);
        drawEntriesMethod.setAccessible(true);
        drawEntriesMethod.invoke(menuViewer, gui, entries);
    }
}
