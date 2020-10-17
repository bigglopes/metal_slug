package br.big.metalslug.elementos;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import engine.Animacao;
import engine.Constantes;

public class Player implements KeyListener {

	private Animacao animacaoCorrente;
	private Animacao animacaoCorrente2;
	private Animacao animacaoCorrente3;
	private Animacao animacaoCorrente4;

	private int status = 0;

	private char face = Constantes.RIGHT;

	private int posXPlayerMundo = 300, posXPlayerTela = 300, posXPlayerInicial = 300, posYPlayer = 373;

	private int pos1XPlayerMundo = 200, pos1XPlayerTela = 198, pos1XPlayerInicial = 300, pos1YPlayer = 373;
	private int pos2XPlayerTela = 200, pos2YPlayer = 410;

	private List<String> quadroAnimacaoParado = new ArrayList<String>();
	private List<String> quadroAnimacaoCorrendo = new ArrayList<String>();

	private List<String> quadroAnimacaoCorrendoParteSuperior = new ArrayList<String>();
	private List<String> quadroAnimacaoCorrendoParteInferior = new ArrayList<String>();
	private List<String> quadroAnimacaoAtirandoParteSuperior = new ArrayList<String>();

	public Player() {

		for (int i = 1; i <= 4; i++)
			quadroAnimacaoParado.add(String.format("D:\\metal_slug\\sprites\\eri\\parado\\q%d.png", i));

		for (int i = 1; i <= 16; i++)
			quadroAnimacaoCorrendo.add(String.format("D:\\metal_slug\\sprites\\eri\\correndo\\q%d.png", i));

		for (int i = 1; i <= 16; i++)
			quadroAnimacaoCorrendoParteInferior
					.add(String.format("D:\\metal_slug\\sprites\\eri\\correndo_2partes\\fb%d.png", i));

		for (int i = 1; i <= 12; i++)
			quadroAnimacaoCorrendoParteSuperior
					.add(String.format("D:\\metal_slug\\sprites\\eri\\correndo_2partes\\fc%d.png", i));
		
		for (int i = 1; i <= 10; i++)
			quadroAnimacaoAtirandoParteSuperior
					.add(String.format("D:\\metal_slug\\sprites\\eri\\tiro\\t%d.png", i));

		animacaoCorrente = new Animacao(quadroAnimacaoParado, 0, Constantes.DURACAO_QUADRO_PARADO , true );
		animacaoCorrente2 = new Animacao(quadroAnimacaoCorrendoParteSuperior, 4, Constantes.DURACAO_QUADRO_CORRENDO , true );
		animacaoCorrente3 = new Animacao(quadroAnimacaoCorrendoParteInferior, 4, Constantes.DURACAO_QUADRO_CORRENDO , true );
		animacaoCorrente4 = new Animacao(quadroAnimacaoAtirandoParteSuperior, 0, Constantes.DURACAO_QUADRO_CORRENDO , true );

		this.status = Constantes.STATUS_PARADO;
	}

	public void render(Graphics2D g) {
		this.animacaoCorrente.render(g, this.face == Constantes.LEFT, posXPlayerTela, posYPlayer);

		if (this.status == Constantes.STATUS_CORRENDO) {

			this.animacaoCorrente3.render(g, this.face == Constantes.LEFT, pos2XPlayerTela, pos2YPlayer);

		//	this.animacaoCorrente2.render(g, this.face == Constantes.LEFT, pos1XPlayerTela, pos1YPlayer);
			
			this.animacaoCorrente4.render(g, this.face == Constantes.LEFT, pos1XPlayerTela, pos1YPlayer);

		}

	}

	public synchronized int getDeslocamento() {

		if (isCorrendoParaFrente())
			return Constantes.DESLOCAMENTO;

		if (isCorrendoParaTras())
			return -Constantes.DESLOCAMENTO;

		return 0;
	}

	public boolean isCorrendoParaFrente() {
		return status == Constantes.STATUS_CORRENDO && face == Constantes.RIGHT;
	}

	public boolean isCorrendoParaTras() {
		return status == Constantes.STATUS_CORRENDO && face == Constantes.LEFT;
	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
			correr();
		}

		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			posXPlayerMundo += Constantes.DESLOCAMENTO;
			this.face = Constantes.RIGHT;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.face = Constantes.LEFT;
			// barreira no canto esquerdo da tela.
			if (posXPlayerMundo > posXPlayerInicial) {
				posXPlayerMundo -= Constantes.DESLOCAMENTO;

			} else
				pararCorrer();

		}

	}

	private synchronized void correr() {
		if (status == Constantes.STATUS_PARADO) {
			status = Constantes.STATUS_CORRENDO;
			animacaoCorrente = new Animacao(quadroAnimacaoCorrendo, 4, Constantes.DURACAO_QUADRO_CORRENDO , true );
		}
	}

	private synchronized void pararCorrer() {
		if (status == Constantes.STATUS_CORRENDO) {
			status = Constantes.STATUS_PARADO;
			animacaoCorrente = new Animacao(quadroAnimacaoParado, 0, Constantes.DURACAO_QUADRO_PARADO , true );
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pararCorrer();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
