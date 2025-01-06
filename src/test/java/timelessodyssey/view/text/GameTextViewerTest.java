package timelessodyssey.view.text;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.mockito.Mockito.*;

public class GameTextViewerTest {
    private GameTextViewer gameTextViewer;
    private ResizableGUI gui;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        gameTextViewer = spy(new GameTextViewer());
        gui = mock(ResizableGUI.class);
    }

    @Test
    public void testDrawValidString() {
        gameTextViewer.draw("abc", 0, 0, null, gui);

        verify(gameTextViewer, times(1)).draw('a', 0, 0, null, gui);
    }

    @Test
    public void testDrawInvalidString() {
        gameTextViewer.draw("€aaaaa", 0, 0, null, gui);

        verify(gameTextViewer, times(1)).draw('€', 0, 0, null, gui);
    }
}
