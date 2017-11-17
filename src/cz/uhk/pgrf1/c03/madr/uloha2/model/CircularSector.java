package cz.uhk.pgrf1.c03.madr.uloha2.model;
/**
 * trida pro uchovavani informaci o kruhove vyseci
 * 
 * 
 */
public class CircularSector {
	private Point mid;
	double angle;
	double startAngle;

	
	public double getStartAngle() {
		return startAngle;
	}

	double r=-1;

	public double getR() {
		return r;
	}

	public void setR(double r) {
		this.r = r;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public Point getMid() {
		return mid;
	}

	public void setMid(Point p) {
		mid = p;
	}

	public void computeAngle(Point current) {

		
		this.angle = Math.atan2(current.getY() - mid.getY(), current.getX() - mid.getX());

	}

	public void computeStartAngle(Point current) {
		this.startAngle = Math.atan2(current.getY() - mid.getY(), current.getX() - mid.getX());

	}

}
