package timelessodyssey.view.screens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.menu.Menu;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class ScreenViewerTest {
    private ScreenViewer screenViewer;

    @BeforeEach
    public void setUp() {
        screenViewer = spy(new ScreenViewer(mock(Menu.class)) {
            @Override
            public void draw(ResizableGUI gui, long frameCount) throws IOException {

            }
        });
    }

    @Test
    public void testDrawBackgroundAndFrameFIRST() {
        ResizableGUI gui = mock(ResizableGUI.class);


        screenViewer.drawBackgroundAndFrame(gui, mock(), mock());


        verify(gui, atLeast(1)).drawRectangle(0, 0, gui.getWidth(), 1, mock());
    }

    @Test
    public void testDrawBackgroundAndFrameSECOND() {
        ResizableGUI gui = mock(ResizableGUI.class);


        screenViewer.drawBackgroundAndFrame(gui, mock(), mock());


        verify(gui, atLeast(1)).drawRectangle(0, gui.getHeight() - 1, gui.getWidth(), 1, mock());
    }

    @Test
    public void testDrawBackgroundAndFrameTHIRD() {
        ResizableGUI gui = mock(ResizableGUI.class);


        screenViewer.drawBackgroundAndFrame(gui, mock(), mock());


        verify(gui, atLeast(1)).drawRectangle(0, 1, 1, gui.getHeight() - 2, mock());
    }

    @Test
    public void testDrawBackgroundAndFrameFOURTH() {
        ResizableGUI gui = mock(ResizableGUI.class);


        screenViewer.drawBackgroundAndFrame(gui, mock(), mock());


        verify(gui, atLeast(1)).drawRectangle(gui.getWidth() - 1, 1, 1, gui.getHeight() - 2, mock());
    }

    @Test
    public void testDrawBackgroundAndFrameFIFTH() {
        ResizableGUI gui = mock(ResizableGUI.class);


        screenViewer.drawBackgroundAndFrame(gui, mock(), mock());


        verify(gui, atLeast(1)).drawRectangle(1, 1, gui.getWidth() - 2, gui.getHeight() - 2, mock());
    }
}
