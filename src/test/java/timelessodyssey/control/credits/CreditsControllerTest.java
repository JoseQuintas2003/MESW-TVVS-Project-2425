package timelessodyssey.control.credits;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static timelessodyssey.gui.GUI.Action.QUIT;

import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.states.CreditsState;
import timelessodyssey.states.MainMenuState;
import timelessodyssey.states.State;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.lang.reflect.Field;

public class CreditsControllerTest {
    private Credits model;
    private CreditsController creditsController;
    private Game game;
    SpriteLoader spriteLoader;
    long frameCount = 0;

    @BeforeEach
    public void setUp() throws Exception {
        model = mock(Credits.class);
        creditsController = new CreditsController(model);
        game = spy(new Game());

        spriteLoader = spriteFilepath -> mock(Sprite.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);

        doCallRealMethod().when(game).setState(any());
    }

    @Test
    public void testStepQuit() throws Exception {
        game.setState(new CreditsState(model, spriteLoader));

        creditsController.step(game, QUIT, frameCount);

        State<?> state = getState(game);
        assertNotNull(state);
        assertTrue(state instanceof MainMenuState);
    }

    @Test
    public void testStepNotQuit() throws Exception {
        game.setState(new CreditsState(model, spriteLoader));

        creditsController.step(game, GUI.Action.UP, frameCount);
        creditsController.step(game, GUI.Action.RIGHT, frameCount);
        creditsController.step(game, GUI.Action.DOWN, frameCount);
        creditsController.step(game, GUI.Action.LEFT, frameCount);
        creditsController.step(game, GUI.Action.NONE, frameCount);
        creditsController.step(game, GUI.Action.SELECT, frameCount);
        creditsController.step(game, GUI.Action.JUMP, frameCount);
        creditsController.step(game, GUI.Action.DASH, frameCount);

        State<?> state = getState(game);
        assertNotNull(state);
        assertTrue(state instanceof CreditsState);
    }

    private State<?> getState(Game game) throws Exception {
        Field stateField = Game.class.getDeclaredField("state");
        stateField.setAccessible(true);
        return (State<?>) stateField.get(game);
    }
}
