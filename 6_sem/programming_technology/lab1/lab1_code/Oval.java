import java.awt.*;
import java.awt.geom.AffineTransform;

public class Oval extends Shape {

	private int a,b;

	/**
	 * 
	 * @param center
	 * @param anchor
	 * @param point
	 */
	public Oval(Point center, Point anchor, Point point) {
		super(center, anchor);
		point = Point.sub(Point.proection(center,anchor,point),center);
		a = (int)Point.norm(this.anchor);
		b = (int)Point.norm(point);
	}

	/**
	 * 
	 * @param graphics
	 */
	public void Draw(Graphics graphics) {
		double rotation = anchor.x == 0 ? Math.PI/2 : (double)anchor.y / anchor.x;
		Graphics2D g2d = (Graphics2D)graphics;
		AffineTransform old = g2d.getTransform();
		g2d.rotate(rotation,theCenter.x,theCenter.y);
		g2d.setColor(fillingColor);
		g2d.fillOval(theCenter.x - a,theCenter.y - b, 2 * a, 2 * b);
		g2d.setColor(lineColor);
		g2d.drawOval(theCenter.x - a,theCenter.y - b, 2 * a, 2 * b);
		g2d.setTransform(old);
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
}