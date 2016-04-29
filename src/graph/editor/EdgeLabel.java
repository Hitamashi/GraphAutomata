package graph.editor;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;

public class EdgeLabel extends JLabel implements Serializable {
	private static final long serialVersionUID = 1L;
	public static Dimension LabelBound = new Dimension(20,20);
	
	public EdgeLabel() {
		// TODO Auto-generated constructor stub
		setText("a");
		//setBounds(LabelBound);
	}
}
