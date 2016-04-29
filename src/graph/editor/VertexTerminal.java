package graph.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

public class VertexTerminal extends Vertex {

	public VertexTerminal(RectangularShape rs, String label) {
		super(rs, label);
		System.out.println("Vertex Terminal");
		// TODO Auto-generated constructor stub
	}

	void draw(Graphics2D g2) {
		Color current = g2.getColor();
		g2.fill(shape);
		
		g2.setColor(Color.WHITE);
		if (label != null){
			int width = g2.getFontMetrics().stringWidth(label);
		    g2.drawString(label, (int)(shape.getCenterX() - width/2) ,(int) (shape.getCenterY() + 5));
		}
		
		g2.setColor(current);
		
		int radius = (int) shape.getWidth()/2 + 5;
		g2.drawOval((int)shape.getCenterX() - radius, (int)shape.getCenterY() - radius ,radius*2, radius*2);
	}
}
