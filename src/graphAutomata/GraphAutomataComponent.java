package graphAutomata;

import java.awt.geom.RectangularShape;

import javax.swing.JOptionPane;

import graph.editor.GraphComponent;
import graph.editor.Vertex;
import graph.editor.VertexInit;
import graph.editor.VertexTerminal;
import graphAutomata.Automata.Automata;

public class GraphAutomataComponent extends GraphComponent {
	private ShapeVertex shape;
	
	public void setShapeType(ShapeVertex sample) {
		// TODO Auto-generated method stub
		setShapeType(sample.getShape());
		shape = sample;
	}
	
	protected Vertex createVertex(int x, int y) {
		RectangularShape rs = newShape(x, y);
		Vertex v;
		switch (shape){
			case Init:
				v = new VertexInit(rs, Integer.toString(n++));
				break;
			case Terminal:
				v = new VertexTerminal(rs, Integer.toString(n++));
				break;
			default:
				v = new Vertex(rs, Integer.toString(n++));
				break;
		}
		vertices.add(v);
		return v;
	}
	
	public void checkString(){
		Automata automat = new Automata();
		String word = JOptionPane.showInputDialog("Input String to check");
		if(automat.recognizeAutomata(word, vertices, edges)){
			//JOptionPane.showMessageDialog(null, "String " + word + " is accepted");
		}
		else{
			//JOptionPane.showMessageDialog(null, "String " + word + " is not accepted");
		}
	}
}
