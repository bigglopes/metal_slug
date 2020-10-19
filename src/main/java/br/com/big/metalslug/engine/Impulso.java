package br.com.big.metalslug.engine;

public class Impulso {

	// fazer uma mapa para cada elemento que pular dos atributos abaixo
	private int valorX;

	private int valorY;

	private GameObject gameObject;

	private long inicoImpulso = 0L;

	private long DURACAO_IMPULSO_MS = 500;

	private static Impulso me;

	// SERA EVOLUIDA PARA OUTROS INIMOGOS
	private Impulso() {

	}

	public static Impulso getInstance() {
		if (me == null)
			me = new Impulso();
		return me;
	}

	public void setGameObject(GameObject gameObject) {
		this.gameObject = gameObject;
	}

	public int getValorX() {
		return valorX;
	}

	public void setValorX(int valorX) {
		this.inicoImpulso = System.currentTimeMillis();
		this.valorX = valorX;
	}

	public int getValorY() {
		return valorY;
	}

	public void setValorY(int valorY) {
		this.inicoImpulso = System.currentTimeMillis();
		this.valorY = valorY;
	}

	public void consumirImpulso() {

		long agora = System.currentTimeMillis();

		if (agora - inicoImpulso < DURACAO_IMPULSO_MS) {
			gameObject.addX(valorX);
			gameObject.addY(valorY);
		}
	}
	
	

}
