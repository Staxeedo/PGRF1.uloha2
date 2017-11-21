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

	public Boolean isHorizontal() {
		// usecka je vodorovna
		return (int) first.getY() == (int) last.getY();

	}

	public Line getOrientedEdge() {
		// spravne orientovane hrany
		if (first.getY() > last.getY()) {

			return new Line(last, first);

		}

		return this;

	}

	public Boolean isIntersection(int y) {
		if(y>=first.getY()&&y<last.getY())
			return true;
		return false;

	}


	public Integer intersection(int y) {
		double k = (last.getX()-first.getX()) / (last.getY()-first.getY());
		double q = first.getX() - (k * first.getY());
		int x = (int) (k*y+q) ;
		return x;
	}

	public Boolean isInside(Point v2) {
		// TODO Auto-generated method stub
		return false;
	}

	public Point intersection(Point v1, Point v2) {
		// TODO Auto-generated method stub
		return null;
	}

}
