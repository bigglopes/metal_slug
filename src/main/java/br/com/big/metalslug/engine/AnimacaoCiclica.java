package br.com.big.metalslug.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import br.com.big.metalslug.util.ImageUtil;

public class AnimacaoCiclica implements Animacao {

	private List<BufferedImage> quadros = new ArrayList<BufferedImage>();

	private int quadroCorrenteAnimacao = 0;

	private Long duracaoQuadro = 50L;

	private Long ultimaAtualizacaoQuadro = 0L;

	private int quadroRestart;

	private Animador animador;

	private Animacao proximaAnimacao;

	private boolean loop;

	public AnimacaoCiclica(List<String> pathQuadros, int quadroRestart, long duracaoQuadro, boolean loop) {
		try {
			this.duracaoQuadro = duracaoQuadro;
			this.loop = loop;
			this.quadroRestart = quadroRestart;
			for (String pathQuadro : pathQuadros) {
				InputStream is = getClass().getResourceAsStream(pathQuadro);
				this.quadros.add(ImageIO.read(is));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized BufferedImage getQuadroAtual() {

		long agora = System.currentTimeMillis();
		BufferedImage quadro = this.quadros.get(quadroCorrenteAnimacao);

		if ((agora - ultimaAtualizacaoQuadro) > duracaoQuadro) {
			quadroCorrenteAnimacao++;
			ultimaAtualizacaoQuadro = agora;

			if (quadroCorrenteAnimacao == this.quadros.size())
				if (loop)
					this.quadroCorrenteAnimacao = this.quadroRestart;
				else {
					this.quadroCorrenteAnimacao = 0;
					animador.setAnimacaoCorrente(proximaAnimacao);
				}
		}

		return quadro;
	}

	public synchronized void render(Graphics2D g, boolean flip, int x, int y) {
		g.drawImage(ImageUtil.flip(this.getQuadroAtual(), flip, Constantes.SCALA_SPRITES), x, y, null);
	}

	public Animador getAnimador() {
		return animador;
	}

	public void setAnimador(Animador animador) {
		this.animador = animador;
	}

	public Animacao getProximaAnimacao() {
		return proximaAnimacao;
	}

	public void setProximaAnimacao(Animacao proximaAnimacao) {
		this.proximaAnimacao = proximaAnimacao;
	}

	public synchronized void resetAnimacao() {
		this.ultimaAtualizacaoQuadro = 0L;
		this.quadroCorrenteAnimacao = 0;
	}

	public int getLarguraQuadroCorrente() {
		return this.getQuadroAtual().getWidth();
	}

	public int getAlturaQuadroCorrente() {
		return this.getQuadroAtual().getHeight();
	}

}
