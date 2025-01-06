package timelessodyssey.view.screens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.model.game.elements.Spike;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.elements.PlayerViewer;
import timelessodyssey.view.text.TextViewer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.*;

public class GameViewerTest {
    private GameViewer gameViewer;
    private PlayerViewer playerViewer;
    private Scene scene;
    private ViewerProvider viewerProvider;
    private SpriteLoader spriteLoader;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        scene = spy(new Scene(20, 20, 1));


        spriteLoader = spriteFilepath -> mock(Sprite.class);

        playerViewer = mock(PlayerViewer.class);


        viewerProvider = spy(new ViewerProvider(spriteLoader));
        when(viewerProvider.getPlayerViewer()).thenReturn(playerViewer);


        gameViewer = new GameViewer(scene, viewerProvider);
    }

    @Test
    public void testDraw_EMPTYLIST() throws IOException {
        ResizableGUI gui = mock(ResizableGUI.class);

        Spike[][] spikes = new Spike[20][20];

        when(scene.getPlayer()).thenReturn(null);
        when(scene.getSpikes()).thenReturn(spikes);
        when(scene.getSnow()).thenReturn(List.of());
        when(scene.getDeathParticles()).thenReturn(List.of());

        gameViewer.draw(gui, 0);


        verify(gui, times(1)).clear();
        verify(gui, times(1)).drawRectangle(0, 0, gui.getWidth(), gui.getHeight(), GameViewer.backgroundColor);
        verify(playerViewer, times(1)).draw(any(), any(), anyLong());
        verify(gui, times(1)).refresh();
    }

    @Test
    public void testDraw() throws IOException {
        ResizableGUI gui = mock(ResizableGUI.class);

        gameViewer.draw(gui, 0);

        Player player = mock(Player.class);

        Spike[][] spikes = new Spike[20][20];
        spikes[0][0] = mock(Spike.class);

        when(scene.getPlayer()).thenReturn(player);
        when(scene.getSpikes()).thenReturn(spikes);
        when(scene.getSnow()).thenReturn(List.of(mock(Particle.class)));
        when(scene.getDeathParticles()).thenReturn(List.of(mock(Particle.class)));

        verify(gui, times(1)).clear();
        verify(gui, times(1)).drawRectangle(0, 0, gui.getWidth(), gui.getHeight(), GameViewer.backgroundColor);
        verify(playerViewer, times(1)).draw(any(), any(), anyLong());
        verify(gui, times(1)).refresh();
    }
}
