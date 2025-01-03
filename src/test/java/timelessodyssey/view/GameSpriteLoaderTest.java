package timelessodyssey.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

public class GameSpriteLoaderTest {
    private GameSpriteLoader gameSpriteLoader;

    @BeforeEach
    public void setUp() {
        gameSpriteLoader = new GameSpriteLoader();
        gameSpriteLoader.spriteMap.put("test.png", mock(Sprite.class));
    }

    @Test
    public void testGetExistingSprite() throws Exception {
        Sprite result = gameSpriteLoader.get("test.png");

        assertNotNull(result);
        assertEquals(gameSpriteLoader.spriteMap.get("test.png"), result);
    }

    @Test
    public void testGetNewSprite() throws NullPointerException, IOException {
        assertNull(gameSpriteLoader.spriteMap.get("sprites/star.png"));

        Sprite result = gameSpriteLoader.get("sprites/star.png");

        assertNotNull(result);
        assertEquals(gameSpriteLoader.spriteMap.get("sprites/star.png"), result);
    }

}
