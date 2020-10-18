package br.com.big.metalslug.util;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import br.com.big.metalslug.engine.Constantes;

public class ImageUtil {

	public static BufferedImage flip(BufferedImage myImage, boolean flip, double qtdeVezesTamanho) {

		BufferedImage tmp = myImage;
		if (flip) {
			AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-myImage.getWidth(null), 0);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			tmp = op.filter(myImage, null);
		}

		return resize(tmp, qtdeVezesTamanho);

	}

	public static BufferedImage resize(BufferedImage myImage, double qtdeVezes) {
		AffineTransform tx = AffineTransform.getScaleInstance(1, 1);

		tx.scale(qtdeVezes, qtdeVezes);

		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(myImage, null);
	}

	public static void drawCollider(Graphics2D g, Rectangle rectangle) {
		if (Constantes.LIGAR_COLLIDERS)
			g.drawRect((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(),
					(int) rectangle.getHeight());
	}

}
