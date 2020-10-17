package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import br.big.metalslug.util.ImageUtil;

public class Animacao {

	private List<BufferedImage> quadros = new ArrayList<BufferedImage>();

	private int quadroCorrenteAnimacao = 0;

	private Long duracaoQuadro = 50L;

	private Long ultimaAtualizacaoQuadro = 0L;

	private int quadroRestart;

	private Animador animador;

	private Animacao proximaAnimacao;

	private boolean loop;

	public Animacao(List<String> bases, int quadroRestart, long duracaoQuadro, boolean loop) {
		try {
			this.duracaoQuadro = duracaoQuadro;
			this.loop = loop;
			this.quadroRestart = quadroRestart;
			for (String b : bases) {
				File f = new File(b);
				this.quadros.add(ImageIO.read(f));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getQuadroAtual() {

		long agora = System.currentTimeMillis();

		BufferedImage quadro = this.quadros.get(quadroCorrenteAnimacao);

		if ((agora - ultimaAtualizacaoQuadro) > duracaoQuadro) {
			quadroCorrenteAnimacao++;
			ultimaAtualizacaoQuadro = agora;

			if (quadroCorrenteAnimacao == this.quadros.size())
				if (loop)
					this.quadroCorrenteAnimacao = this.quadroRestart;
				else
					animador.setAnimacaoCorrente(proximaAnimacao);
		}

		return quadro;
	}

	public void render(Graphics2D g, boolean flip, int x, int y) {
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
	
	public void resetAnimacao()
	{
		this.ultimaAtualizacaoQuadro = 0L;
		this.quadroCorrenteAnimacao = 0;
	}

}
