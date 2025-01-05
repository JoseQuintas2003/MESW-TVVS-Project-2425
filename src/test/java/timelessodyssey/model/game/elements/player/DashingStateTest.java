package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class DashingStateTest {
    private PlayerState dashingState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        dashingState = new DashingState(player);
    }

    @Test
    public void testGetNextState() {
        when(dashingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(dashingState.getPlayer().getVelocity()).thenReturn(new Vector(2, 0.5));

        PlayerState result1 = dashingState.getNextState();


        when(dashingState.getPlayer().getScene().isPlayerDying()).thenReturn(true);
        when(dashingState.getPlayer().getVelocity()).thenReturn(new Vector(2, 0.5));

        PlayerState result2 = dashingState.getNextState();


        when(dashingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(dashingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        PlayerState result3 = dashingState.getNextState();


        assertTrue(result1 instanceof DashingState);
        assertTrue(result2 instanceof DeadState);
        assertTrue(result3 instanceof AfterDashState);
    }
}
