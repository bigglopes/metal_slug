package br.com.big.metalslug.elementos;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import br.com.big.metalslug.engine.Animacao;
import br.com.big.metalslug.engine.AnimacaoCiclica;
import br.com.big.metalslug.engine.AnimacaoPulo;
import br.com.big.metalslug.engine.Animador;
import br.com.big.metalslug.engine.Constantes;
import br.com.big.metalslug.engine.GameObject;
import br.com.big.metalslug.engine.Impulso;
import br.com.big.metalslug.engine.RectangleCollider;
import br.com.big.metalslug.util.ImageUtil;

public class Player extends GameObject implements KeyListener {

	private final String ANIMACAO_CORRENDO = "correndo";
	private final String ANIMACAO_PARADO = "parado";
	private final String ANIMACAO_ATIRANDO = "atirando";
	private final String ANIMACAO_PULO_PARADO = "puloParado";

	private int status = 0;

	private char face = Constantes.RIGHT;

	private int posXPlayerMundo = 300, posXPlayerInicial = 300, posYPlayerInicial = 372,
			posYAnterior = posYPlayerInicial;

	private int parteSuperiorXPlayerTela = 200, parteSuperiorYPlayerTela = posYPlayerInicial;
	private int parteInferiorXPlayerTela = 200, parteInferiorYPlayerTela = posYPlayerInicial + 37;

	private Animador animadorSuperior = new Animador();
	private Animador animadorInferior = new Animador();

	private boolean tiroPressionado = false;

	private int tamanhoPulo = 120;

	private boolean estouChao = false;

	// TODO BASEADO NO QUADRO ATUALIZAR LARGURA DO RETANGULO E A
	private RectangleCollider collider = new RectangleCollider(parteSuperiorXPlayerTela, parteSuperiorYPlayerTela, 30,
			60);

	private List<String> initListaQuadros(String padrao, int qtdeQuadros) {
		List<String> result = new ArrayList<String>();

		for (int i = 1; i <= qtdeQuadros; i++)
			result.add(String.format(padrao, i));

		return result;
	}

	public Player() {

		List<String> quadroAnimacaoParadoSuperior = initListaQuadros("/sprites/eri/parado/f%d.png", 4);
		List<String> quadroAnimacaoAtirandoParteSuperior = initListaQuadros("/sprites/eri/tiro/t%d.png", 10);
		List<String> quadroAnimacaoCorrendoParteSuperior = initListaQuadros("/sprites/eri/correndo/fc%d.png", 12);
		List<String> quadroAnimacaoPulandoParadoParteSuperior = initListaQuadros("/sprites/eri/puloparado/fc%d.png", 6);

		List<String> quadroAnimacaoParadoInferior = initListaQuadros("/sprites/eri/parado/base%d.png", 1);
		List<String> quadroAnimacaoCorrendoParteInferior = initListaQuadros("/sprites/eri/correndo/fb%d.png", 16);
		List<String> quadroAnimacaoPulandoParadoParteInferior = initListaQuadros("/sprites/eri/puloparado/fb%d.png", 6);

		AnimacaoCiclica animacaoParadoSuperior = new AnimacaoCiclica(quadroAnimacaoParadoSuperior, 0,
				Constantes.DURACAO_QUADRO_PARADO, true);
		AnimacaoCiclica animacaoCorrendoSuperior = new AnimacaoCiclica(quadroAnimacaoCorrendoParteSuperior, 4,
				Constantes.DURACAO_QUADRO_CORRENDO, true);
		AnimacaoCiclica animacaoAtirandoCorrendo = new AnimacaoCiclica(quadroAnimacaoAtirandoParteSuperior, 0,
				Constantes.DURACAO_QUADRO_CORRENDO, false);
		AnimacaoPulo animacaoPuloParadoSuperior = new AnimacaoPulo(quadroAnimacaoPulandoParadoParteSuperior,
				parteSuperiorYPlayerTela, parteSuperiorYPlayerTela - tamanhoPulo);
		animadorSuperior.addAnimacao(ANIMACAO_PARADO, animacaoParadoSuperior);
		animadorSuperior.addAnimacao(ANIMACAO_CORRENDO, animacaoCorrendoSuperior);
		animadorSuperior.addAnimacao(ANIMACAO_ATIRANDO, animacaoAtirandoCorrendo);
		animadorSuperior.addAnimacao(ANIMACAO_PULO_PARADO, animacaoPuloParadoSuperior);

		AnimacaoCiclica animacaoParadoInferior = new AnimacaoCiclica(quadroAnimacaoParadoInferior, 0,
				Constantes.DURACAO_QUADRO_PARADO, true);
		AnimacaoCiclica animacaoCorrendoInferior = new AnimacaoCiclica(quadroAnimacaoCorrendoParteInferior, 4,
				Constantes.DURACAO_QUADRO_CORRENDO, true);
		AnimacaoPulo animacaoPuloParadoInferior = new AnimacaoPulo(quadroAnimacaoPulandoParadoParteInferior,
				parteInferiorYPlayerTela, parteInferiorYPlayerTela - tamanhoPulo);
		animadorInferior.addAnimacao(ANIMACAO_PARADO, animacaoParadoInferior);
		animadorInferior.addAnimacao(ANIMACAO_CORRENDO, animacaoCorrendoInferior);
		animadorInferior.addAnimacao(ANIMACAO_PULO_PARADO, animacaoPuloParadoInferior);

		animadorSuperior.setAnimacaoCorrente(animacaoParadoSuperior);
		animadorInferior.setAnimacaoCorrente(animacaoParadoInferior);

		this.status = Constantes.STATUS_PARADO;
	}

