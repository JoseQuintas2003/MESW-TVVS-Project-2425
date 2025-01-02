package timelessodyssey.sound;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

import org.mockito.Mockito;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;

public class SoundLoaderTest {
    private final AudioInputStream audioInput = Mockito.mock(AudioInputStream.class);

    @Test
    public void testLoadValidSound() throws Exception {
        Clip musicClip = Mockito.mock(Clip.class);
        SoundLoader soundLoader = new SoundLoader();

        Clip result = soundLoader.loadSound(audioInput, musicClip);

        assertEquals(musicClip, result);
    }

    @Test
    public void testLoadInvalidSound() {
        SoundLoader soundLoader = new SoundLoader();

        try {
            soundLoader.loadSound(audioInput, null);
        } catch (Exception e) {
            assertEquals("Unable to load sound file!", e.getMessage());
        }
    }
}
