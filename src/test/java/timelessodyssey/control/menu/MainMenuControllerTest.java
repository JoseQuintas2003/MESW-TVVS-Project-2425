package timelessodyssey.control.menu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.model.menu.MainMenu;

import static org.mockito.Mockito.*;

public class MainMenuControllerTest {
    private MainMenuController mainMenuController;
    private Game game;

    @BeforeEach
    public void setUp() {
        MainMenu mainMenu = mock(MainMenu.class);
        EntryController entryController = mock(EntryController.class);
        game = mock(Game.class);

        mainMenuController = spy(new MainMenuController(mainMenu, entryController));
    }

    @Test
    public void testOnQuit() {
        mainMenuController.onQuit(game);

        verify(game).setState(null);
    }
}
