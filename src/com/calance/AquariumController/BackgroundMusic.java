package com.calance.AquariumController;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.calance.Utility.ConsoleMessageColor.ANSI_RED;

public class BackgroundMusic {

    private InputStream inputStream;
    private AudioInputStream audioIn;
    private Clip clip;

    public BackgroundMusic() {
        inputStream = getClass().getResourceAsStream("/resources/audio/bgm.wav");
        try {
            audioIn = AudioSystem.getAudioInputStream(new BufferedInputStream(inputStream));
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            System.out.println(ANSI_RED + "BGM audio format not supported");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(ANSI_RED + "BGM audio file error");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println(ANSI_RED + "Line unavailable!");
            e.printStackTrace();
        }
    }

    public void startMusic() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void pauseMusic() {
        if (clip.isRunning()) {
            clip.stop();
        }
    }
}
