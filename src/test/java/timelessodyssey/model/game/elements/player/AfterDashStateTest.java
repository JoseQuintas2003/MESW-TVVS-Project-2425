package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class AfterDashStateTest {
    private PlayerState afterDashState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        afterDashState = new AfterDashState(player);
    }

    @Test
    public void testGetNextState() {
        when(afterDashState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(afterDashState.getPlayer().isOnGround()).thenReturn(false);

        PlayerState result1 = afterDashState.getNextState();


        when(afterDashState.getPlayer().getScene().isPlayerDying()).thenReturn(true);
        when(afterDashState.getPlayer().isOnGround()).thenReturn(false);

        PlayerState result2 = afterDashState.getNextState();


        when(afterDashState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(afterDashState.getPlayer().isOnGround()).thenReturn(true);

        PlayerState result3 = afterDashState.getNextState();


        assertTrue(result1 instanceof AfterDashState);
        assertTrue(result2 instanceof DeadState);
        assertTrue(result3 instanceof WalkingState || result3 instanceof RunningState || result3 instanceof IdleState);
    }
}
