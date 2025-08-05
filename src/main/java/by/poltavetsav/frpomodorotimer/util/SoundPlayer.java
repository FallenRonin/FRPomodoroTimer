package by.poltavetsav.frpomodorotimer.util;

import javafx.scene.media.AudioClip;

public class SoundPlayer {

    public static void PlaySound(String filePath, double volume) {
        try {
            String soundPath = SoundPlayer.class.getResource(filePath).toExternalForm();
            AudioClip audioClip = new AudioClip(soundPath);
            audioClip.setVolume(volume);
            audioClip.play();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

}
