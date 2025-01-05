package timelessodyssey.control.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.menu.Entry;
import timelessodyssey.model.menu.Menu;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doCallRealMethod;

public class MenuControllerTest {
    private Menu menu;
    private EntryController entryController;
    private MenuController menuController;
    private Game game;
    private long frameCount = 0;
    private SpriteLoader spriteLoader;

    @BeforeEach
    public void setUp() throws Exception {
        game = spy(new Game());

        spriteLoader = spriteFilepath -> mock(Sprite.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);

        doCallRealMethod().when(game).setState(any());


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

        entryController = spy(new EntryController(menu));

        menuController = spy(new MenuController(menu, entryController) {
            @Override
            protected void onQuit(Game game) {
            }
        });
    }

    @Test
    public void testStep() throws IOException, URISyntaxException, FontFormatException {
        menuController.step(game, GUI.Action.UP, frameCount);

        verify(menu, times(1)).moveUp();


        menuController.step(game, GUI.Action.DOWN, frameCount);

        verify(menu, times(1)).moveDown();


        menuController.step(game, GUI.Action.QUIT, frameCount);

        verify(menuController, times(1)).onQuit(game);


        menuController.step(game, GUI.Action.RIGHT, frameCount);

        verify(entryController, times(1)).step(game, GUI.Action.RIGHT, frameCount);
    }
}
