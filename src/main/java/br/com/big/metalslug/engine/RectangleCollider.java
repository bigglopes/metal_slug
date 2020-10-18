package br.com.big.metalslug.engine;

import java.awt.Rectangle;

public class RectangleCollider extends Rectangle implements Collider {

	private static final long serialVersionUID = 1L;

	public RectangleCollider(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public RectangleCollider(double x, double y, double width, double height) {
		super((int) x, (int) y, (int) width, (int) height);
	}

	@Override
	public Rectangle getDimensoes() {
		return this;
	}

}
