package br.com.big.metalslug.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Gravidade {

	private List<GameObject> objetosJogo = new ArrayList<GameObject>();
	private static Gravidade me;

	public static Gravidade getInstance() {
		if (me == null)
			me = new Gravidade();
		return me;
	}

	public void adicionarComponentesColisao( GameObject ... gos  )
	{
		objetosJogo.addAll( Arrays.asList( gos ) );
	}

	public void aplicarGravidade() {
		for (GameObject obj : objetosJogo) {
			if (obj.sofreComGravidade()) {
				obj.addY(Constantes.GRAVIDADE);
				for (GameObject objDestino : objetosJogo) {
					if (obj != objDestino) {
						if (obj.getDimensoes().intersects(objDestino.getDimensoes())) {
							aplicarForcaColisao(obj, objDestino);
						}
					}
				}
			}
		}
	}

	private void aplicarForcaColisao(GameObject origem, GameObject objDestino) {
		for (int i = 1; i <= Constantes.GRAVIDADE*15; i++) {
			origem.addY(-1);
			if (!origem.getDimensoes().intersects(objDestino.getDimensoes())) {
				//origem.desativarGravidade();
				return;
			}

		}
	}

}
