package br.com.big.metalslug.engine;

public abstract class GameObject implements Collider {

	public abstract void addX(int valor);

	public abstract void addY(int valor);

	public abstract boolean sofreComGravidade();
	
	public abstract void setEstaChao( boolean estado );

}
