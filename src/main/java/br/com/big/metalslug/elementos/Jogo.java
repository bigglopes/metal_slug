package br.com.big.metalslug.elementos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import br.com.big.metalslug.engine.Constantes;
import br.com.big.metalslug.engine.Gravidade;
import br.com.big.metalslug.engine.SoundPlayer;

public class Jogo extends JFrame {

	private static final long serialVersionUID = 1L;

	private Cenario ambiente = new Cenario();;
	private Player player = new Player();;
	private SoundPlayer soundPlayer = new SoundPlayer();
	private Hud hud = new Hud();

	private Long quadroAnterior = 0L;

	private final String MUSICA_ESTAGIO_1 = "/music/stage1.wav";

	public Jogo() {

		Gravidade.getInstance().adicionarComponentesColisao(player);
		Gravidade.getInstance().adicionarComponentesColisao(ambiente.getColliders() );
		try {
			this.soundPlayer.playMusic(MUSICA_ESTAGIO_1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.addKeyListener(player);
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
			Gravidade.getInstance().aplicarGravidade();
			Graphics2D g2d = (Graphics2D) g;
			this.ambiente.render((Graphics2D) g, -this.player.getDeslocamento());
			this.player.render(g2d);
			this.hud.render(g2d);
			this.quadroAnterior = agora;
		}
	}

}
