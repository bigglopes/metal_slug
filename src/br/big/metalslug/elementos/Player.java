package br.big.metalslug.elementos;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import br.big.metalslug.util.ImageUtil;
import engine.Animacao;
import engine.Constantes;

public class Player implements KeyListener {

	private Animacao animacaoCorrente;

	private int status = 0;

	private char face = Constantes.RIGHT;

	private int posXPlayer = 10, posYPlayer = 390;

	private List<String> quadroAnimacaoParado = new ArrayList<String>();
	private List<String> quadroAnimacaoCorrendo = new ArrayList<String>();

	public Player() {

		for (int i = 1; i <= 4; i++)
			quadroAnimacaoParado.add(String.format("D:\\metal_slug\\sprites\\eri\\parado\\q%d.png", i));

		for (int i = 1; i <= 16; i++)
			quadroAnimacaoCorrendo.add(String.format("D:\\metal_slug\\sprites\\eri\\correndo\\q%d.png", i));

		animacaoCorrente = new Animacao(quadroAnimacaoParado, 0, Constantes.DURACAO_QUADRO_PARADO);

		this.status = Constantes.STATUS_PARADO;
	}

	public void render(Graphics2D g) {
		g.drawImage(ImageUtil.flip(this.animacaoCorrente.getBase(), this.face, Constantes.SCALA_SPRITES), posXPlayer,
				posYPlayer, null);

	}

	public int getPosicionamentoXPlayer() {
		return posXPlayer;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (posXPlayer < Constantes.RESOLUCAO_PADRAO_LARGURA - 5)
				posXPlayer += Constantes.DESLOCAMENTO;
			this.face = Constantes.RIGHT;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.face = Constantes.LEFT;
			if (posXPlayer > 0)
				posXPlayer -= Constantes.DESLOCAMENTO;
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (status == Constantes.STATUS_PARADO) {
				status = Constantes.STATUS_CORRENDO;
				animacaoCorrente = new Animacao(quadroAnimacaoCorrendo, 4, Constantes.DURACAO_QUADRO_CORRENDO);
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

		if (status == Constantes.STATUS_CORRENDO) {
			status = Constantes.STATUS_PARADO;
			animacaoCorrente = new Animacao(quadroAnimacaoParado, 0, Constantes.DURACAO_QUADRO_PARADO);
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
