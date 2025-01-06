package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class WalkingStateTest {
    private PlayerState walkingState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        walkingState = new WalkingState(player);
    }

    @Test
    public void testJump() {
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));
        when(walkingState.getPlayer().getJumpBoost()).thenReturn(1.0);


        Vector result = walkingState.jump();


        assertNotNull(result);
        assertEquals(new Vector(0, -0.5), result);
    }

    @Test
    public void testUpdateVelocity() {
        when(walkingState.getPlayer().getScene().getFriction()).thenReturn(0.5);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));


        Vector result = walkingState.updateVelocity(new Vector(0.5, 0.5));


        assertNotNull(result);
        assertEquals(new Vector(0.25, 0.5), result);
    }

    @Test
    public void testDash() {
        when(walkingState.getPlayer().isFacingRight()).thenReturn(true);
        when(walkingState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result1 = walkingState.dash();


        when(walkingState.getPlayer().isFacingRight()).thenReturn(false);
        when(walkingState.getPlayer().getDashBoost()).thenReturn(1.0);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        Vector result2 = walkingState.dash();


        assertEquals(new Vector(1.0, 0.5), result1);
        assertEquals(new Vector(-1.0, 0.5), result2);
    }

    @Test
    public void testGetNextState() {
        when(walkingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(walkingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(walkingState.getPlayer().isOnGround()).thenReturn(true);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result1 = walkingState.getNextState();


        when(walkingState.getPlayer().getScene().isPlayerDying()).thenReturn(true);
        when(walkingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(walkingState.getPlayer().isOnGround()).thenReturn(true);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result2 = walkingState.getNextState();


        when(walkingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(walkingState.getPlayer().isOverMaxXVelocity()).thenReturn(true);
        when(walkingState.getPlayer().isOnGround()).thenReturn(true);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result3 = walkingState.getNextState();


        when(walkingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(walkingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(walkingState.getPlayer().isOnGround()).thenReturn(false);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.8, 0.5));

        PlayerState result4 = walkingState.getNextState();


        when(walkingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(walkingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(walkingState.getPlayer().isOnGround()).thenReturn(true);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(1.7, 0.5));

        PlayerState result5 = walkingState.getNextState();


        when(walkingState.getPlayer().getScene().isPlayerDying()).thenReturn(false);
        when(walkingState.getPlayer().isOverMaxXVelocity()).thenReturn(false);
        when(walkingState.getPlayer().isOnGround()).thenReturn(true);
        when(walkingState.getPlayer().getVelocity()).thenReturn(new Vector(0.5, 0.5));

        PlayerState result6 = walkingState.getNextState();


        assertTrue(result1 instanceof WalkingState);
        assertTrue(result2 instanceof DeadState);
        assertTrue(result3 instanceof DashingState);
        assertTrue(result4 instanceof JumpingState || result4 instanceof FallingState);
        assertTrue(result5 instanceof RunningState);
        assertTrue(result6 instanceof IdleState);
    }
}
