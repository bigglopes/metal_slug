package br.com.big.metalslug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import br.com.big.metalslug.elementos.Cenario;
import br.com.big.metalslug.elementos.Player;
import br.com.big.metalslug.engine.Constantes;
import br.com.big.metalslug.engine.SoundPlayer;

public class Jogo extends JFrame {

	private static final long serialVersionUID = 1L;

	private Cenario ambiente;
	private Player eri;
	private SoundPlayer soundPlayer;
	
	private Long quadroAnterior = 0L;
	
	private final String MUSICA_ESTAGIO_1 = "/music/stage1.wav";

	public Jogo() {

		this.eri = new Player();
		this.ambiente = new Cenario();
		this.soundPlayer = new SoundPlayer();
		try {
			this.soundPlayer.playMusic(MUSICA_ESTAGIO_1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.addKeyListener(eri);

		this.setSize(Constantes.RESOLUCAO_PADRAO_LARGURA, Constantes.RESOLUCAO_PADRAO_COMPRIMENTO);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void paint(Graphics g) {

		Long agora = System.currentTimeMillis();

		if ((agora - quadroAnterior) > Constantes.FPS) {
			Graphics2D g2d = (Graphics2D) g;
			this.ambiente.render((Graphics2D) g, -this.eri.getDeslocamento());
			this.eri.render(g2d);
			this.quadroAnterior = agora;
		}
	}

}
