package timelessodyssey.view;

import com.googlecode.lanterna.TextColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    public void testGetImage() {
        BufferedImage image = sprite.getImage();

        assertNotNull(image);
    }

    @Test
    public void testDraw() throws IOException {
        sprite.draw(gui, 0, 0);

        verify(gui, atLeast(1)).drawPixel(anyDouble(), anyDouble(), any());
    }

    @Test
    public void testGetRGB() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        TextColor rgb = invokeGetRGB(0x12345678);

        assertNotNull(rgb);
        assertEquals(new TextColor.RGB(52, 86, 120), rgb);
    }

    @Test
    public void testGetTransparency() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        int transparency = invokeGetTransparency(0x12345678);

        assertNotEquals(0, transparency);
        assertEquals(18, transparency);
    }

    public TextColor invokeGetRGB(int color) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getRGBMethod = Sprite.class.getDeclaredMethod("getRGB", int.class);
        getRGBMethod.setAccessible(true);
        return (TextColor) getRGBMethod.invoke(sprite, color);
    }

    public int invokeGetTransparency(int color) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getTransparencyMethod = Sprite.class.getDeclaredMethod("getTransparency", int.class);
        getTransparencyMethod.setAccessible(true);
        return (int) getTransparencyMethod.invoke(sprite, color);
    }
}
