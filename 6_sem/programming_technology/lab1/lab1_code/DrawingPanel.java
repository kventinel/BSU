import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {
    private ArrayList<Figure> figures = new ArrayList<>();

    private Figure temp = null;
    private ArrayList<Point> points = new ArrayList<>(10);
    private FigureType type = null;
    private int regEdges = 0;
    private Color lineColor = Color.BLACK;
    private Color fillingColor = Color.LIGHT_GRAY;

    public DrawingPanel(JFrame window) {
        super();

        PopupMenu menu = new PopupMenu();
        Menu d1 = new Menu("1D figures");
        Menu shapes = new Menu("Shapes");
        Menu polygons = new Menu("Polygons");
        for (FigureType t : FigureType.values()) {
            MenuItem item = new MenuItem(t.name);
            if (t != FigureType.REG_POLYGON)
                item.addActionListener((e) -> type = t);
            else {
                item.addActionListener((e) -> {
                    SpinnerModel model = new SpinnerNumberModel(3, 3, 100, 1);
                    JSpinner spinner = new JSpinner(model);
                    int input = JOptionPane.showConfirmDialog(this, spinner, "Enter number of edges", JOptionPane.DEFAULT_OPTION);
                    if (input == JOptionPane.OK_OPTION) {
                        regEdges = (int) spinner.getValue();
                        type = t;
                    } else
                        type = null;
                });
            }
            switch (t.clazz) {
                case FigureType.D1:
                    d1.add(item);
                    break;
                case FigureType.SHAPES:
                    shapes.add(item);
                    break;
                case FigureType.POLYGONS:
                    polygons.add(item);
                    break;
            }
        }
        menu.add(d1);
        shapes.add(polygons);
        menu.add(shapes);
        add(menu);

        JMenuBar bar = new JMenuBar();
        JMenu colors = new JMenu("Color");
        JMenuItem lineColorItem = new JMenuItem("Set line color");
        lineColorItem.addActionListener((e)-> lineColor = ColorChooser.showDialog(window,lineColor));
        JMenuItem shapeColorItem = new JMenuItem("Set shape color");
        shapeColorItem.addActionListener((e)-> fillingColor = ColorChooser.showDialog(window,fillingColor));
        colors.add(lineColorItem);
        colors.add(shapeColorItem);
        bar.add(colors);
        window.setJMenuBar(bar);

        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
                super.mouseEntered(mouseEvent);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                super.mouseExited(mouseEvent);
                repaint();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
                super.mouseWheelMoved(mouseWheelEvent);
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent mouseEvent) {
                super.mouseDragged(mouseEvent);
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
                if (mouseEvent.isPopupTrigger()) {
                    menu.show(DrawingPanel.this, mouseEvent.getX(), mouseEvent.getY());
                    points.clear();
                    temp = null;
                } else if (type != null && (points.size() == type.pointsSize ||
                        (type.pointsSize == -1 && mouseEvent.getClickCount() == 2 && points.size() > 1))) {
                    figures.add(temp);
                    temp = null;
                    points.clear();
                }
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                if (mouseEvent.isPopupTrigger()) {
                    menu.show(DrawingPanel.this, mouseEvent.getX(), mouseEvent.getY());
                }
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                if (type != null && (points.size() == type.pointsSize - 1 || (type.pointsSize == -1 && points.size() > 0))) {
                    points.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
                    if (type != FigureType.REG_POLYGON)
                        temp = FigureType.build(lineColor,fillingColor, type, points);
                    else {
                        Shape shape = new RegularPolygon(regEdges, points.get(0), points.get(1));
                        shape.setFillingColor(fillingColor);
                        shape.setLineColor(lineColor);
                        temp = shape;
                    }
                    points.remove(points.size() - 1);
                }
                repaint();
            }
        };
        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        for (Figure f : figures)
            f.Draw(graphics);
        if (temp != null)
            temp.Draw(graphics);
    }
}
