package br.big.metalslug.util;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import br.big.metalslug.elementos.Sprite;
import engine.Constantes;

public class ImageUtil {

	public static BufferedImage flip(BufferedImage myImage, Character face, int qtdeVezesTamanho) {

		AffineTransform tx = AffineTransform.getScaleInstance(1, 1);
		if (face == Constantes.LEFT) {
			tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-myImage.getWidth(null), 0);
		} else {
			tx.translate(myImage.getWidth(null), 0);
		}

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		BufferedImage tmp = op.filter(myImage, null);

		return resize(tmp, qtdeVezesTamanho);

	}

	public static BufferedImage resize(BufferedImage myImage, int qtdeVezes) {
		AffineTransform tx = AffineTransform.getScaleInstance(1, 1);

		tx.scale(qtdeVezes, qtdeVezes);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(myImage, null);
	}
	
	


}
