public class Rectangle extends Polygon {

	/**
	 * 
	 * @param center
	 * @param anchor
	 * @param point
	 */
	public Rectangle(Point center, Point anchor, Point point) {
		super(center,anchor);
		point = Point.sub(Point.proection(center,anchor,point),center);
		setPoints(Point.sum(this.anchor,point),point);
	}

}