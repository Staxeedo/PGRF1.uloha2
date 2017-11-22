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

	public Boolean isInside(Point v) {
		int x = (int)v.getX();
		int y = (int)v.getY();
		int aX = (int)first.getX();
		int aY = (int)first.getY();
		int bX= (int)last.getX();
		int bY = (int)last.getY();
		
		
		int side = ((bX-aX)*(y-aY)-(bY-aY)*(x-aX));
		System.out.println(side);
		if(side<0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	public Point intersection(Point v1, Point v2) {
		int x0,y0;
		int x1 = (int)first.getX();
		int y1 = (int)first.getY();
		int x2 = (int)last.getX();
		int y2= (int)last.getY();
		int x3 = (int)v2.getX();
		int y3 = (int)v2.getY();
		int x4 = (int)v1.getX();
		int y4 = (int)v1.getY();
		
		
		
		x0=((x1*y2-x2*y1)*(x3-x4)-(x3*y4-x4*y3)*(x1-x2))
				/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		y0=((x1*y2-x2*y1)*(y3-y4)-(x3*y4-x4*y3)*(y1-y2))
			/((x1-x2)*(y3-y4)-(y1-y2)*(x3-x4));
		return new Point(x0,y0);
	}

}
