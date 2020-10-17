package engine;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Animador {

	private HashMap<String, Animacao> mapaAnimacoes = new LinkedHashMap();

	private Animacao animacaoCorrente;

	public void addAnimaaco(String nome, Animacao animacao) {
		mapaAnimacoes.put(nome, animacao);
	}

	public void transitar(String nome) {
		animacaoCorrente = mapaAnimacoes.get(nome);
	}

	public Animacao getAnimacaoCorrente() {
		if (animacaoCorrente == null)
			throw new UnsupportedOperationException("Erro no fluxo de animacao");
		return animacaoCorrente;
	}

	public void setAnimacaoCorrente(Animacao animacaoCorrente) {
		this.animacaoCorrente = animacaoCorrente;
	}

}
