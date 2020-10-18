package br.com.big.metalslug.engine;

public class Impulso {

	private int valorX;

	private int valorY;

	private GameObject go;

	public Impulso(GameObject go) {
		this.go = go;
	}

	public int getValorX() {
		return valorX;
	}

	public void setValorX(int valorX) {
		this.valorX = valorX;
	}

	public int getValorY() {
		return valorY;
	}

	public void setValorY(int valorY) {
		this.valorY = valorY;
	}

}
