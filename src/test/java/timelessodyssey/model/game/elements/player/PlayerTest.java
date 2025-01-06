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
    public void testGetWidth() {
        int result = player.getWidth();


        assertEquals(6, result);
    }

    @Test
    public void testGetHeight() {
        int result = player.getHeight();


        assertEquals(8, result);
    }

    @Test
    public void testIsFacingRight() throws NoSuchFieldException, IllegalAccessException {
        boolean result1 = player.isFacingRight();


        setIsFacingRight(false);


        boolean result2 = player.isFacingRight();


        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    public void testGetAcceleration() {
        double result = player.getAcceleration();

        assertEquals(0.75, result, 0);
    }

    @Test
    public void testGetJumpBoost() {
        double result = player.getJumpBoost();

        assertEquals(3.6, result, 0);
    }

    @Test
    public void testUpdatePosition() throws NoSuchFieldException, IllegalAccessException {
        setPlayerVelocity(new Vector(1, 1));


        Vector result = player.updatePosition();


        assertEquals(new Vector(2, 2), result);
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


        setPlayerVelocity(new Vector(2, 0));

        boolean result5 = player.isOverMaxXVelocity();


        assertFalse(result1);
        assertTrue(result2);
        assertFalse(result3);
        assertTrue(result4);
        assertFalse(result5);
    }

    @Test
    public void testIsOnGround() throws NoSuchFieldException, IllegalAccessException {
        setPlayerVelocity(new Vector(0, 1));

        boolean result1 = player.isOnGround();


        setPlayerVelocity(new Vector(0, 0));

        boolean result2 = player.isOnGround();


        setPlayerVelocity(new Vector(0, -1));

        boolean result3 = player.isOnGround();


        assertFalse(result1);
        assertFalse(result2);
        assertFalse(result3);
    }

    @Test
    public void testIncreaseDeaths() {
        int initalDeaths = player.getNumberOfDeaths();


        player.increaseDeaths();

        int result = player.getNumberOfDeaths();


        assertEquals(initalDeaths + 1, result);
    }

    @Test
    public void testGetNumberOfDeaths() {
        int result = player.getNumberOfDeaths();


        player.increaseDeaths();


        int result2 = player.getNumberOfDeaths();


        assertEquals(0, result);
        assertEquals(1, result2);
    }

    @Test
    public void testGetBirthTime() {
        long result = player.getBirthTime();

        assertTrue(result > 0);
    }

    public void setPlayerVelocity(Vector _velocity) throws NoSuchFieldException, IllegalAccessException {
        Field velocityField = Player.class.getDeclaredField("velocity");
        velocityField.setAccessible(true);
        velocityField.set(player, _velocity);
    }

    public void setIsFacingRight(boolean _isFacingRight) throws NoSuchFieldException, IllegalAccessException {
        Field isFacingRightField = Player.class.getDeclaredField("isFacingRight");
        isFacingRightField.setAccessible(true);
        isFacingRightField.set(player, _isFacingRight);
    }
}
