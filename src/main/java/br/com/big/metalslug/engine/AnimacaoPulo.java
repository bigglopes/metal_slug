package br.com.big.metalslug.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.text.Utilities;

import br.com.big.metalslug.util.ImageUtil;

public class AnimacaoPulo implements Animacao {

	private List<BufferedImage> quadros = new ArrayList<BufferedImage>();

	private Animador animador;

	private Animacao proximaAnimacao;

	private int posicaoAtualPulo;

	private int posYInicioPulo, posYFimPulo;
	private int quadroAtual;



	public AnimacaoPulo(List<String> pathQuadros, int posYInicioPulo, int posYFimPulo) {
		try {
			for (String pathQuadro : pathQuadros) {
				InputStream is = getClass().getResourceAsStream(pathQuadro);
				this.quadros.add(ImageIO.read(is));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized BufferedImage getQuadroAtual() {
		return quadros.get(quadroAtual);
	}

	public synchronized void render(Graphics2D g, boolean flip, int x, int y) {

		long agora = System.currentTimeMillis();

		int qtdeDivisorias = quadros.size();

		int tamanhoDivisoria = Math.abs((this.posYFimPulo - this.posYInicioPulo) / qtdeDivisorias);

		this.quadroAtual = quadros.size() - 1;

		// descendo no pulo
		if (y >= posicaoAtualPulo) {
			int estagiosPulo = posYInicioPulo;
			for (int i = 0; i < quadros.size(); i++) {
				if (y > estagiosPulo && y < (estagiosPulo + tamanhoDivisoria)) {
					this.quadroAtual = i;
					break;
				}
				estagiosPulo += tamanhoDivisoria;
			}
		} else {
			int estagiosPulo = posYFimPulo;
			for (int i = quadros.size() - 1; i >= 0; i--) {
				if (y < estagiosPulo && y > (estagiosPulo - tamanhoDivisoria)) {
					this.quadroAtual = i;
					break;
				}
				estagiosPulo -= tamanhoDivisoria;
			}
		}
		this.posicaoAtualPulo = y;

		if (y >= posYInicioPulo)
			animador.setAnimacaoCorrente(proximaAnimacao);

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
		this.posicaoAtualPulo = 0;
		this.quadroAtual = 0;
	}

	public int getLarguraQuadroCorrente() {
		return this.getQuadroAtual().getWidth();
	}

	public int getAlturaQuadroCorrente() {
		return this.getQuadroAtual().getHeight();
	}

	public void setPosYInicioPulo(int posYInicioPulo) {
		this.posYInicioPulo = posYInicioPulo;
	}

	public void setPosYFimPulo(int posYFimPulo) {
		this.posYFimPulo = posYFimPulo;
	}

}
