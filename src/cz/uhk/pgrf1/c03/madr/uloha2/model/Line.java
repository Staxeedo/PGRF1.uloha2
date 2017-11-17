package cz.uhk.pgrf1.c03.madr.uloha2.model;

/**
 * trida pro uchovavani informaci o primce
 * 
 * 
 */

public class Line {
	private Point first;
	private Point last;

	public Line() {
	}

	public Line(Point f, Point l) {
		first = f;
		last = l;
	}

	public Point getFirst() {
		return this.first;

	}

	public Point getLast() {
		return this.last;

	}

	public void setFirst(Point p) {
		this.first = p;
	}

	public void setLast(Point p) {
		this.last = p;
	}

}
