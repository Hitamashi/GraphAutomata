package graphAutomata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import graph.editor.GraphComponent;
import graph.editor.GraphFrame;

public class GraphAutomataFrame extends GraphFrame {
	private static final long serialVersionUID = 1L;
	public GraphAutomataFrame(FramesGAController controller) {
		//super(controller);
		this.controller = controller;

		component = new GraphAutomataComponent();
		component.setForeground(Color.BLACK);
		component.setBackground(Color.WHITE);
		component.setOpaque(true);
		component.setPreferredSize(new Dimension(1000, 1000));
		JScrollPane scrollPane = new JScrollPane(component);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu(GraphAutomataEditor.MENU_FILE);
		menuBar.add(menu);
		createMenuItem(menu, GraphAutomataEditor.MENU_ITEM_NEW, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphAutomataFrame.this.controller.createFrame();
			}
		});
		
		//Save automata
		//TODO JFileChooser to file path
		createMenuItem(menu, GraphAutomataEditor.MENU_ITEM_SAVE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				//Path to file
				((FramesGAController) GraphAutomataFrame.this.controller).save(component);
			}
		});
		
		//Load autotmata
		//TODO JFileChooser to filee path
		createMenuItem(menu, GraphAutomataEditor.MENU_ITEM_LOAD, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				//Path to file
				GraphComponent temp = ((FramesGAController) GraphAutomataFrame.this.controller).load();
				component.setVertices(temp.getVertices());
				component.setEdges(temp.getEdges());
				component.setColors(temp.getColors());
				repaint();
			}
		});
		
		createMenuItem(menu, GraphAutomataEditor.MENU_ITEM_CLOSE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphAutomataFrame.this.controller.deleteFrame(GraphAutomataFrame.this);
			}
		});
		createMenuSeparator(menu);
		createMenuItem(menu, GraphAutomataEditor.MENU_ITEM_CHECK, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				((FramesGAController) GraphAutomataFrame.this.controller).checkWord(component);
			}
		});
		createMenuSeparator(menu);
		createMenuItem(menu, GraphAutomataEditor.MENU_ITEM_QUIT, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphAutomataFrame.this.controller.quit();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GraphAutomataFrame.this.controller.deleteFrame(GraphAutomataFrame.this);
			}
		});

		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new GridLayout(1, 0));
		
		for (ShapeVertex sv : ShapeVertex.values()){
			addShapeButton(toolbar, sv, sv.getLabel());
		}
		JButton b = (JButton) toolbar.getComponentAtIndex(0);
		b.doClick();
		
		Container contentPane = getContentPane();
		contentPane.add(toolbar, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}
	
	protected JButton addShapeButton(JToolBar toolbar, ShapeVertex sample, String name) {
		// TODO Auto-generated method stub
		JButton button = new JButton(name);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((GraphAutomataComponent) component).setShapeType(sample);
				for (Component c : toolbar.getComponents()){
					if(c instanceof JButton)
						c.setEnabled(true);
				}
				button.setEnabled(false);
			}
		});
		button.setPreferredSize(new Dimension(button.getWidth(), 70));
		toolbar.add(button);
		return button;
	}
	
}
