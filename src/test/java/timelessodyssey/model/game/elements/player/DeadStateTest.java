package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.scene.Scene;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;

public class DeadStateTest {
    private DeadState deadState;

    @BeforeEach
    public void setUp() {
        Player player = spy(new Player(1, 1, Mockito.mock(Scene.class)));

        deadState = new DeadState(player, 50);
    }

    @Test
    public void testGetDuration() {
        long result = deadState.getDuration();

        assertEquals(50, result);
    }

    @Test
    public void testJump() {
        Vector result = deadState.jump();

        assertEquals(new Vector(0, 0), result);
    }

    @Test
    public void testDash() {
        Vector result = deadState.dash();

        assertEquals(new Vector(0, 0), result);
    }

    @Test
    public void testUpdateVelocity() {
        Vector result = deadState.updateVelocity(new Vector(0.5, 0.5));

        long duration = deadState.getDuration();


        assertEquals(new Vector(0, 0), result);
        assertEquals(49, duration);
    }

    @Test
    public void testCreateDeathParticles() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Particle> result = invokeCreateDeathParticles();

        assertNotNull(result);
        assertEquals(21, result.size());
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

    public List<Particle> invokeCreateDeathParticles() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = DeadState.class.getDeclaredMethod("createDeathParticles");
        method.setAccessible(true);
        return (List<Particle>) method.invoke(deadState);
    }
}
