import java.awt.*;
import java.util.*;

public class PolyLine extends Figure {

	private Collection<Segment> segments;

	public Collection<Segment> getSegments() {
		return this.segments;
	}

	public void setSegments(Collection<Segment> segments) {
		this.segments = segments;
	}

	@Override
	public void setLineColor(Color lineColor) {
		super.setLineColor(lineColor);
		for (Segment s : segments)
			s.setLineColor(lineColor);
	}

	/**
	 * 
	 * @param points
	 */
	public PolyLine(Point... points) {
		super(points[0]);
		segments = new ArrayList<>(points.length - 1);
		for (int i = 0; i < points.length - 1;i++)
			segments.add(new Segment(points[i],points[i + 1]));
	}

	/**
	 * 
	 * @param graphics
	 */
	public void Draw(Graphics graphics) {
		for(Segment segment : segments)
			segment.Draw(graphics);
	}

	/**
	 * 
	 * @param delta
	 */
	public void Move(Point delta) {
		for (Segment segment : segments)
			segment.Move(delta);
	}

}