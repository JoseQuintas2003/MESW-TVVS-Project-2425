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

public class IdleStateTest {
    private PlayerState idleState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        idleState = new IdleState(player);
    }

    @Test
    public void testDash() {
        when(idleState.getPlayer().isFacingRight()).thenReturn(true);
        when(idleState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(idleState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result1 = idleState.dash();


        when(idleState.getPlayer().isFacingRight()).thenReturn(false);
        when(idleState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(idleState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result2 = idleState.dash();


        assertEquals(new Vector(1.0, 0.5), result1);
        assertEquals(new Vector(-1.0, 0.5), result2);
    }

    @Test
    public void testGetNextState() {
        when(idleState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(idleState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(idleState.getPlayer().isOnGround()).thenReturn(true);
        when(idleState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        PlayerState result1 = idleState.getNextState();


        when(idleState.getPlayer().getScene().isPlayerDying()).thenReturn(true);
        when(idleState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(idleState.getPlayer().isOnGround()).thenReturn(true);
        when(idleState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result2 = idleState.getNextState();


        when(idleState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(idleState.getPlayer().isOverMaxXVelocity()).thenReturn(true);
        when(idleState.getPlayer().isOnGround()).thenReturn(true);
        when(idleState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result3 = idleState.getNextState();


        when(idleState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(idleState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(idleState.getPlayer().isOnGround()).thenReturn(false);
        when(idleState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result4 = idleState.getNextState();


        when(idleState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(idleState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(idleState.getPlayer().isOnGround()).thenReturn(true);
        when(idleState.getPlayer().getVelocity()).thenReturn(new Vector(1.7, 0.5));

        PlayerState result5 = idleState.getNextState();


        assertTrue(result1 instanceof IdleState);
        assertTrue(result2 instanceof DeadState);
        assertTrue(result3 instanceof DashingState);
        assertTrue(result4 instanceof JumpingState || result4 instanceof FallingState);
        assertTrue(result5 instanceof WalkingState);
    }
}
