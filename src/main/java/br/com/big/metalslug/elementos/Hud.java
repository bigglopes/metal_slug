package br.com.big.metalslug.elementos;

import java.awt.Graphics2D;

public class Hud {

	private Cronometro cronometro = new Cronometro();

	public void render(Graphics2D g) {
		this.cronometro.render(g);
	}

}
