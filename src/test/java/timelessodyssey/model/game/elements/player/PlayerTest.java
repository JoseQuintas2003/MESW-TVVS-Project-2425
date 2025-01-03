package timelessodyssey.model.game.elements.player;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.scene.Scene;

import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player(1, 1, Mockito.mock(Scene.class));
    }

    @Test
    public void testIsOverMaxXVelocity() throws NoSuchFieldException, IllegalAccessException {
        setPlayerVelocity(new Vector(1, 0));

        boolean result1 = player.isOverMaxXVelocity();


        setPlayerVelocity(new Vector(2.1, 0));

        boolean result2 = player.isOverMaxXVelocity();


        setPlayerVelocity(new Vector(-1, 0));

        boolean result3 = player.isOverMaxXVelocity();


        setPlayerVelocity(new Vector(-2.1, 0));

        boolean result4 = player.isOverMaxXVelocity();


        assertFalse(result1);
        assertTrue(result2);
        assertFalse(result3);
        assertTrue(result4);
    }

    public void setPlayerVelocity(Vector _velocity) throws NoSuchFieldException, IllegalAccessException {
        Field velocityField = Player.class.getDeclaredField("velocity");
        velocityField.setAccessible(true);
        velocityField.set(player, _velocity);
    }
}
