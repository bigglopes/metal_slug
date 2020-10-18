package br.com.big.metalslug.engine;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import br.com.big.metalslug.util.ImageUtil;

public class Sprite extends ObjetoRepouso {

	private int x;
	private int y;
	private BufferedImage image;
	private Collider collider;

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
		if (collider != null) {
			this.collider = new RectangleCollider((int) this.collider.getDimensoes().getX() + valor,
					(int) this.collider.getDimensoes().getY(), (int) (int) this.collider.getDimensoes().getWidth(),
					(int) this.collider.getDimensoes().getHeight());
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(this.image, x, y, null);

		if (this.getCollider() != null)
			ImageUtil.drawCollider(g, this.getCollider().getDimensoes());
	}

	public int getLargura() {
		return this.image.getWidth();
	}

	public int getComprimento() {
		return this.image.getHeight();
	}

	public Collider getCollider() {
		return collider;
	}

	public void setCollider(Collider collider) {
		this.collider = collider;
	}

	@Override
	public Rectangle getDimensoes() {
		return this.collider.getDimensoes();
	}
}
