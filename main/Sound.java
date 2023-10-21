package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Sound
{
    private Clip clip;
    private final Map<Sounds, AudioInputStream> sounds = new HashMap<>();

    public Sound()
    {
        loadSound(Sounds.Theme, "/assets/sounds/theme.wav");
        loadSound(Sounds.Step, "/assets/sounds/step.wav");
        loadSound(Sounds.Key, "/assets/sounds/coin.wav");
        loadSound(Sounds.Boots, "/assets/sounds/powerup.wav");
    }

    private void loadSound(Sounds key, String file)
    {
        try
        {
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResource(file));
            ais.mark(Integer.MAX_VALUE);
            sounds.put(key, ais);
        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println("Bad audio file format: " + file);
            System.exit(1);
        }
        catch (IOException e)
        {
            System.out.println("Failed to load audio file: " + file);
            System.exit(1);
        }
    }

    public void play(Sounds sound)
    {
        AudioInputStream stream = sounds.get(sound);
        if (stream == null)
            return;

        try
        {
            clip = AudioSystem.getClip();
            stream.reset();
            clip.open(stream);
            clip.start();
        }
        catch (Exception e)
        {
        }
    }

    public void loop()
    {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
