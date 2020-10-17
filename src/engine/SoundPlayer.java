package engine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

	private Clip clip;

	public void playMusic(String music) throws Exception {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(music).getAbsoluteFile());
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}

}
