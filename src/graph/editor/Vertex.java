package graph.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;

public class Vertex implements Serializable {
	RectangularShape shape;
	String label;
	private static final Point2D DELTA_LABEL = new Point(1, -1);

	public Vertex(RectangularShape rs, String label) {
		this.shape = rs;
		this.label = label;
	}

	public RectangularShape getShape() {
		return shape;
	}

	public void setShape(RectangularShape shape) {
		this.shape = shape;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean contains(int x, int y) {
		return shape.contains(x, y);
	}

	void draw(Graphics2D g2) {
		Color current = g2.getColor();
		g2.fill(shape);
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
		if (label != null){
			int width = g2.getFontMetrics().stringWidth(label);
		    g2.drawString(label, (int)(shape.getCenterX() - width/2) ,(int) (shape.getCenterY() + 5));
		}
		g2.setColor(current);
	}
}
