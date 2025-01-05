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

public class FallingStateTest {
    private PlayerState fallingState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        fallingState = new FallingState(player);
    }

    @Test
    public void testDash() {
        when(fallingState.getPlayer().isFacingRight()).thenReturn(true);
        when(fallingState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(fallingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result1 = fallingState.dash();


        when(fallingState.getPlayer().isFacingRight()).thenReturn(false);
        when(fallingState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(fallingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result2 = fallingState.dash();


        assertEquals(new Vector(1.0, 0), result1);
        assertEquals(new Vector(-1.0, 0), result2);
    }

    @Test
    public void testGetNextState() {
        when(fallingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(fallingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(fallingState.getPlayer().isOnGround()).thenReturn(false);

        PlayerState result1 = fallingState.getNextState();


        when(fallingState.getPlayer().getScene().isPlayerDying()).thenReturn(true);
        when(fallingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(fallingState.getPlayer().isOnGround()).thenReturn(false);

        PlayerState result2 = fallingState.getNextState();


        when(fallingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(fallingState.getPlayer().isOverMaxXVelocity()).thenReturn(true);
        when(fallingState.getPlayer().isOnGround()).thenReturn(false);

        PlayerState result3 = fallingState.getNextState();


        when(fallingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(fallingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(fallingState.getPlayer().isOnGround()).thenReturn(true);

        PlayerState result4 = fallingState.getNextState();


        assertTrue(result1 instanceof FallingState);
        assertTrue(result2 instanceof DeadState);
        assertTrue(result3 instanceof DashingState);
        assertTrue(result4 instanceof WalkingState || result4 instanceof RunningState || result4 instanceof IdleState);
    }
}