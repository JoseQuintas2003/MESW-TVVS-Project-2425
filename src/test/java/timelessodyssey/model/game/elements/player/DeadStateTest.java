package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.model.game.scene.Scene;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class DeadStateTest {
    private PlayerState deadState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        deadState = new DeadState(player, 50);
    }

    @Test
    public void testGetNextState() {
        PlayerState result1 = deadState.getNextState();


        deadState = new DeadState(deadState.getPlayer(), 0);

        PlayerState result2 = deadState.getNextState();


        deadState = new DeadState(deadState.getPlayer(), -1);

        PlayerState result3 = deadState.getNextState();


        assertTrue(result1 instanceof DeadState);
        assertNull(result2);
        assertNull(result3);
    }
}
