package timelessodyssey.model.game.scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.Element;
import timelessodyssey.model.game.elements.Star;
import timelessodyssey.model.game.elements.player.Player;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SceneTest {
    private Scene scene;
    private Player player;

    @BeforeEach
    public void setUp(){
        scene = new Scene(20, 16, 1);

        player = spy(new Player(1, 1, scene));
        doCallRealMethod().when(player).getStarCounter();
        doCallRealMethod().when(player).getWidth();
        doCallRealMethod().when(player).getHeight();

        scene.setPlayer(player);
    }

    @Test
    public void testCheckOutsideScene() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double x1;
        double y1;
        double x2;
        double y2;

        Method method = Scene.class.getDeclaredMethod("checkOutsideScene", double.class, double.class, double.class, double.class);
        method.setAccessible(true);

        x1 = 1;
        y1 = 1;
        x2 = 10;
        y2 = 8;
        boolean result1 = (boolean) method.invoke(scene, x1, x2, y1, y2);

        x1 = -1;
        y1 = 1;
        x2 = 10;
        y2 = 8;
        boolean result2 = (boolean) method.invoke(scene, x1, x2, y1, y2);

        x1 = 1;
        y1 = -1;
        x2 = 10;
        y2 = 8;
        boolean result3 = (boolean) method.invoke(scene, x1, x2, y1, y2);

        x1 = 1;
        y1 = 1;
        x2 = 300;
        y2 = 8;
        boolean result4 = (boolean) method.invoke(scene, x1, x2, y1, y2);

        x1 = 1;
        y1 = 1;
        x2 = 10;
        y2 = 300;
        boolean result5 = (boolean) method.invoke(scene, x1, x2, y1, y2);


        assertFalse(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertTrue(result4);
        assertTrue(result5);
    }

    @Test
    public void testCheckCollision() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        double x1;
        double y1;
        double x2;
        double y2;
        Star[][] layer = new Star[16][20];

        Method method = Scene.class.getDeclaredMethod("checkCollision", double.class, double.class, double.class, double.class, Element[][].class);
        method.setAccessible(true);

        x1 = 1;
        y1 = 1;
        x2 = 10;
        y2 = 8;
        boolean result1 = (boolean) method.invoke(scene, x1, x2, y1, y2, layer);

        x1 = 1;
        y1 = 1;
        x2 = 10;
        y2 = 8;
        layer[1][1] = new Star(1, 1);
        boolean result2 = (boolean) method.invoke(scene, x1, x2, y1, y2, layer);

        x1 = 1;
        y1 = -1;
        x2 = 10;
        y2 = 8;
        boolean result3 = (boolean) method.invoke(scene, x1, x2, y1, y2, layer);

        assertFalse(result1);
        assertTrue(result2);
        assertTrue(result3);
    }

    @Test
    public void testTransitionPointCheck() {
        scene.setTransitionPositionBegin(new Vector(1, 1));
        scene.setTransitionPositionEnd(new Vector(10, 10));

        scene.getPlayer().setPosition(new Vector(2, 2));

        boolean result1 = scene.isAtTransitionPosition();


        scene.setTransitionPositionBegin(new Vector(1, 1));
        scene.setTransitionPositionEnd(new Vector(10, 10));

        scene.getPlayer().setPosition(new Vector(20, 2));

        boolean result2 = scene.isAtTransitionPosition();


        scene.setTransitionPositionBegin(new Vector(1, 1));
        scene.setTransitionPositionEnd(new Vector(10, 10));

        scene.getPlayer().setPosition(new Vector(2, 20));

        boolean result3 = scene.isAtTransitionPosition();


        scene.setTransitionPositionBegin(new Vector(100, 100));
        scene.setTransitionPositionEnd(new Vector(150, 150));

        scene.getPlayer().setPosition(new Vector(125, 1));

        boolean result4 = scene.isAtTransitionPosition();


        scene.setTransitionPositionBegin(new Vector(100, 100));
        scene.setTransitionPositionEnd(new Vector(150, 150));

        scene.getPlayer().setPosition(new Vector(1, 125));

        boolean result5 = scene.isAtTransitionPosition();


        assertTrue(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertFalse(result4);
        assertFalse(result5);
    }

    @Test
    public void testUpdateStars() throws NoSuchFieldException, IllegalAccessException {
        int playerStars = player.getStarCounter();
        setSceneStars(new Star[][]{{new Star(1, 1)}, {null}});

        scene.updateStars();

        assertEquals(playerStars + 1, player.getStarCounter());
        assertNull(scene.getStars()[0][0]);
    }

    public void setSceneStars(Star[][] stars) throws NoSuchFieldException, IllegalAccessException {
        Field field = Scene.class.getDeclaredField("stars");
        field.setAccessible(true);
        field.set(scene, stars);
    }
}
