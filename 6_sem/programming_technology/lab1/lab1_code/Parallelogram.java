public class Parallelogram extends Polygon {

	private int a;
	private int b;

	public int getA() {
		return this.a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return this.b;
	}

	public void setB(int b) {
		this.b = b;
	}

	/**
	 * 
	 * @param center
	 * @param anchor
	 * @param point
	 */
	public Parallelogram(Point center, Point anchor, Point point) {
		super(center,anchor);
		point = Point.sub(point,center);
		setPoints(Point.sum(this.anchor,point),point);
	}

}