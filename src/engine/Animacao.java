package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import br.big.metalslug.elementos.Animador;
import br.big.metalslug.util.ImageUtil;

public class Animacao {

	private List<BufferedImage> bases = new ArrayList<BufferedImage>();

	private int quadroBase = 0;

	private Long duracaoQuadro = 50L;

	private Long quadroAnimacaoBase = 0L;

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
				this.bases.add(ImageIO.read(f));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getQuadroAtual() {

		long agora = System.currentTimeMillis();

		BufferedImage quadro = this.bases.get(quadroBase);

		if ((agora - quadroAnimacaoBase) > duracaoQuadro) {
			quadroBase++;
			quadroAnimacaoBase = agora;

			if (quadroBase == this.bases.size())
				if (loop)
					this.quadroBase = this.quadroRestart;
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

}
