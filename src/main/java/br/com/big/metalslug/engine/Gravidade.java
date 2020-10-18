package br.com.big.metalslug.engine;

import java.util.ArrayList;
import java.util.List;

public class Gravidade {

	private List<GameObject> objetosJogo = new ArrayList<GameObject>();

	public void aplicarGravidade() {
		for (GameObject obj : objetosJogo) {
			if (obj.sofreComGravidade()) {
				obj.addX(Constantes.GRAVIDADE);
				for (GameObject objDestino : objetosJogo) {
					if( obj != objDestino )
					{
						
					}
				}
			}
		}
	}

}
