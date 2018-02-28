import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public enum FigureType {
    SEGMENT("Segment",2,1),
    RAY("Ray",2,1),
    LINE("Line",2,1),
    POLYLINE("Polyline",-1,1),

    OVAL("Oval",3,2),
    CIRCLE("Circle",2,2),

    POLYGON("Polygon",-1,3),
    REG_POLYGON("Regular polygon",2,3),
    EU_TRIANGLE("Euler triangle",3,3),
    IS_TRIANGLE("Isometric triangle",3,3),
    RECTANGLE("Rectangle",3,3),
    PARALL("Parallelogram",3,3),
    RHOMBUS("Rhombus",3,3);


    public static final int D1 = 1;
    public static final int SHAPES = 2;
    public static final int POLYGONS = 3;

    public String name;
    public int clazz;
    public int pointsSize;

    FigureType(String name, int pointsSize, int clazz) {
        this.name = name;
        this.pointsSize = pointsSize;
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return name;
    }

    static Figure build(Color lineColor, Color shapeColor, FigureType type, ArrayList<Point> points){
        Figure figure;
        switch (type){
            case SEGMENT:
                figure = new Segment(points.get(0),points.get(1));
                break;
            case RAY:
                figure = new Ray(points.get(0),points.get(1));
                break;
            case LINE:
                figure = new Line(points.get(0),points.get(1));
                break;
            case POLYLINE:
                figure = new PolyLine(points.toArray(new Point[points.size()]));
                break;
            case OVAL:
                figure = new Oval(points.get(0),points.get(1),points.get(2));
                break;
            case CIRCLE:
                figure = new Circle(points.get(0),points.get(1));
                break;
            case POLYGON:
                figure = new Polygon(points.toArray(new Point[points.size()]));
                break;
            case PARALL:
                figure = new Parallelogram(points.get(0),points.get(1),points.get(2));
                break;
            case RECTANGLE:
                figure = new Rectangle(points.get(0),points.get(1),points.get(2));
                break;
            case RHOMBUS:
                figure = new Rhombus(points.get(0),points.get(1),points.get(2));
                break;
            case EU_TRIANGLE:
                figure = new EulerTriangle(points.get(0),points.get(1),points.get(2));
                break;
            case IS_TRIANGLE:
                figure = new IsoTriangle(points.get(0),points.get(1),points.get(2));
                break;
            default:
                return null;
        }
        figure.setLineColor(lineColor);
        if (figure instanceof Shape)
            ((Shape) figure).setFillingColor(shapeColor);
        return figure;
    }
}
