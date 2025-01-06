package timelessodyssey.control.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.states.CreditsState;
import timelessodyssey.states.GameState;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SceneControllerTest {
    private Scene model;
    private SceneController sceneController;
    private PlayerController playerController;
    private ParticleController particleController;
    private Game game;
    SpriteLoader spriteLoader;
    long frameCount = 0;

    @BeforeEach
    public void setUp() throws Exception {
        model = spy(new Scene(20, 20, 1));
        model.setPlayer(spy(new Player(1, 1, model)));
        model.setTransitionPositionEnd(new Vector(1, 1));
        model.setTransitionPositionBegin(new Vector(1, 1));

        playerController = new PlayerController(model);
        particleController = new ParticleController(model);
        sceneController = new SceneController(model, playerController, particleController);

        game = spy(new Game());

        spriteLoader = spriteFilepath -> mock(Sprite.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);

        doCallRealMethod().when(game).setState(any());
    }

    @Test
    public void testStepNULL() throws IOException, URISyntaxException {
        sceneController.step(game, GUI.Action.QUIT, frameCount);

        verify(game, times(1)).setState(null);
    }

    @Test
    public void testStepNOT_AT_TRANSITION_POSITION() throws IOException, URISyntaxException {
        when(model.isAtTransitionPosition()).thenReturn(false);

        sceneController.step(game, GUI.Action.NONE, frameCount);

        verify(model, times(0)).getSceneCode();
        verify(model, times(1)).updateStars();
    }

    @Test
    public void testStepAT_TRANSITION_POSITION_CREDITS() throws IOException, URISyntaxException, NoSuchFieldException, IllegalAccessException {
        when(model.isAtTransitionPosition()).thenReturn(true);
        when(game.getNumberOfLevels()).thenReturn(2);
        setSceneCode(1);

        sceneController.step(game, GUI.Action.NONE, frameCount);

        verify(model, times(1)).getSceneCode();
        verify(game, times(1)).setState(any(CreditsState.class));
        verify(model, times(1)).updateStars();
    }

    @Test
    public void testStepAT_TRANSITION_POSITION_GAME_STATE() throws IOException, URISyntaxException, NoSuchFieldException, IllegalAccessException {
        when(model.isAtTransitionPosition()).thenReturn(true);
        when(game.getNumberOfLevels()).thenReturn(2);
        setSceneCode(0);

        sceneController.step(game, GUI.Action.NONE, frameCount);

        verify(model, times(2)).getSceneCode();
        verify(game, times(1)).setState(any(GameState.class));
        verify(model, times(1)).updateStars();
    }

    public void setSceneCode(int sceneCode) throws NoSuchFieldException, IllegalAccessException {
        Field sceneCodeField = Scene.class.getDeclaredField("sceneCode");
        sceneCodeField.setAccessible(true);
        sceneCodeField.set(model, sceneCode);
    }
}
