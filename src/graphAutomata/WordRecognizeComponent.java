package graphAutomata;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RectangularShape;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import graph.editor.Edge;

public class WordRecognizeComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private List<String> states;
	private String word;
	private boolean isAccepted;
	
	public WordRecognizeComponent(List<String> states, String word, boolean isAccepted) {
		// TODO Auto-generated constructor stub
		this.states = states;
		this.word = word;
		this.isAccepted = isAccepted;
		
		this.setVisible(true);
		setBorder(BorderFactory.createLoweredBevelBorder());
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setOpaque(true);
		setPreferredSize(new Dimension(400, 3000));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		int x = 220, y = 80;
		int space = 80;
		for(int i=0; i < states.size(); i++){
			RectangularShape shape = ShapeVertex.SmCircle.getShape();
			
			//Draw edges
			if(i < word.length()){
				drawEdge(g2, x, y, space);
				String transLabel = Character.toString(word.charAt(i));
				//Label for edge
				drawLabelEdge(g2, x, y, space, transLabel);
			}
			
			if(word.length() <= states.size() -1 && i == states.size() -1){
				drawEdge(g2, x, y, space);
			}
			
			//Draw State
			Color c = isAccepted? Color.GREEN : Color.GRAY;
			drawState(g2, x, y, shape, c);
			String label = states.get(i);
			drawLabelState(g2, label, shape);
		    
		    y=y+space;
		}
		
		if(!isAccepted){
			RectangularShape shape = ShapeVertex.SmCircle.getShape();
			drawState(g2, x, y, shape, Color.RED);
			drawLabelState(g2, "STOP", shape);
		}
		else{
			RectangularShape shape = ShapeVertex.SmCircle.getShape();
			drawState(g2, x, y, shape, Color.GREEN);
			drawLabelState(g2, "GOOD", shape);
		}
		
	}

	private void drawLabelEdge(Graphics2D g2, int x, int y, int space, String transLabel) {
		g2.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		g2.drawString(transLabel, x + 15, y + space/2 + 10);
	}

	private void drawEdge(Graphics2D g2, int x, int y, int space) {
		g2.setPaint(Color.BLACK);
		g2.drawLine(x, y, x, y + space);
		Edge.drawArrow(g2, x, y, x, (y+y+space)/2 );
	}

	private void drawLabelState(Graphics2D g2, String label, RectangularShape shape) {
		//Draw label
		g2.setColor(Color.WHITE);
		g2.setFont(new Font(Font.MONOSPACED,Font.BOLD,14));
		int width = g2.getFontMetrics().stringWidth(label);
		g2.drawString(label, (int)(shape.getCenterX() - width/2) ,(int) (shape.getCenterY() + 5));
	}

	private void drawState(Graphics2D g2, int x, int y, RectangularShape shape, Color color) {
		//Draw states
		g2.setPaint(color);
		shape.setFrameFromCenter(x, y, x + shape.getHeight() / 2, y + shape.getWidth() / 2);
		g2.fill(shape);
	}

}
