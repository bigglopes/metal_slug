package br.big.metalslug.elementos;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import br.big.metalslug.util.ImageUtil;
import engine.Constantes;

public class Cenario {
	private BufferedImage chao;
	private BufferedImage fundo;
	private BufferedImage ceu;

	private Long FPS_NUVEM = (long) 1000 / 8;
	private Long FPS_JOGO = (long) 1000 / 60;
	private Long quadroAnterior = 0L;
	private Long quadroAnteriorNuvem = 0L;
	private int deslocamentoCeu = 0;

	private final int INICIO_CHAO = 150;
	private final int INICIO_FUNDO = 270;
	private final int INICIO_CEU = 150;

	private int posicionamentoInicialPlayer = 0;

	public Cenario(int posicionamentoPlayer) {
		try {
			File f = new File("D:\\metal_slug\\sprites\\ambiente\\chao.png");
			this.chao = ImageUtil.resize(ImageIO.read(f), Constantes.SCALA_SPRITES);
			f = new File("D:\\metal_slug\\sprites\\ambiente\\fundo.png");
			this.fundo = ImageUtil.resize(ImageIO.read(f), Constantes.SCALA_SPRITES);
			f = new File("D:\\metal_slug\\sprites\\ambiente\\ceu.png");
			this.ceu = ImageUtil.resize(ImageIO.read(f), Constantes.SCALA_SPRITES);

			this.posicionamentoInicialPlayer = posicionamentoPlayer;

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void render(Graphics2D g , int posicionamentoPlayer ) {
		double larguraTela =Constantes.RESOLUCAO_PADRAO_LARGURA;
		
		int deslocamentoAmbiente = 0;
		
		deslocamentoAmbiente = -1*(posicionamentoPlayer - posicionamentoInicialPlayer)/3;
		
		
		if( posicionamentoPlayer < posicionamentoInicialPlayer )
			deslocamentoAmbiente = 0;
		
		if ((System.currentTimeMillis() - quadroAnteriorNuvem) > FPS_NUVEM) {
			deslocamentoCeu += 1;
			this.quadroAnteriorNuvem = System.currentTimeMillis();
			
		}
			
		
		if ((System.currentTimeMillis() - quadroAnterior) > FPS_JOGO) {
			g.drawImage(this.ceu, 0 - deslocamentoCeu, INICIO_CEU, null);
			g.drawImage(this.ceu, ceu.getWidth() - deslocamentoCeu, INICIO_CEU, null);
			
			
			g.drawImage(this.fundo, deslocamentoAmbiente, INICIO_FUNDO  , null);
			g.drawImage(this.chao, deslocamentoAmbiente, INICIO_CHAO , null);
			this.quadroAnterior = System.currentTimeMillis();
		}	
		
			
		
	}

}
