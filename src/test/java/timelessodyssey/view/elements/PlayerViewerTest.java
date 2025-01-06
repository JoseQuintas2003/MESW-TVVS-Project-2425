package timelessodyssey.view.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timelessodyssey.gui.ResizableGUI;
import timelessodyssey.model.Vector;
import timelessodyssey.model.game.elements.player.DashingState;
import timelessodyssey.model.game.elements.player.DeadState;
import timelessodyssey.model.game.elements.player.Player;
import timelessodyssey.model.game.scene.Scene;
import timelessodyssey.view.Sprite;
import timelessodyssey.view.SpriteLoader;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class PlayerViewerTest {
    private PlayerViewer playerViewer;
    private Player player;
    private ResizableGUI gui;
    private SpriteLoader spriteLoader;

    @BeforeEach
    public void setUp() throws IOException {
        spriteLoader = spriteFilepath -> mock(Sprite.class);

        playerViewer = new PlayerViewer(spriteLoader);

        player = mock(Player.class);
        when(player.getPosition()).thenReturn(new Vector(0, 0));
        when(player.getScene()).thenReturn(new Scene(1, 1, 1));

        gui = mock(ResizableGUI.class);
    }

    @Test
    public void testDrawPlayerFACING_LEFT() {
        DashingState dashingState = new DashingState(player);
        when(player.getState()).thenReturn(dashingState);


        playerViewer.draw(player, gui, 0);


        verify(player, atLeast(1)).isFacingRight();
    }

    @Test
    public void testDrawPlayerFACING_RIGHT() {
        DashingState dashingState = new DashingState(player);
        when(player.getState()).thenReturn(dashingState);
        when(player.isFacingRight()).thenReturn(true);


        playerViewer.draw(player, gui, 0);


        verify(player, atLeast(1)).isFacingRight();
    }
}