	public void render(Graphics2D g) {

		if (this.status == Constantes.STATUS_PULANDO && posYAnterior == parteSuperiorYPlayerTela) {
			this.pararCorrer();
		}

		posYAnterior = parteSuperiorYPlayerTela;
		Animacao aimacaoInferior = this.animadorInferior.getAnimacaoCorrente();
		aimacaoInferior.render(g, this.face == Constantes.LEFT, parteInferiorXPlayerTela, parteInferiorYPlayerTela);

		Animacao animacaoSuperior = this.animadorSuperior.getAnimacaoCorrente();
		animacaoSuperior.render(g, this.face == Constantes.LEFT, parteSuperiorXPlayerTela, parteSuperiorYPlayerTela);

		desenharCollider(g, aimacaoInferior, animacaoSuperior);

		if (posYPlayerInicial != parteSuperiorYPlayerTela)
			System.out.println("Maximo Pulo:" + parteSuperiorYPlayerTela);
	}

	private void desenharCollider(Graphics2D g, Animacao aimacaoInferior, Animacao animacaoSuperior) {
		int largura = (int) (1
				* (aimacaoInferior.getLarguraQuadroCorrente() + animacaoSuperior.getLarguraQuadroCorrente()));
		int altura = (int) (1.9
				* (aimacaoInferior.getAlturaQuadroCorrente() + animacaoSuperior.getAlturaQuadroCorrente()));

		this.collider = new RectangleCollider(this.parteSuperiorXPlayerTela, parteSuperiorYPlayerTela, largura, altura);

		ImageUtil.drawCollider(g, this.collider.getDimensoes());
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

		if (e.getKeyCode() == KeyEvent.VK_SPACE && tiroPressionado == false) {
			atirar();
			this.tiroPressionado = true;
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
		if (status == Constantes.STATUS_CORRENDO || status == Constantes.STATUS_PULANDO) {
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

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			tiroPressionado = false;
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
//			this.status = Constantes.STATUS_PULANDO;
//			Animacao aCorrente = this.animadorSuperior.getAnimacaoCorrente();
//			AnimacaoPulo aPulo = (AnimacaoPulo) this.animadorSuperior.getAnimacaoPorNome(ANIMACAO_PULO_PARADO);
//			aPulo.setProximaAnimacao(aCorrente);
//			aPulo.setPosYInicioPulo(parteSuperiorYPlayerTela);
//			aPulo.setPosYFimPulo(parteSuperiorYPlayerTela - tamanhoPulo);
//			this.animadorSuperior.transitar(ANIMACAO_PULO_PARADO);
//
//			aCorrente = this.animadorInferior.getAnimacaoCorrente();
//			aPulo = (AnimacaoPulo) this.animadorInferior.getAnimacaoPorNome(ANIMACAO_PULO_PARADO);
//			aPulo.setProximaAnimacao(aCorrente);
//			aPulo.setPosYInicioPulo(parteInferiorYPlayerTela);
//			aPulo.setPosYFimPulo(parteInferiorYPlayerTela - tamanhoPulo);
//			this.animadorInferior.transitar(ANIMACAO_PULO_PARADO);

			if (this.estouChao) {
				Impulso.getInstance().setGameObject(this);
				Impulso.getInstance().setValorX(0);
				Impulso.getInstance().setValorY(-9);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addX(int valor) {
		this.parteInferiorXPlayerTela += valor;
		this.parteSuperiorXPlayerTela += valor;
		this.collider = new RectangleCollider(this.collider.getX() + valor, this.collider.getY(),
				this.collider.getWidth(), this.collider.getHeight());
	}

	@Override
	public void addY(int valor) {
		this.parteInferiorYPlayerTela += valor;
		this.parteSuperiorYPlayerTela += valor;
		this.collider = new RectangleCollider(this.collider.getX(), this.collider.getY() + valor,
				this.collider.getWidth(), this.collider.getHeight());

	}

	@Override
	public boolean sofreComGravidade() {
		return true;
	}

	@Override
	public Rectangle getDimensoes() {
		return this.collider;
	}

	@Override
	public void setEstaChao(boolean estado) {
		this.estouChao = true;

	}

}
