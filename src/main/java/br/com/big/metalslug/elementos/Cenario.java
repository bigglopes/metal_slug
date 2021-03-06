package br.com.big.metalslug.elementos;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import br.com.big.metalslug.engine.Constantes;
import br.com.big.metalslug.engine.GameObject;
import br.com.big.metalslug.engine.RectangleCollider;
import br.com.big.metalslug.engine.Sprite;
import br.com.big.metalslug.util.ImageUtil;

public class Cenario {
	private List<Sprite> chao = new ArrayList<Sprite>();
	private List<Sprite> fundo = new ArrayList<Sprite>();
	private List<Sprite> ceu = new ArrayList<Sprite>();

	private Long FPS_NUVEM = (long) 1000 / 8;

	private Long quadroAnteriorNuvem = 0L;

	private final int INICIO_CHAO = 40;
	private final int INICIO_FUNDO = 185;
	private final int INICIO_CEU = 50;

	public Cenario() {
		try {
			InputStream is = this.getClass().getResourceAsStream("/sprites/ambiente/chao.png");
			chao.add(new Sprite(0, INICIO_CHAO, ImageUtil.resize(ImageIO.read(is), Constantes.SCALA_SPRITES)));
			is = this.getClass().getResourceAsStream("/sprites/ambiente/fundo.png");
			fundo.add(new Sprite(0, INICIO_FUNDO, ImageUtil.resize(ImageIO.read(is), Constantes.SCALA_SPRITES)));
			is = this.getClass().getResourceAsStream("/sprites/ambiente/ceu.png");
			ceu.add(new Sprite(0, INICIO_CEU, ImageUtil.resize(ImageIO.read(is), Constantes.SCALA_SPRITES)));
			ceu.add(new Sprite(0 + ceu.get(0).getLargura(), INICIO_CEU, ceu.get(0).getImage()));
			chao.get(0).setCollider(new RectangleCollider(0, INICIO_CHAO + 430, chao.get(0).getLargura(), 30));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GameObject[] getColliders() {
		GameObject gameObjects  [] = new GameObject[ this.chao.size() ];
		this.chao.toArray(gameObjects);
		return gameObjects;
	}

	public void render(Graphics2D g, int deslocamento) {

		if ((System.currentTimeMillis() - quadroAnteriorNuvem) > FPS_NUVEM) {
			this.ceu.forEach(c -> c.deslocarX(-1));
			this.quadroAnteriorNuvem = System.currentTimeMillis();
		}

		this.chao.forEach(c -> c.deslocarX(deslocamento));
		this.fundo.forEach(f -> f.deslocarX(deslocamento));

		this.ceu.forEach(c -> c.render(g));
		this.fundo.forEach(f -> f.render(g));
		this.chao.forEach(c -> c.render(g));
	}

}
