package cz.uhk.pgrf1.c03.madr.uloha2.clip;

import java.util.ArrayList;

import cz.uhk.pgrf1.c03.madr.uloha2.model.Line;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Point;
import cz.uhk.pgrf1.c03.madr.uloha2.model.Polygon;

public class Clipper {
		Polygon clipper;

		public Clipper(Polygon pol) {
			this.clipper = pol;
			// zde volam orezavaci polygon
		}

		public Polygon clipPoly(Polygon clipPoly) {
			
			Polygon out = new Polygon(clipPoly);
			// pro veskere hrany toho orezavaciho polygonu
			// orezavaci polygon je konvexni
			for (int i = 0; i < clipper.getSize(); i++) {
				System.out.println("hrana"+i);
				Line cutter = new Line(clipper.getPoint(i), clipper.getPoint((i + 1) % clipper.getSize()));
				clipPoly=new Polygon(out);
				out.clearPolygon();
				

				// neni to ta intersection kterou uz tam mame to je intersection pro vodorovne
				// hrany
				// ten posledni bod
				System.out.println(clipPoly.getSize());
				Point v1 = new Point(clipPoly.getPoint(clipPoly.getSize() - 1));
				for (int j = 0; j < clipPoly.getSize(); j++) {
					Point v2 = new Point(clipPoly.getPoint(j));

					if (cutter.isInside(v2)) {
						if (cutter.isInside(v1) == false)

							out.add(cutter.intersection(v1, v2));
						out.add(v2);

					} else {
						if (cutter.isInside(v1)) {
							out.add(cutter.intersection(v1, v2));
						}

					}

					v1 = v2;
				}
				System.out.println(out.getSize()+" out");
				
			}

			return out;
		}

	}


