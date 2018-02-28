public class Rhombus extends Polygon {
    public Rhombus(Point center, Point anchor, Point point) {
        super(anchor, Point.proection(center,anchor,point));
        point = Point.proection(center,anchor,point);
        Point dp = Point.sub(center,point);
        Point da = Point.sub(center,anchor);
        setPoints(Point.mul(2,da),Point.sum(this.anchor,Point.mul(2,dp)));
    }
}