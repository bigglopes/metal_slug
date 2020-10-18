package br.com.big.metalslug.engine;

import java.awt.Graphics2D;

public interface Animacao {

	public void setAnimador(Animador animador);

	public void resetAnimacao();

	public void setProximaAnimacao(Animacao animacao);

	public void render(Graphics2D g, boolean flip, int x, int y);

	public int getLarguraQuadroCorrente();

	public int getAlturaQuadroCorrente();

}
