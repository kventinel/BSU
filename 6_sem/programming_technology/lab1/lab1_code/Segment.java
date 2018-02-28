import java.awt.*;

public class Segment extends Figure {

	protected Point point;

	public Point getPoint() {
		return this.point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	/**
	 * 
	 * @param p1
	 * @param p2
	 */
	public Segment(Point p1, Point p2) {
		super(p1);
		point = Point.sub(p2,p1);
	}

	/**
	 * 
	 * @param graphics
	 */
	public void Draw(Graphics graphics) {
		graphics.setColor(lineColor);
		Point temp = Point.sum(theCenter,point);
		graphics.drawLine(theCenter.x,theCenter.y,temp.x,temp.y);
	}

}