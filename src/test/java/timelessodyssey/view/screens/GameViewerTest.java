package timelessodyssey.view.screens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.credits.Credits;
import timelessodyssey.model.game.elements.Spike;
import timelessodyssey.model.game.elements.Star;
import timelessodyssey.model.game.elements.particles.DeathParticle;
import timelessodyssey.model.game.elements.particles.Particle;
import timelessodyssey.model.game.elements.particles.Snow;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;
import timelessodyssey.view.ViewerProvider;
import timelessodyssey.view.elements.*;
import timelessodyssey.view.text.TextViewer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.*;

public class GameViewerTest {
    private GameViewer gameViewer;
    private PlayerViewer playerViewer;
    private SpikeViewer spikeViewer;
    private TileViewer tileViewer;
    private StarViewer starViewer;
    private ParticleViewer particleViewer;
    private Scene scene;
    private ViewerProvider viewerProvider;
    private SpriteLoader spriteLoader;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        scene = spy(new Scene(20, 20, 1));


        spriteLoader = spriteFilepath -> new Sprite("sprites/star.png");

        playerViewer = mock(PlayerViewer.class);
        spikeViewer = mock(SpikeViewer.class);
        tileViewer = mock(TileViewer.class);
        starViewer = mock(StarViewer.class);
        particleViewer = mock(ParticleViewer.class);


        viewerProvider = spy(new ViewerProvider(spriteLoader));
        when(viewerProvider.getPlayerViewer()).thenReturn(playerViewer);
        when(viewerProvider.getSpikeViewer()).thenReturn(spikeViewer);
        when(viewerProvider.getTileViewer()).thenReturn(tileViewer);
        when(viewerProvider.getStarViewer()).thenReturn(starViewer);
        when(viewerProvider.getParticleViewer()).thenReturn(particleViewer);


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

        Player player = mock(Player.class);

        Spike[][] spikes = new Spike[20][20];
        spikes[0][0] = new Spike(0, 0, '^');

        Star[][] stars = new Star[20][20];
        stars[0][0] = new Star(0, 0);

        List<Particle> snow = List.of(new Snow(0, 0, 1, 1, 1));

        List<Particle> deathParticles = List.of(new DeathParticle(0, 0, 1, 1));

        when(scene.getPlayer()).thenReturn(player);
        when(scene.getSpikes()).thenReturn(spikes);
        when(scene.getStars()).thenReturn(stars);
        when(scene.getSnow()).thenReturn(snow);
        when(scene.getDeathParticles()).thenReturn(deathParticles);


        gameViewer.draw(gui, 0);


        verify(gui, times(1)).clear();
        verify(gui, times(1)).drawRectangle(0, 0, gui.getWidth(), gui.getHeight(), GameViewer.backgroundColor);
        verify(playerViewer, times(1)).draw(any(), any(), anyLong());
        verify(spikeViewer, times(1)).draw(any(), any(), anyLong());
        verify(starViewer, times(1)).draw(any(), any(), anyLong());
        verify(particleViewer, times(2)).draw(any(), any(), anyLong()); // Snow and DeathParticles
        verify(gui, times(1)).refresh();
    }
}
