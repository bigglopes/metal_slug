package br.com.big.metalslug.elementos;

import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.big.metalslug.engine.AnimacaoCiclica;
import br.com.big.metalslug.engine.Animador;
import br.com.big.metalslug.engine.Constantes;

public class Cronometro {

	private Animador animadorNumero1 = new Animador();
	private Animador animadorNumero2 = new Animador();

	private int posY = 50, posXNumero1 = (Constantes.RESOLUCAO_PADRAO_LARGURA / 2) - 25,
			posXNumero2 = (Constantes.RESOLUCAO_PADRAO_LARGURA / 2) + 25;

	private int valorInicial = 99;
	private Long atualizacaoAnterior = 0L;

	public Cronometro() {
		String padrao = "/diversos/numeros/numero%d.png";

		for (int i = 0; i <= 9; i++) {
			List<String> quadros = new ArrayList<String>();
			quadros.add(String.format(padrao, i));
			AnimacaoCiclica animacao = new AnimacaoCiclica(quadros, 0, Constantes.SEGUNDO, true);
			animadorNumero1.addAnimacao("numero" + i, animacao);
			animadorNumero1.setAnimacaoCorrente(animacao);
			animadorNumero2.addAnimacao("numero" + i, animacao);
			animadorNumero2.setAnimacaoCorrente(animacao);
		}
	}

	public void render(Graphics2D g) {
		updateCronometro();
		this.animadorNumero1.getAnimacaoCorrente().render(g, false, posXNumero1, posY);
		this.animadorNumero2.getAnimacaoCorrente().render(g, false, posXNumero2, posY);
	}

	private void updateCronometro() {
		Long agora = System.currentTimeMillis();

		while ((agora - atualizacaoAnterior) >= 1000) {
			atualizacaoAnterior = agora;
			valorInicial--;

			if (valorInicial < 0)
				valorInicial = 0;

			DecimalFormat df = new DecimalFormat("00");
			String valorInicialString = df.format(valorInicial);
			animadorNumero1.transitar("numero" + valorInicialString.charAt(0));
			animadorNumero2.transitar("numero" + valorInicialString.charAt(1));
		}
	}

}
