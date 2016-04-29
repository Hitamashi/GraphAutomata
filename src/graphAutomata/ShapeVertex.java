package graphAutomata;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public enum ShapeVertex {
	Init("Init State", new Ellipse2D.Double(0, 0, 50, 50)),
	Terminal("Terminal State", new Ellipse2D.Double(0, 0, 50, 50)),
	SmCircle("Small Circle", new Ellipse2D.Double(0, 0, 50, 50)),
	BigCircle("Big Circle", new Ellipse2D.Double(0, 0, 70, 70)),
	SmSquare("Small Square",new Rectangle2D.Double(0, 0, 50, 50)),
	BigSquare("Big Square", new Rectangle2D.Double(0, 0,70, 70));
	
	private RectangularShape shape;
	private String label;
	
	ShapeVertex(String label, RectangularShape shape){
		this.shape = shape;
		this.label = label;
	}

	public RectangularShape getShape() {
		return shape;
	}
	
	public String getLabel() {
		return label;
	}
}
