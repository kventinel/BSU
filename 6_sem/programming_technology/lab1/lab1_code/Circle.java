public class Circle extends Oval {

	/**
	 * 
	 * @param center
	 * @param anchor
	 */
	public Circle(Point center, Point anchor) {
		super(center,anchor,new Point(center.x + anchor.y - center.y,center.y - anchor.x + center.x));
	}

}