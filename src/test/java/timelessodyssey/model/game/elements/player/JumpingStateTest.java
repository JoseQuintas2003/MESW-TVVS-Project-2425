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

public class JumpingStateTest {
    private PlayerState jumpingState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        jumpingState = new JumpingState(player);
    }

    @Test
    public void testDash() {
        when(jumpingState.getPlayer().isFacingRight()).thenReturn(true);
        when(jumpingState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(jumpingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result1 = jumpingState.dash();


        when(jumpingState.getPlayer().isFacingRight()).thenReturn(false);
        when(jumpingState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(jumpingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result2 = jumpingState.dash();


        assertEquals(new Vector(1.0, 0.5), result1);
        assertEquals(new Vector(-1.0, 0.5), result2);
    }

    @Test
    public void testGetNextState() {
        when(jumpingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(jumpingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(jumpingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, -1));

        PlayerState result1 = jumpingState.getNextState();


        when(jumpingState.getPlayer().getScene().isPlayerDying()).thenReturn(true);
        when(jumpingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(jumpingState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result2 = jumpingState.getNextState();


        when(jumpingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(jumpingState.getPlayer().isOverMaxXVelocity()).thenReturn(true);
        when(jumpingState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result3 = jumpingState.getNextState();


        when(jumpingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(jumpingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(jumpingState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result4 = jumpingState.getNextState();


        assertTrue(result1 instanceof JumpingState);
        assertTrue(result2 instanceof DeadState);
        assertTrue(result3 instanceof DashingState);
        assertTrue(result4 instanceof FallingState);
    }
}
