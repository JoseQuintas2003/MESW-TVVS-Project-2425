package timelessodyssey.control.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.model.menu.SettingsMenu;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.view.SpriteLoader;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class SettingsMenuControllerTest {
    private SettingsMenuController settingsMenuController;
    private Game game;

    @BeforeEach
    public void setUp() {
        SettingsMenu settingsMenu = mock(SettingsMenu.class);
        EntryController entryController = mock(EntryController.class);
        SpriteLoader spriteLoader = mock(SpriteLoader.class);

        game = mock(Game.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);

        settingsMenuController = spy(new SettingsMenuController(settingsMenu, entryController));
    }

    @Test
    public void testOnQuit() throws IOException, URISyntaxException {
        settingsMenuController.onQuit(game);

        verify(game).setState(any(MainMenuState.class));
    }
}
