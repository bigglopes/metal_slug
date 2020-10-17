package br.big.metalslug.elementos;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import engine.Animacao;
import engine.Animador;
import engine.Constantes;

public class Player implements KeyListener {

	private final String ANIMACAO_CORRENDO = "correndo";
	private final String ANIMACAO_PARADO = "parado";
	private final String ANIMACAO_ATIRANDO = "atirando";

	private int status = 0;

	private char face = Constantes.RIGHT;

	private int posXPlayerMundo = 300, posXPlayerInicial = 300, posYPlayer = 373;

	private int parteSuperiorPos1XPlayerTela = 197, parteSuperiorPosYPlayerTela = 373;
	private int parteInferiorXPlayerTela = 200, parteInferiorYPlayerTela = 410;

	private List<String> quadroAnimacaoParadoSuperior = new ArrayList<String>();
	private List<String> quadroAnimacaoParadoInferior = new ArrayList<String>();

	private List<String> quadroAnimacaoCorrendoParteSuperior = new ArrayList<String>();
	private List<String> quadroAnimacaoCorrendoParteInferior = new ArrayList<String>();
	private List<String> quadroAnimacaoAtirandoParteSuperior = new ArrayList<String>();

	private Animador animadorSuperior = new Animador();
	private Animador animadorInferior = new Animador();

	private List<String> initListaQuadros(String padrao, int qtdeQuadros) {
		List<String> result = new ArrayList<String>();

		for (int i = 1; i <= qtdeQuadros; i++)
			result.add(String.format(padrao, i));

		return result;
	}

	public Player() {

		this.quadroAnimacaoParadoSuperior = initListaQuadros("sprites\\eri\\parado\\f%d.png", 4);
		this.quadroAnimacaoAtirandoParteSuperior = initListaQuadros("sprites\\eri\\tiro\\t%d.png", 10);
		this.quadroAnimacaoCorrendoParteSuperior = initListaQuadros(
				"sprites\\eri\\correndo_2partes\\fc%d.png", 12);

		this.quadroAnimacaoParadoInferior = initListaQuadros("sprites\\eri\\parado\\base%d.png", 1);
		this.quadroAnimacaoCorrendoParteInferior = initListaQuadros(
				"sprites\\eri\\correndo_2partes\\fb%d.png", 16);

		Animacao animacaoParadoSuperior = new Animacao(quadroAnimacaoParadoSuperior, 0,
				Constantes.DURACAO_QUADRO_PARADO, true);
		Animacao animacaoCorrendoSuperior = new Animacao(quadroAnimacaoCorrendoParteSuperior, 4,
				Constantes.DURACAO_QUADRO_CORRENDO, true);
		Animacao animacaoAtirandoCorrendo = new Animacao(quadroAnimacaoAtirandoParteSuperior, 0,
				Constantes.DURACAO_QUADRO_CORRENDO, false);
		animadorSuperior.addAnimacao(ANIMACAO_PARADO, animacaoParadoSuperior);
		animadorSuperior.addAnimacao(ANIMACAO_CORRENDO, animacaoCorrendoSuperior);
		animadorSuperior.addAnimacao(ANIMACAO_ATIRANDO, animacaoAtirandoCorrendo);

		Animacao animacaoParadoInferior = new Animacao(quadroAnimacaoParadoInferior, 0,
				Constantes.DURACAO_QUADRO_PARADO, true);
		Animacao animacaoCorrendoInferior = new Animacao(quadroAnimacaoCorrendoParteInferior, 4,
				Constantes.DURACAO_QUADRO_CORRENDO, true);
		animadorInferior.addAnimacao(ANIMACAO_PARADO, animacaoParadoInferior);
		animadorInferior.addAnimacao(ANIMACAO_CORRENDO, animacaoCorrendoInferior);

		animadorSuperior.setAnimacaoCorrente(animacaoParadoSuperior);
		animadorInferior.setAnimacaoCorrente(animacaoParadoInferior);

		this.status = Constantes.STATUS_PARADO;
	}

	public void render(Graphics2D g) {

		this.animadorInferior.getAnimacaoCorrente().render(g, this.face == Constantes.LEFT, parteInferiorXPlayerTela,
				parteInferiorYPlayerTela);

		this.animadorSuperior.getAnimacaoCorrente().render(g, this.face == Constantes.LEFT,
				parteSuperiorPos1XPlayerTela, parteSuperiorPosYPlayerTela);

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

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			atirar();
		}

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

	private synchronized void atirar() {
		animadorSuperior.transitarComVinculo(ANIMACAO_ATIRANDO);
	}

	private synchronized void correr() {
		if (status == Constantes.STATUS_PARADO) {
			status = Constantes.STATUS_CORRENDO;

			animadorInferior.transitar(ANIMACAO_CORRENDO);
			animadorSuperior.transitar(ANIMACAO_CORRENDO);
		}
	}

	private synchronized void pararCorrer() {
		if (status == Constantes.STATUS_CORRENDO) {
			status = Constantes.STATUS_PARADO;

			animadorInferior.transitar(ANIMACAO_PARADO);
			animadorSuperior.transitar(ANIMACAO_PARADO);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
			pararCorrer();
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}
