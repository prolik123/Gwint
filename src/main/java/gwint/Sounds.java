package gwint;

import java.io.File;

import javafx.scene.media.*;

public class Sounds {
    public static MediaPlayer backgroundMusicPlayer;

    public static MediaPlayer soundEffectPlayer;

    public static double soundEffectVolume;
    public static double backgroundMusicVolume;

    public static void playBackgroundMusic() {
        Media backgroundMusic=new Media(new File(Constants.backgroundSoundPath+"background.mp3").toURI().toString());
        backgroundMusicPlayer=new MediaPlayer(backgroundMusic);
        backgroundMusicPlayer.setVolume(backgroundMusicVolume);
        backgroundMusicPlayer.play();
    }

    public static void playSoundEffect(String name) {
        Media soundEffect=new Media(new File(Constants.backgroundSoundPath+name+".mp3").toURI().toString());
        soundEffectPlayer=new MediaPlayer(soundEffect);
        soundEffectPlayer.setVolume(soundEffectVolume);
        soundEffectPlayer.play();
    }
}
