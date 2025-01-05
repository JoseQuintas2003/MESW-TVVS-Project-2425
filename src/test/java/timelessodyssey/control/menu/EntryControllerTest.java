package timelessodyssey.control.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.model.menu.Entry;
import timelessodyssey.model.menu.Menu;
import timelessodyssey.states.GameState;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.states.SettingsMenuState;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class EntryControllerTest {
    private Menu menu;
    private EntryController entryController;
    private Game game;
    private long frameCount = 0;
    private SpriteLoader spriteLoader;

    @BeforeEach
    public void setUp() throws Exception {
        game = spy(new Game());

        spriteLoader = spriteFilepath -> mock(Sprite.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);

        doCallRealMethod().when(game).setState(any());


        menu = new Menu() {
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
        };

        entryController = new EntryController(menu);
    }

    @Test
    public void testMainOptionsStep() throws IOException, URISyntaxException, FontFormatException, NoSuchFieldException, IllegalAccessException {
        entryController.step(game, GUI.Action.RIGHT, frameCount);

        verify(game, never()).setState(any(GameState.class));

        entryController.step(game, GUI.Action.SELECT, frameCount);

        verify(game, times(1)).setState(any(GameState.class));


        menu.moveDown(); // SETTINGS

        entryController.step(game, GUI.Action.RIGHT, frameCount);

        verify(game, never()).setState(any(SettingsMenuState.class));

        entryController.step(game, GUI.Action.SELECT, frameCount);

        verify(game, times(1)).setState(any(SettingsMenuState.class));


        menu.moveDown(); // EXIT

        entryController.step(game, GUI.Action.RIGHT, frameCount);

        verify(game, never()).setState(null);

        entryController.step(game, GUI.Action.SELECT, frameCount);

        verify(game, times(1)).setState(null);


        menu.moveDown(); // RESOLUTION - Resolution is tested later, due to its complexity
        menu.moveDown(); // TO_MAIN_MENU

        entryController.step(game, GUI.Action.RIGHT, frameCount);

        verify(game, never()).setState(any(MainMenuState.class));

        entryController.step(game, GUI.Action.SELECT, frameCount);

        verify(game, times(1)).setState(any(MainMenuState.class));
    }

    @Test
    public void testResolutionStep() throws IOException, URISyntaxException, FontFormatException {
        menu.moveDown(); // SETTINGS
        menu.moveDown(); // EXIT
        menu.moveDown(); // RESOLUTION


        entryController.step(game, GUI.Action.LEFT, frameCount);

        verify(game, times(0)).setResolution(any());


        entryController.step(game, GUI.Action.RIGHT, frameCount);

        verify(game, times(1)).setResolution(ResizableGUI.Resolution.WXGA);


        entryController.step(game, GUI.Action.RIGHT, frameCount);

        verify(game, times(1)).setResolution(ResizableGUI.Resolution.FHD);


        entryController.step(game, GUI.Action.RIGHT, frameCount);

        verify(game, times(1)).setResolution(ResizableGUI.Resolution.WQHD);


        entryController.step(game, GUI.Action.RIGHT, frameCount);

        verify(game, times(1)).setResolution(ResizableGUI.Resolution.FOUR_K);


        // Reset to WXGA and test left
        entryController.step(game, GUI.Action.LEFT, frameCount);
        entryController.step(game, GUI.Action.LEFT, frameCount);
        entryController.step(game, GUI.Action.LEFT, frameCount);

        verify(game, times(2)).setResolution(ResizableGUI.Resolution.WXGA);


        entryController.step(game, GUI.Action.LEFT, frameCount);

        verify(game, times(1)).setResolution(null);
    }

    @Test
    public void testGetResolutionIndex() throws NoSuchFieldException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Integer expected1 = 0;
        Integer expected2 = -1;


        ResizableGUI.Resolution resolution = ResizableGUI.Resolution.WXGA;

        Integer result1 = invokeGetResolutionIndex(resolution);


        resolution = null;

        Integer result2 = invokeGetResolutionIndex(resolution);


        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }

    public Integer invokeGetResolutionIndex(ResizableGUI.Resolution resolution) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method getResolutionIndex = EntryController.class.getDeclaredMethod("getResolutionIndex", ResizableGUI.Resolution.class);
        getResolutionIndex.setAccessible(true);
        return (Integer) getResolutionIndex.invoke(entryController, resolution);
    }
}
