package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PlayerStateTest {
    private PlayerState playerState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));
        doCallRealMethod().when(player).getPosition();
        doCallRealMethod().when(player).getWidth();
        doCallRealMethod().when(player).getHeight();
        doCallRealMethod().when(player).getScene();

        playerState = new PlayerState(player) {

            @Override
            public Vector jump() {
                return null;
            }

            @Override
            public Vector dash() {
                return null;
            }

            @Override
            public Vector updateVelocity(Vector velocity) {
                return null;
            }

            @Override
            public PlayerState getNextState() {
                return null;
            }
        };
    }

    @Test
    public void testLimitVelocity() {
        Vector velocity = new Vector(1, 1);

        Vector result1 = playerState.limitVelocity(velocity);


        velocity = new Vector(-1, -1);

        Vector result2 = playerState.limitVelocity(velocity);


        velocity = new Vector(0.15, 0.2);

        Vector result3 = playerState.limitVelocity(velocity);


        velocity = new Vector(-0.15, -0.2);

        Vector result4 = playerState.limitVelocity(velocity);


        assertEquals(new Vector(1, 1), result1);
        assertEquals(new Vector(-1, -1), result2);
        assertEquals(new Vector(0, 0.2), result3);
        assertEquals(new Vector(0, -0.2), result4);
    }

    @Test
    public void testApplyCollisions() {
        when(playerState.getPlayer().getScene().collidesDown(any(), any())).thenReturn(true);
        when(playerState.getPlayer().getScene().collidesUp(any(), any())).thenReturn(true);
        when(playerState.getPlayer().getScene().collidesLeft(any(), any())).thenReturn(true);
        when(playerState.getPlayer().getScene().collidesRight(any(), any())).thenReturn(true);


        Vector velocity = new Vector(1, 1);

        Vector result1 = playerState.applyCollisions(velocity);


        velocity = new Vector(-1, -1);

        Vector result2 = playerState.applyCollisions(velocity);


        when(playerState.getPlayer().getScene().collidesDown(any(), any())).thenReturn(false);
        when(playerState.getPlayer().getScene().collidesUp(any(), any())).thenReturn(false);
        when(playerState.getPlayer().getScene().collidesLeft(any(), any())).thenReturn(false);
        when(playerState.getPlayer().getScene().collidesRight(any(), any())).thenReturn(false);


        velocity = new Vector(1, 1);

        Vector result3 = playerState.applyCollisions(velocity);


        velocity = new Vector(-1, -1);

        Vector result4 = playerState.applyCollisions(velocity);


        assertEquals(new Vector(0, 0), result1);
        assertEquals(new Vector(0, 0), result2);
        assertEquals(new Vector(1, 1), result3);
        assertEquals(new Vector(-1, -1), result4);
    }

    @Test
    public void testGetNextGroundState() {
        when(playerState.getPlayer().getVelocity()).thenReturn(new Vector(0, 0));

        PlayerState result1 = playerState.getNextGroundState();


        when(playerState.getPlayer().getVelocity()).thenReturn(new Vector(1, 0));

        PlayerState result2 = playerState.getNextGroundState();


        when(playerState.getPlayer().getVelocity()).thenReturn(new Vector(2, 0));

        PlayerState result3 = playerState.getNextGroundState();


        assertTrue(result1 instanceof IdleState);
        assertTrue(result2 instanceof WalkingState);
        assertTrue(result3 instanceof RunningState);
    }

    @Test
    public void testGetNextOnAirState() {
        when(playerState.getPlayer().getVelocity()).thenReturn(new Vector(0, -1));

        PlayerState result1 = playerState.getNextOnAirState();


        when(playerState.getPlayer().getVelocity()).thenReturn(new Vector(0, 1));

        PlayerState result2 = playerState.getNextOnAirState();


        assertTrue(result1 instanceof JumpingState);
        assertTrue(result2 instanceof FallingState);
    }
}
