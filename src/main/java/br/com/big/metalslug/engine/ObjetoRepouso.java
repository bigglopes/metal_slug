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

	@Override
	public void desativarGravidade() {
		System.out.println("Operacao nao suportada desativaGravidade");

	}

	@Override
	public Impulso temImpulso() {
		System.out.println("Operacao nao suportada temImpulso");
		return null;
	}

}
