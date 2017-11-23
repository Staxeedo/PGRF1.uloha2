package cz.uhk.pgrf1.c03.madr.uloha2.render;

import java.awt.image.BufferedImage;

import cz.uhk.pgrf1.c03.madr.uloha2.CanvasMouse;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;

public class SeedFillRenderer extends Renderer {

	public SeedFillRenderer(BufferedImage img) {
		super(img);
		int polColor= CanvasMouse.polColor;
		int polCutterColor=CanvasMouse.polCutterColor;
	}

	public void draw(Point p, int color, int[][] pattern) {
		// Pouzit kod z cviceni

		// vzor

		int x = (int) p.getX();
		int y = (int) p.getY();
		int i, j, pcolor;
		
	
			//predelat to na to modulo, z 
			i = x % pattern.length;// u vetsiho pattern(%8)
			j = y % pattern[0].length;// u vetsiho patter (%1)
			pcolor = pattern[i][j];
		

		// point celociselna pozice v img
		int bgColor = img.getRGB(x, y);

		// if jsme uvnitr?
		if (bgColor == color&&color!=CanvasMouse.polColor&&color!=CanvasMouse.polCutterColor) {

			// img.setRGB(x, y, 0xFFFFCA);
			img.setRGB(x, y, pcolor);
			draw(new Point(x + 1, y), color, pattern);
			draw(new Point(x, y + 1), color, pattern);
			draw(new Point(x - 1, y), color, pattern);
			draw(new Point(x, y - 1), color, pattern);

		}

	}
	

}
