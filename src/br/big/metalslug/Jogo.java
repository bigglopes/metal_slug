package br.big.metalslug;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import br.big.metalslug.elementos.Cenario;
import br.big.metalslug.elementos.Player;
import br.big.metalslug.elementos.SoundPlayer;
import engine.Constantes;

public class Jogo extends JFrame {

	private static final long serialVersionUID = 1L;

	private Cenario ambiente;
	private Player eri;
	private SoundPlayer soundPlayer;
	private Long FPS = (long) 1000 / 60;
	private Long quadroAnterior = 0L;

	public Jogo() {

		this.eri = new Player();
		this.ambiente = new Cenario(eri.getPosicionamentoXPlayer());
		this.soundPlayer = new SoundPlayer();
		try {
			this.soundPlayer.playMusic("D:\\metal_slug\\music\\stage1.wav");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.addKeyListener(eri);

		this.setSize(Constantes.RESOLUCAO_PADRAO_LARGURA, Constantes.RESOLUCAO_PADRAO_COMPRIMENTO);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void paint(Graphics g) {

		Long agora = System.currentTimeMillis();

		if ((agora - quadroAnterior) > FPS) {
			Graphics2D g2d = (Graphics2D) g;
			this.ambiente.render((Graphics2D) g, this.eri.getPosicionamentoXPlayer());
			this.eri.render(g2d);
			this.quadroAnterior = agora;
		}
	}

}
