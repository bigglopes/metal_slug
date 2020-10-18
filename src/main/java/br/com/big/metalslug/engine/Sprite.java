package br.com.big.metalslug.engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Sprite {

	private int x;
	private int y;
	private BufferedImage image;

	public Sprite(int x, int y, BufferedImage image) {
		super();
		this.x = x;
		this.y = y;
		this.image = image;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void deslocarX(int valor) {
		this.x += valor;
	}

	public void render(Graphics2D g) {
		g.drawImage(this.image, x, y, null);
	}

	public int getLargura() {
		return this.image.getWidth();
	}

	public int getComprimento() {
		return this.image.getHeight();
	}
}
