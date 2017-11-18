package cz.uhk.pgrf1.c03.madr.uloha2.render;

import java.awt.image.BufferedImage;

public class SeedFillRenderer extends Renderer {

	public SeedFillRenderer(BufferedImage img) {
		super(img);
	}

	public void draw(int x, int y, int color) {

		// point celociselna pozice v img
		int bgColor = img.getRGB(x, y);

		// if jsme uvnitr?
		if (bgColor == color) {

			img.setRGB(x, y, 0xFFFFCA);
			draw(x + 1, y, color);
			draw(x, y + 1, color);
			draw(x - 1, y, color);
			draw(x, y - 1, color);

		}

	}

}
