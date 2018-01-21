import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Diagram extends JPanel {
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private BufferedImage buffer;
	private Graphics2D g2d;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(buffer, 0, 0, null);
	}

	DiagramData data;

	public Diagram() {
		Scanner sc ;
		int n = 0;
		double values[];
		String names[];
		try {
			sc = new Scanner(new File("input.txt"));
			n = sc.nextInt();
			values = new double[n];
			names = new String[n];
			int i = 0;
			while (sc.hasNext()) {
				names[i] = sc.next();
				values[i] = sc.nextInt();
				if (values[i] < 0)
					JOptionPane.showMessageDialog(null, "Life is pain");
				i++;
			}
			if (i != n)
                JOptionPane.showMessageDialog(null, "Life is pain");
		
		data = new DiagramData(values, names, n);
		ArrayList<Double> temp = data.angles();
		buffer = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_ARGB);
		g2d = buffer.createGraphics();
		g2d.setStroke(new BasicStroke(2.0f));
		g2d.setColor(Color.BLACK);
		g2d.drawOval(50, 50, 500, 500);
		Double oldx = 0.0;
		int i1=0;
		for (Double x : temp) {
			g2d.setColor(new Color((float)Math.random(),(float)Math.random(),(float)Math.random()));
			g2d.setColor(Color.BLACK);
			if(temp.size()!=1)
			g2d.drawLine(300, 300, (int) (300 + 250 * Math.sin(oldx)),
					(int) (300 - 250 * Math.cos(oldx)));
			g2d.drawString(data.getName(i1), (int) (300 + 270 * Math.sin(oldx)),
					(int) (300 - 270 * Math.cos(oldx)));
			i1++;
			oldx += x;
			
		}
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, e.toString());
		}
		
	}
}
