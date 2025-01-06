package timelessodyssey.control.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.game.elements.player.IdleState;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doCallRealMethod;

public class PlayerControllerTest {
    private Scene model;
    private Player player;
    private PlayerController playerController;
    private Game game;
    SpriteLoader spriteLoader;
    long frameCount = 0;

    @BeforeEach
    public void setUp() throws Exception {
        model = spy(new Scene(20, 20, 1));
        model.setPlayer(player = spy(new Player(1, 1, model)));

        playerController = new PlayerController(model);
        game = spy(new Game());

        spriteLoader = spriteFilepath -> mock(Sprite.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);

        doCallRealMethod().when(game).setState(any());
    }

    @Test
    public void testStepNULL() {
        when(player.getNextState()).thenReturn(null);


        playerController.step(game, GUI.Action.NONE, frameCount);


        verify(model.getPlayer(), times(1)).setVelocity(player.updateVelocity());
        verify(model.getPlayer(), times(1)).setState(any(IdleState.class));
    }

    @Test
    public void testStepLEFT() {
        playerController.step(game, GUI.Action.LEFT, frameCount);


        verify(model.getPlayer(), times(1)).setVelocity(player.moveLeft());
        verify(model.getPlayer(), times(1)).setFacingRight(false);
    }

    @Test
    public void testStepRIGHT() {
        playerController.step(game, GUI.Action.RIGHT, frameCount);


        verify(model.getPlayer(), times(1)).setVelocity(player.moveRight());
        verify(model.getPlayer(), times(1)).setFacingRight(true);
    }

    @Test
    public void testStepJUMP() {
        playerController.step(game, GUI.Action.JUMP, frameCount);


        verify(model.getPlayer(), times(1)).setVelocity(player.jump());
    }

    @Test
    public void testStepDASH() {
        playerController.step(game, GUI.Action.DASH, frameCount);


        verify(model.getPlayer(), times(1)).setVelocity(player.dash());
    }
}
