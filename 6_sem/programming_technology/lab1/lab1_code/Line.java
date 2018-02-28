import java.awt.*;
import java.awt.Rectangle;

public class Line extends Ray {
	public Line(Point p1, Point p2) {
		super(p1,p2);
	}

	@Override
	public void Draw(Graphics graphics) {
		graphics.setColor(lineColor);
		Rectangle rect = graphics.getClipBounds();
		int dx,dy;
		if (point.x == 0)
			dx = 0;
		else if (2 * theCenter.x > rect.width)
			dx = - theCenter.x;
		else if (2 * theCenter.x < rect.width)
			dx = rect.width - theCenter.x;
		else
			dx = 0;

		if (dx != 0)
			dy = dx * point.y / point.x;
		else
			dy = (2 * point.y < rect.getHeight()) ? rect.height - point.y : -point.y;
		graphics.drawLine(theCenter.x - dx,theCenter.y - dy,theCenter.x + dx,theCenter.y + dy);
	}
}