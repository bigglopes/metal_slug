package engine;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Animacao {

	private List<BufferedImage> bases = new ArrayList<BufferedImage>();

	private int quadroBase = 0;

	private Long duracaoQuadro = 50L;

	private Long quadroAnimacaoBase = 0L;
	
	private int quadroRestart ;

	public Animacao( List<String> bases , int quadroRestart , long duracaoQuadro ) {
		try {
			this.duracaoQuadro = duracaoQuadro;
			for (String b : bases) {
				File f = new File(b);
				this.bases.add(ImageIO.read(f));
			}
			this.quadroRestart = quadroRestart;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public BufferedImage getBase() {

		long agora = System.currentTimeMillis();

		BufferedImage quadro = this.bases.get(quadroBase);

		if ((agora - quadroAnimacaoBase) > duracaoQuadro) {
			quadroBase++;
			quadroAnimacaoBase = agora;

			if (quadroBase == this.bases.size())
				this.quadroBase = this.quadroRestart;
		}

		return quadro;
	}

}
