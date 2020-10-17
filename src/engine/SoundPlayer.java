package engine;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

	private Clip clip;

	public void playMusic(String music) throws Exception {
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream( music ));
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}

}
