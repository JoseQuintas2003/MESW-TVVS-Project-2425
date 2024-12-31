package timelessodyssey.sound;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import javax.sound.sampled.Clip;

import java.lang.reflect.Field;

import static org.mockito.Mockito.verify;

public class BackgroundSoundPlayerTest {
    private Clip sound;
    private BackgroundSoundPlayer backgroundSoundPlayer;

    @BeforeEach
    public void setUp() {
        sound = Mockito.mock(Clip.class);
        backgroundSoundPlayer = new BackgroundSoundPlayer(sound);
    }

    @Test
    public void testStart() {
        backgroundSoundPlayer.start();

        verify(sound).setMicrosecondPosition(0);
        verify(sound).start();
        verify(sound).loop(Clip.LOOP_CONTINUOUSLY);
    }

    @Test
    public void testStop() {
        backgroundSoundPlayer.stop();

        verify(sound).stop();
    }

    @Test
    public void testSetSound() throws NoSuchFieldException, IllegalAccessException {
        Clip newSound = Mockito.mock(Clip.class);

        backgroundSoundPlayer.setSound(newSound);

        Field soundField = BackgroundSoundPlayer.class.getDeclaredField("sound");
        soundField.setAccessible(true);
        Clip sound = (Clip) soundField.get(backgroundSoundPlayer);

        assertEquals(newSound, sound);
    }

    @Test
    public void testGetSound() {
        Clip returnedSound = backgroundSoundPlayer.getSound();

        assertEquals(sound, returnedSound);
    }
}
