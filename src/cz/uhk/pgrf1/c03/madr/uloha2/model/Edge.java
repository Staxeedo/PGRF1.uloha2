package cz.uhk.pgrf1.c03.madr.uloha2.model;


public class Edge {
	final private Point pointA;
	final private Point pointB;

	public Edge(Point a, Point b) {
		// zkontrolovat hranu jedno y musi byt vetsi nez druhy(spravna orintace) - >
		// resim getOrientedEdge
		this.pointA = a;
		this.pointB = b;
	}

	public Point getPointA() {
		return pointA;
	}

	public Point getPointB() {
		return pointB;
	}

	public Boolean isHorizontal() {

		// tohle vraci boolean podle pravdy true nebo false
		return (int) pointA.getY() == (int) pointB.getY();

	}

	public Edge getOrientedEdge() {
		// spravne orientovane hrany
		if (pointA.getY() < pointB.getY()) {

			return new Edge(pointB, pointA);

		}

		return this;

	}

	public Boolean isIntersection(int y) {
		// TODO Auto-generated method stub
		return true;

	}

	public Boolean isInside(Point p) {
		return true;
	}

	public Point intersection(Point v1, Point v2) {
		return new Point(1, 1);
	}


}
