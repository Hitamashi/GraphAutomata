package graphAutomata;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import graph.editor.FramesController;
import graph.editor.GraphComponent;
import graph.editor.GraphEditor;
import graph.editor.GraphFrame;

public class WordRecognizeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public final static String TITLE = "Word Recognize Frame ";
	public final static Dimension FRAMESIZE = new Dimension(500,600);
	private static final String TEXT_ACCEPT = " is accepted!";
	private static final String TEXT_NOTACCEPT = " is not accepted!";
	
	private WordRecognizeComponent component;
	protected String word;
	
	public WordRecognizeFrame(String word, List<String> states, boolean isAccepted) {
		// TODO Auto-generated constructor stub
		
		component = new WordRecognizeComponent(states, word, isAccepted);
		this.word = word;
		
		JScrollPane scrollPane = new JScrollPane(component);
		JLabel status = new JLabel();
		status.setHorizontalAlignment(SwingConstants.CENTER);
		if(isAccepted) status.setText("String \"" + word + "\"" + TEXT_ACCEPT);
		else status.setText("String \"" + word + "\"" + TEXT_NOTACCEPT);
		
		JButton OkButton = new JButton("OK");
		OkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				CloseFrame();
			}
		});
		
		Container contentPane = getContentPane();
		contentPane.add(status, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		contentPane.add(OkButton, BorderLayout.SOUTH);
	}
	
	protected void CloseFrame() {
		// TODO Auto-generated method stub
		this.dispose();
	}

	public void config(){
		this.setTitle(TITLE + '"' + word + '"');
		this.setPreferredSize(FRAMESIZE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int posX = screen.width/2 - FRAMESIZE.width/2;
		int posY = screen.height/2 - FRAMESIZE.height/2;
		this.setLocation(posX, posY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		removeMinMaxClose(this);
		this.pack();
		component.repaint();
		component.setVisible(true);
		this.setVisible(true);
	}
	
	public static void showWRF( String word, List<String> states, boolean isAccepted){
		WordRecognizeFrame f = new WordRecognizeFrame(word, states, isAccepted);
		f.config();
	}
	
	public void removeMinMaxClose(Component comp)
	  {
	    if(comp instanceof AbstractButton)
	    {
	      comp.getParent().remove(comp);
	    }
	    if (comp instanceof Container)
	    {
	      Component[] comps = ((Container)comp).getComponents();
	      for(int x = 0, y = comps.length; x < y; x++)
	      {
	        removeMinMaxClose(comps[x]);
	      }
	    }
	  }
}
