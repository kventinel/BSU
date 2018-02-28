public class Point {

	public final int x;
	public final int y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	static public Point sum(Point p1, Point p2){
		return new Point(p1.x + p2.x,p1.y + p2.y);
	}

	static public Point sub(Point p1, Point p2){
		return new Point(p1.x - p2.x,p1.y - p2.y);
	}

	static public Point mul(double m,Point p1){
		double s = m * p1.x;
		double t = m * p1.y;
		return new Point((int)s,(int)t);
	}

	static public double distance(Point p1,Point p2){
		int dx = p1.x - p2.x;
		int dy = p1.y - p2.y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	static public double norm(Point p){
		return distance(new Point(0,0),p);
	}

	static public Point proection(Point center,Point anchor,Point point){
		Point d = Point.sub(anchor,center);
		if (d.x != 0){
			double k = (double)d.y/d.x;
			double y = k*k*center.y + point.y + k * (center.x - point.x);
			y /= (1 + k*k);
			int x = (int)(k * center.y - k * y + center.x);
			point = new Point(x,(int)y);
		}
		else
			point = new Point(point.x, center.y);
		return point;
	}
}