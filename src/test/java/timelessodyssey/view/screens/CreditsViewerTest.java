package timelessodyssey.view.screens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.GUI;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.menu.Entry;
import timelessodyssey.model.menu.Menu;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.menu.EntryViewer;
import timelessodyssey.view.text.TextViewer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.*;

public class CreditsViewerTest {
    private CreditsViewer creditsViewer;
    private TextViewer textViewer;
    private Credits credits;
    private ViewerProvider viewerProvider;
    private SpriteLoader spriteLoader;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        Player player = mock(Player.class);

        credits = spy(new Credits(player));

        spriteLoader = spriteFilepath -> mock(Sprite.class);

        textViewer = mock(TextViewer.class);

        viewerProvider = spy(new ViewerProvider(spriteLoader));
        when(viewerProvider.getTextViewer()).thenReturn(textViewer);

        creditsViewer = new CreditsViewer(credits, viewerProvider);
    }

    @Test
    public void testDrawMessages() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ResizableGUI gui = mock(ResizableGUI.class);

        invokeDrawMessages(gui);

        verify(textViewer, times(credits.getMessages().length)).draw(anyString(), anyDouble(), anyDouble(), any(), any());
    }

    @Test
    public void testDrawNames() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ResizableGUI gui = mock(ResizableGUI.class);

        invokeDrawNames(gui);

        verify(textViewer, times(credits.getNames().length)).draw(anyString(), anyDouble(), anyDouble(), any(), any());
    }

    public void invokeDrawMessages(ResizableGUI gui) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = CreditsViewer.class.getDeclaredMethod("drawMessages", GUI.class);
        method.setAccessible(true);
        method.invoke(creditsViewer, gui);
    }

    public void invokeDrawNames(ResizableGUI gui) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method method = CreditsViewer.class.getDeclaredMethod("drawNames", GUI.class);
        method.setAccessible(true);
        method.invoke(creditsViewer, gui);
    }
}
