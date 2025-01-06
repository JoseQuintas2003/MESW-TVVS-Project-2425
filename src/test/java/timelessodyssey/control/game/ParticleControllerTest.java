package timelessodyssey.control.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.Game;
import timelessodyssey.gui.GUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doCallRealMethod;

public class ParticleControllerTest {
    private Scene model;
    private ParticleController particleController;
    private Game game;
    SpriteLoader spriteLoader;
    long frameCount = 0;

    @BeforeEach
    public void setUp() throws Exception {
        model = spy(new Scene(20, 20, 1));
        model.setSnow(List.of(mock(Particle.class)));
        model.setDeathParticles(List.of(mock(Particle.class)));

        particleController = new ParticleController(model);

        game = spy(new Game());

        spriteLoader = spriteFilepath -> mock(Sprite.class);
        when(game.getSpriteLoader()).thenReturn(spriteLoader);

        doCallRealMethod().when(game).setState(any());
    }

    @Test
    public void testStepSnowParticles() {
        particleController.step(game, GUI.Action.NONE, frameCount);

        verify(model.getSnow().get(0), times(1)).setPosition(any());
    }

    @Test
    public void testStepDeathParticles() {
        particleController.step(game, GUI.Action.NONE, frameCount);

        verify(model.getDeathParticles().get(0), times(1)).setPosition(any());
    }
}
