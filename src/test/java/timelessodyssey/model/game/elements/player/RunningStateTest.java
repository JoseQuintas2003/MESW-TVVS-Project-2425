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

public class RunningStateTest {
    private PlayerState runningState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        runningState = new RunningState(player);
    }

    @Test
    public void testDash() {
        when(runningState.getPlayer().isFacingRight()).thenReturn(true);
        when(runningState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(runningState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result1 = runningState.dash();


        when(runningState.getPlayer().isFacingRight()).thenReturn(false);
        when(runningState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(runningState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result2 = runningState.dash();


        assertEquals(new Vector(1.0, 0.5), result1);
        assertEquals(new Vector(-1.0, 0.5), result2);
    }

    @Test
    public void testGetNextState() {
        when(runningState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(runningState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(runningState.getPlayer().isOnGround()).thenReturn(true);
        when(runningState.getPlayer().getVelocity()).thenReturn(new Vector(2, 0.5));

        PlayerState result1 = runningState.getNextState();


        when(runningState.getPlayer().getScene().isPlayerDying()).thenReturn(true);
        when(runningState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(runningState.getPlayer().isOnGround()).thenReturn(true);
        when(runningState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result2 = runningState.getNextState();


        when(runningState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(runningState.getPlayer().isOverMaxXVelocity()).thenReturn(true);
        when(runningState.getPlayer().isOnGround()).thenReturn(true);
        when(runningState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result3 = runningState.getNextState();


        when(runningState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(runningState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(runningState.getPlayer().isOnGround()).thenReturn(false);
        when(runningState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result4 = runningState.getNextState();


        when(runningState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(runningState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(runningState.getPlayer().isOnGround()).thenReturn(true);
        when(runningState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        PlayerState result5 = runningState.getNextState();


        assertTrue(result1 instanceof RunningState);
        assertTrue(result2 instanceof DeadState);
        assertTrue(result3 instanceof DashingState);
        assertTrue(result4 instanceof JumpingState || result4 instanceof FallingState);
        assertTrue(result5 instanceof WalkingState);
    }
}
