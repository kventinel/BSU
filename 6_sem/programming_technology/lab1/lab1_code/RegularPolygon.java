public class RegularPolygon extends Polygon {

	/**
	 * 
	 * @param n
	 * @param center
	 * @param anchor
	 */
	public RegularPolygon(int n, Point center, Point anchor) {
		super(anchor,anchor);
		Point d = Point.sub(anchor,center);
		Point points[] = new Point[n];
		double dist = Point.norm(d);
		double rad = d.x != 0 ? Math.atan((double)d.y / d.x) : (d.y > 0 ? 1 : -1) * Math.PI / 2;
		double del = 2 * Math.PI / n;
		if (d.x < 0)
			rad += Math.PI;
		for (int i = 0; i < n;i++,rad+=del){
			points[i] = Point.sum(new Point((int)(dist * Math.cos(rad)),(int)(dist * Math.sin(rad))),center);
		}

		this.theCenter = points[0];
		this.anchor = Point.sub(points[1],theCenter);
		Point[] arg = new Point[n - 2];
		for (int i = 0; i < n - 2;i++)
			arg[i] = Point.sub(points[i + 2],theCenter);
		setPoints(arg);
	}
}