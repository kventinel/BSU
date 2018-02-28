public class EulerTriangle extends Polygon {

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
	public EulerTriangle(Point center, Point anchor, Point point) {
		super(center, anchor,Point.proection(anchor,center,point));
	}

}