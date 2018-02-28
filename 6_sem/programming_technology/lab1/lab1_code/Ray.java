import java.awt.*;
import java.awt.Rectangle;

public class Ray extends Segment {
	public Ray(Point p1, Point p2) {
		super(p1,p2);
	}

	@Override
	public void Draw(Graphics graphics) {
		graphics.setColor(lineColor);
		Rectangle rect = graphics.getClipBounds();
		int x,y;
		if (point.x < 0)
			x = 0;
		else if (point.x > 0)
			x = (int)rect.getWidth();
		else
			x = theCenter.x;

		int d = x - theCenter.x;
		if (d != 0)
			y = theCenter.y + d * point.y / point.x;
		else
			y = (point.y > 0) ? (int)rect.getHeight() : 0;
		graphics.drawLine(theCenter.x,theCenter.y,x,y);
	}
}