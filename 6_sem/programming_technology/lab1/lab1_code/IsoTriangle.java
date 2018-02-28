public class IsoTriangle extends Polygon {
	/**
	 * 
	 * @param center
	 * @param anchor
	 * @param point
	 */
	public IsoTriangle(Point center, Point anchor, Point point) {
		super(anchor,center, Point.proection(Point.sum(center,Point.mul(0.5,Point.sub(anchor,center))),anchor,point));
	}

}