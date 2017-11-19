package cz.uhk.pgrf1.c03.madr.uloha2.render;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import cz.uhk.pgrf1.c03.madr.uloha2.model.Line;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Polygon;

public class ScanLineRenderer extends Renderer {

	List<Line> edges = new ArrayList<>();

	public ScanLineRenderer(BufferedImage img) {
		super(img);
	}

	public void fill(Polygon pol) {
		LineRenderer lren = new LineRenderer(img);
		Point min = pol.getPoint(0);
		Point max = pol.getPoint(0);
		List<Integer> points = new ArrayList<>();
		// nalezeni maxima

		for (int i = 0; i < pol.getSize(); i++) {
			if (pol.getPoint(i).getY() > max.getY()) {
				max = pol.getPoint(i);
			}

		}

		// nalezeni minima
		for (int i = 0; i < pol.getSize(); i++) {
			if (pol.getPoint(i).getY() < min.getY()) {
				min = pol.getPoint(i);

			}

		}

		// prokopiruju vrcholy z polygonu
		for (int i = 0; i < pol.getSize(); i++) {

			// spoji nam to s polednim bodem
			Line e = new Line(pol.getPoint(i), pol.getPoint((i + 1) % pol.getSize()));
			// seznam orientovany hran
			edges.add(e.getOrientedEdge());

		}

		int yMin = (int) min.getY();
		int yMax = (int) max.getY();

		int x;

		for (int y = yMin; y <= yMax; y++) {
			System.out.println(y + " souradnice");
			// projit vsechny hrany a zjistit jestli existuje prusecik
			for (Line e : edges) {
				if (e.isIntersection(y)) {
					x = e.intersection(y);
					points.add(x);
				}

			}

			// Sort
			quickSort(points,0,points.size()-1);
			//java.util.Collections.sort(points); // doplnit sort

			for (int i = 0; i < points.size() - 1; i += 2) {
				// vykreslujeme pixel po pixlu
				Line line = new Line(new Point(points.get(i), y), new Point(points.get(i + 1), y));
				lren.draw(line, 0xFF0000);
			}

			points.clear();
			// jeste prekresleni hranice
			// jina barva hranice a jina barva vyplneni
		}
		// prekreslim obrys
		PolygonRenderer pren = new PolygonRenderer(img);
		pren.draw(pol, 0x0000FF);

	}

	public void quickSort(List<Integer> list,int lBound, int rBound)
	{
		
		if(lBound < rBound)
		{
			int bound = lBound;
			for(int i = lBound +1;i<rBound;i++)
			{
				if(list.get(i)>list.get(lBound))
				{
					swap(list,lBound,bound);
				}
					
					
			}
			swap(list,lBound,bound);
			quickSort(list,lBound,bound);
			quickSort(list,bound+1,rBound);
			
		}
		
	}
	
	private void swap(List<Integer>list,int lBound,int rBound)
	{
		int tmp = list.get(rBound);
		list.set(rBound,list.get(lBound));
		list.set(lBound,tmp);
	}

}
