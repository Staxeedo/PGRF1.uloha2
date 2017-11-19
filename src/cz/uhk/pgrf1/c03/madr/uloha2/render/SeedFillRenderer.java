package cz.uhk.pgrf1.c03.madr.uloha2.render;

import java.awt.image.BufferedImage;

import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;

public class SeedFillRenderer extends Renderer {

	public SeedFillRenderer(BufferedImage img) {
		super(img);
	}

	public void draw(Point p, int color) {
		//vzor
		int[][] pattern = {
				{0xFF0000,0xFF0000,0xFF0000},
				{0xFF0000,0xFF0000,0xFF0000},
				{0xFF0000,0xFF0000,0xFF0000},
				{0xFF0000,0xFF0000,0xFF0000},
				{0xFFFFFF,0xFFFFFF,0xFFFFFF},
				{0xFFFFFF,0xFFFFFF,0xFFFFFF},
				{0xFFFFFF,0xFFFFFF,0xFFFFFF},
				{0xFFFFFF,0xFFFFFF,0xFFFFFF}
				
		};
		int x = (int)p.getX();
		int y= (int)p.getY();
		int i = x % 8;
		int	j = y % 3;
		int pcolor=pattern[i][j];
		
		// point celociselna pozice v img
		int bgColor = img.getRGB(x, y);
		
		// if jsme uvnitr?
		if (bgColor == color) {

			//img.setRGB(x, y, 0xFFFFCA);
			img.setRGB(x, y, pcolor);
			draw(new Point(x + 1, y), color);
			draw(new Point(x, y + 1), color);
			draw(new Point(x - 1, y), color);
			draw(new Point(x, y - 1), color);

		}

	}

}
