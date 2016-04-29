package graph.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.MouseInputListener;

public class GraphComponent extends JComponent implements MouseInputListener, KeyListener, Serializable {

	protected static final long serialVersionUID = 1L;

	protected static int n = 0;
	protected List<Vertex> vertices = new ArrayList<Vertex>();
	Vertex currentVertex = null;
	protected List<Color> colors = new ArrayList<>();
	protected int dx = 0;
	protected int dy = 0;
	protected static final Color[] colorList = new Color[] {Color.GRAY };

	protected RectangularShape shapeSample = new Ellipse2D.Double(0, 0, 10, 10);

	protected List<Edge> edges = new ArrayList<Edge>();
	Edge currentEdge = null;
	protected RectangularShape currentJointPoint = null;

	public GraphComponent() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		for (Edge e : edges) {
			g.setColor(getForeground());
			e.draw(g2);
		}
		for (int i = 0; i < vertices.size(); i++) {
			Vertex v = vertices.get(i);
			g.setColor(colors.get(i));
			v.draw(g2);
		}
	}

	protected Vertex getVertex(int x, int y) {
		for (int i = vertices.size() - 1; i >= 0; i--) {
			Vertex v = vertices.get(i);
			if (v.contains(x, y)) {
				dx = (int) (x - v.getShape().getCenterX());
				dy = (int) (y - v.getShape().getCenterY());
				System.out.println(dx);
				System.out.println(dy);
				return v;
			}
		}
		return null;
	}

	protected RectangularShape getJointPoint(int x, int y) {
		for (Edge e : edges) {
			RectangularShape jp = e.getJointPoint(x, y);
			if (jp != null)
				return jp;
		}
		return null;
	}

	protected static final double EDGE_EPSILON = 2.0;

	protected Edge getEdge(int x, int y) {
		for (Edge e : edges)
			if (e.contains(x, y, EDGE_EPSILON))
				return e;
		return null;
	}

	public void setShapeType(RectangularShape sample) {
		shapeSample = sample;
	}

	protected void removeVertex(Vertex v) {
		List<Edge> toRemove = new ArrayList<Edge>();
		for (Edge e : edges) {
			if (e.v1 == v || e.v2 == v)
				toRemove.add(e);
		}
		for (Edge e : toRemove)
			removeEdge(e);
		vertices.remove(v);
	}

	protected void removeEdge(Edge e) {
		this.remove(e.textField);
		edges.remove(e);
	}

	protected Vertex createVertex(int x, int y) {
		RectangularShape rs = newShape(x, y);
		Vertex v = new Vertex(rs, Integer.toString(n++));
		vertices.add(v);
		return v;
	}

	protected void moveShape(RectangularShape rs, int x, int y) {
		rs.setFrameFromCenter(x, y, x + rs.getHeight() / 2, y + rs.getWidth() / 2);
	}

	protected RectangularShape newShape(int x, int y) {
		RectangularShape rs = (RectangularShape) shapeSample.clone();
		moveShape(rs, x, y);
		Random r = new Random();
		colors.add(colorList[r.nextInt(colorList.length)]);
		return rs;
	}

	protected Edge startEdge(Vertex v) {
		RectangularShape rs2 = newShape(0, 0);
		RectangularShape rs = v.shape;
		rs2.setFrameFromCenter((int) rs.getCenterX(), (int) rs.getCenterY(), (int) rs.getCenterX(),
				(int) rs.getCenterY());
		Edge e = new Edge(v, new Vertex(rs2, null));
		edges.add(e);
		return e;
	}

	protected void endEdge(Edge e, int x, int y) {
		Vertex v = getVertex(x, y);
		if (v == null) {
			e.v2.shape.setFrameFromCenter(x, y, x + shapeSample.getHeight() / 2, y + shapeSample.getWidth() / 2);
			e.v2.label = Integer.toString(n++);
			vertices.add(e.v2);
		} else
			e.v2 = v;
	}
	
	private EdgeLabel getLabel(int x, int y) {
		// TODO Auto-generated method stub
		for(Edge e : edges){
			EdgeLabel eL = e.textField;
			Rectangle r = new Rectangle(eL.getX()-3,eL.getY()-3,EdgeLabel.LabelBound.width, EdgeLabel.LabelBound.height);
			if(r.contains(x, y))
				return e.textField;
		}
		return null;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mouseClicked");
		int x = arg0.getX();
		int y = arg0.getY();
		if (arg0.getButton() == MouseEvent.BUTTON3) {
			Vertex v = getVertex(x, y);
			if (v != null) {
				removeVertex(v);
				repaint();
				return;
			}
			for (Edge edge : edges) {
				RectangularShape jp = edge.getJointPoint(x, y);
				if (jp != null) {
					edge.removeJointPoint(jp);
					repaint();
					return;
				}
			}
			Edge edge = getEdge(x, y);
			if (edge != null) {
				removeEdge(edge);
				repaint();
				return;
			}
		}
		
		if(arg0.getButton() == MouseEvent.BUTTON1){
			EdgeLabel label = getLabel(x,y);
			if (label != null) {
				String nlabel = JOptionPane.showInputDialog(this,
		                "Edge label: ", null);
				if (nlabel != null && nlabel.length() > 0) {
					label.setText(nlabel);
					label.validate();
					label.repaint();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mouseEntered");
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mouseExited");
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		requestFocusInWindow();
		if ((arg0.getModifiersEx() & InputEvent.BUTTON3_DOWN_MASK) == InputEvent.BUTTON3_DOWN_MASK)
			return;
		int x = arg0.getX();
		int y = arg0.getY();
		
		if(arg0.getButton() == MouseEvent.BUTTON1){
			EdgeLabel label = getLabel(x,y);
			if (label != null) {
				String nlabel = JOptionPane.showInputDialog(this,
		                "Edge label: ", null);
				if (nlabel != null && nlabel.length() > 0) {
					label.setText(nlabel);
					label.validate();
					label.repaint();
				}
				return;
			}
		}
		
		Vertex v = getVertex(x, y);
		if (v == null) {
			currentJointPoint = getJointPoint(x, y);
		}
		if (v == null && currentJointPoint == null)
			v = createVertex(x, y);
		if (v != null && arg0.isAltDown())
			currentEdge = startEdge(v);
		else
			currentVertex = v;
		repaint();
	}

	public void addEdgeLabel(final Edge e) {
		final EdgeLabel textField = new EdgeLabel();
		textField.setSize(50, 20);
		textField.setLocation(e.labelPosition());
		e.textField = textField;
		this.add(textField);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mouseReleased");
		if (currentEdge != null) {
			endEdge(currentEdge, arg0.getX(), arg0.getY());
			addEdgeLabel(currentEdge);
			currentEdge = null;
			repaint();
		}
		currentVertex = null;
		currentJointPoint = null;
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mouseDragged");
		if (currentVertex != null) {
			moveShape(currentVertex.getShape(), arg0.getX() - dx, arg0.getY() - dy);
			repaint();
		} else if (currentEdge != null) {
			moveShape(currentEdge.v2.getShape(), arg0.getX(), arg0.getY());
			repaint();
		} else if (currentJointPoint != null) {
			moveShape(currentJointPoint, arg0.getX(), arg0.getY());
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println("mouseMoved");
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == 32 && currentEdge != null) {
			currentEdge.addJointPoint();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	//Getter setter of Vertices and Edges
	public List<Vertex> getVertices() {
		return vertices;
	}

	public void setVertices(List<Vertex> vertices) {
		this.vertices = vertices;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public List<Color> getColors() {
		return colors;
	}

	public void setColors(List<Color> colors) {
		this.colors = colors;
	}
	
}
