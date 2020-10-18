package br.com.big.metalslug.engine;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class Animador {

	private HashMap<String, Animacao> mapaAnimacoes = new LinkedHashMap<>();

	private Animacao animacaoCorrente;

	public void addAnimacao(String nome, Animacao animacao) {
		animacao.setAnimador(this);
		mapaAnimacoes.put(nome, animacao);
	}

	public synchronized void transitar(String nome) {
		animacaoCorrente.resetAnimacao();
		animacaoCorrente = mapaAnimacoes.get(nome);
		animacaoCorrente.resetAnimacao();
	}

	public synchronized void transitarComVinculo(String nome) {
		this.animacaoCorrente.resetAnimacao();
		Animacao temp = this.animacaoCorrente;
		animacaoCorrente = mapaAnimacoes.get(nome);
		animacaoCorrente.setProximaAnimacao(temp);
		animacaoCorrente.resetAnimacao();
	}

	public synchronized Animacao getAnimacaoCorrente() {
		if (animacaoCorrente == null)
			throw new UnsupportedOperationException("Erro no fluxo de animacao");
		return animacaoCorrente;
	}

	public synchronized void setAnimacaoCorrente(Animacao animacaoCorrente) {
		this.animacaoCorrente = animacaoCorrente;
	}

}
