package graph.editor;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RectangularShape;

public class VertexInit extends Vertex {

	public VertexInit(RectangularShape rs, String label) {
		super(rs, label);
		System.out.println("Vertex Init");
		// TODO Auto-generated constructor stub
	}
	
	@Override
	void draw(Graphics2D g2) {
		super.draw(g2);
		
		Color current = g2.getColor();
		//DRAW triangle
		//g2.setColor(Color.BLACK);
		int x = (int) shape.getMinX();
		int y = (int) shape.getCenterY();
		int sizeTriangle = 20;
		g2.drawLine(x-sizeTriangle, y-sizeTriangle, x, y);
		g2.drawLine(x-sizeTriangle, y+sizeTriangle, x, y);
		g2.drawLine(x-sizeTriangle, y-sizeTriangle, x-sizeTriangle, y+sizeTriangle);
		
		//DRAW line + arrow 
		/*
		int x2 = (int) shape.getCenterX();
		int y2 = (int)shape.getMinY();
		int x1 = x2;
		int y1 = y2  - 10;
		g2.drawLine(x1, y1-20, x2, y2);
		Edge.drawArrow(g2, x1, y1, (x1*2 + x2)/3, (y1*2 + y2)/3);
		*/
		
		g2.setColor(current);
	}

}
