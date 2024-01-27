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
    private Clip effect;
    private Clip backing;
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

    public void playEffect(Sounds sound)
    {
        effect = play(sound, false);
    }

    public void playBacking(Sounds sound)
    {
        backing = play(sound, true);
    }

    public void stopBacking()
    {
        if (backing != null)
            backing.close();
    }

    public void stopEffect()
    {
        if (effect != null)
            effect.close();
    }

    private Clip play(Sounds sound, boolean loop)
    {
        AudioInputStream stream = sounds.get(sound);
        if (stream == null)
            return null;

        try
        {
            stream.reset();

            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            if (loop)
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            return clip;
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
