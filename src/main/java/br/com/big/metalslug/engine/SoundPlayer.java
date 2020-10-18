package br.com.big.metalslug.engine;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {

	private Clip clip;

	public void playMusic(String music) throws Exception {

		DataInputStream dis = new DataInputStream(getClass().getResourceAsStream(music));
		byte[] musica = new byte[dis.available()];
		dis.readFully(musica);
		dis.close();
		ByteArrayInputStream bis = new ByteArrayInputStream(musica);
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bis);
		clip = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip.start();
	}

}
