package br.com.big.metalslug.engine;

public abstract class ObjetoRepouso extends GameObject {

	@Override
	public void addX(int valor) {
		System.out.println("Operacao nao suportada add x");

	}

	@Override
	public void addY(int valor) {
		System.out.println("Operacao nao suportada add y");

	}

	@Override
	public boolean sofreComGravidade() {
		return false;
	}
	
	public void setEstaChao( boolean estado )
	{
		
	}

	

}
