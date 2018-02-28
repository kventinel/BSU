import java.awt.*;

public abstract class Figure {

	protected Point theCenter;
	protected Color lineColor;

	public Color getLineColor() {
		return this.lineColor;
	}

	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	/**
	 * 
	 * @param center
	 */
	public Figure(Point center) {
		theCenter = center;
		lineColor = Color.BLACK;
	}

	/**
	 * 
	 * @param graphics
	 */
	public abstract void Draw(Graphics graphics);

	/**
	 * 
	 * @param delta
	 */
	public void Move(Point delta) {
		theCenter = new Point(theCenter.x + delta.x,theCenter.y + delta.y);
	}

	public Point Location() {
		return theCenter;
	}

}