package timelessodyssey.model.game.scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import timelessodyssey.model.game.elements.player.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

public class SceneBuilderTest {
    private SceneBuilder sceneBuilder;
    private Player player;

    @BeforeEach
    public void setUp(){
        player = Mockito.mock(Player.class);
    }

    @Test
    public void testBuildValidScene() throws IOException, URISyntaxException {
        sceneBuilder = new SceneBuilder(1);
        Scene result = sceneBuilder.createScene(player);

        assertNotNull(result);
        assertEquals(20, result.getWidth());
        assertEquals(16, result.getHeight());
        assertEquals(1, result.getSceneCode());
        assertEquals(0.25, result.getGravity(), 0.01);
        assertEquals(0.75, result.getFriction(), 0.01);
    }

    @Test
    public void testBuildInvalidScene() throws URISyntaxException, IOException {
        try {
            sceneBuilder = new SceneBuilder(4000);
        } catch (FileNotFoundException e) {
            assertEquals("Level file not found!", e.getMessage());
        }
    }

    @Test
    public void testBuildSceneWithGoals() throws URISyntaxException, IOException {
        sceneBuilder = new SceneBuilder(10);
        Scene result = sceneBuilder.createScene(player);

        assertNotNull(result.getGoals());
    }

    @Test
    public void testBuildSceneWithoutPlayer() throws URISyntaxException, IOException, NoSuchFieldException, IllegalAccessException {
        sceneBuilder = new SceneBuilder(1);
        setSceneLines(List.of("#####", "###W##", "#####"));

        try {
            sceneBuilder.createScene(player);
        } catch (IllegalStateException e) {
            assertEquals("Player not found within the level file!", e.getMessage());
        }
    }

    public void setSceneLines(List<String> newLines) throws NoSuchFieldException, IllegalAccessException {
        Field linesField = SceneBuilder.class.getDeclaredField("lines");
        linesField.setAccessible(true);
        linesField.set(sceneBuilder, newLines);
    }
}
