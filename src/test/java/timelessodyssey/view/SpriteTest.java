package timelessodyssey.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SpriteTest {
    private Sprite sprite;
    private ResizableGUI gui;

    @BeforeEach
    public void setUp() throws IOException {
        sprite = spy(new Sprite("sprites/star.png"));
        gui = mock(ResizableGUI.class);
    }

    @Test
    public void testDraw() throws IOException {
        sprite.draw(gui, 0, 0);

        verify(gui, atLeast(1)).drawPixel(anyDouble(), anyDouble(), any());
    }
}
