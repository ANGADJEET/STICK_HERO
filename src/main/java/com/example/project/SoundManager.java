package com.example.project;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;
import javafx.scene.media.AudioClip;

public class SoundManager {

    private static final String SOUND_FILE = "/Stick_Hero_Audio.mp3";
    private AudioClip audioClip;

    public SoundManager() {
        audioClip = new AudioClip(getClass().getResource(SOUND_FILE).toExternalForm());
    }

    public void playSoundInBackground() {
        new Thread(() -> audioClip.play()).start();
    }
}
