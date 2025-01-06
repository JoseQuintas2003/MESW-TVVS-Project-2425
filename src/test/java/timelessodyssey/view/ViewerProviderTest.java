package timelessodyssey.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.view.elements.*;
import timelessodyssey.view.text.TextViewer;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

public class ViewerProviderTest {
    private ViewerProvider viewerProvider;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException {
        SpriteLoader spriteLoader = mock(SpriteLoader.class);

        viewerProvider = new ViewerProvider(spriteLoader);
    }

    @Test
    public void testGetParticleViewer() {
        ParticleViewer particleViewer = viewerProvider.getParticleViewer();

        assertNotNull(particleViewer);
    }

    @Test
    public void testGetPlayerViewer() {
        PlayerViewer playerViewer = viewerProvider.getPlayerViewer();

        assertNotNull(playerViewer);
    }

    @Test
    public void testGetSpikeViewer() {
        SpikeViewer spikeViewer = viewerProvider.getSpikeViewer();

        assertNotNull(spikeViewer);
    }

    @Test
    public void testGetStarViewer() {
        StarViewer starViewer = viewerProvider.getStarViewer();

        assertNotNull(starViewer);
    }

    @Test
    public void testGetTileViewer() {
        TileViewer tileViewer = viewerProvider.getTileViewer();

        assertNotNull(tileViewer);
    }

    @Test
    public void testGetTextViewer() {
        TextViewer textViewer = viewerProvider.getTextViewer();

        assertNotNull(textViewer);
    }
}
