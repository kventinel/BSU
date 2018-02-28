import java.awt.*;

public class Polygon extends Shape {

	private Point[] points;

	public Point[] getPoints() {
		return this.points;
	}

	public void setPoints(Point... points) {
		this.points = points;
	}

	/**
	 * 
	 * @param points
	 */
	public Polygon(Point... points) {
		super(points[0],points[1]);
		this.points = new Point[points.length - 2];
		for (int i = 0; i < points.length - 2;i++)
			this.points[i] = Point.sub(points[i + 2],theCenter);
	}

	/**
	 * 
	 * @param graphics
	 */
	public void Draw(Graphics graphics) {
		int xs[] = new int[this.points.length + 2];
		int ys[] = new int[this.points.length + 2];
		xs[0] = theCenter.x; ys[0] = theCenter.y;
		Point temp = Point.sum(theCenter,anchor);
		xs[1] = temp.x; ys[1] = temp.y;
		int i = 2;
		for (Point point : points){
			temp = Point.sum(point,theCenter);
			xs[i] = temp.x; ys[i] = temp.y;
			i++;
		}

		graphics.setColor(fillingColor);
		graphics.fillPolygon(xs,ys,xs.length);
		graphics.setColor(lineColor);
		graphics.drawPolygon(xs,ys,xs.length);
	}

}