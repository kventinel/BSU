import java.awt.*;

public abstract class Shape extends Figure {

	protected Color fillingColor;
	protected Point anchor;

	public Shape(Point center, Point anchor) {
		super(center);
		this.anchor = Point.sub(anchor,center);
		this.fillingColor = Color.LIGHT_GRAY;
	}

	public Color getFillingColor() {
		return this.fillingColor;
	}

	public void setFillingColor(Color fillingColor) {
		this.fillingColor = fillingColor;
	}

	public Point getAnchor() {
		return anchor;
	}

	public void setAnchor(Point anchor) {
		this.anchor = anchor;
	}
}