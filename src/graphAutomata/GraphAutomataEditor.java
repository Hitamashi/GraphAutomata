package graphAutomata;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import graph.editor.GraphComponent;
import graph.editor.GraphEditor;
import graph.editor.GraphFrame;
import graphAutomata.FileUtil.FileUtils;

public class GraphAutomataEditor extends GraphEditor implements FramesGAController{
	//Add menu Save/Load file
	public final static String MENU_ITEM_SAVE = "Save";
	public final static String MENU_ITEM_LOAD = "Load";
	public final static String MENU_ITEM_CHECK = "Check Word";
	
	private FileUtils<GraphComponent> fileUtil = new FileUtils<GraphComponent>();
	
	@Override
	public void save(GraphComponent gc) {
		// TODO Something to save file into path
		try{
			fileUtil.generateExport(gc);
		}
		catch(IOException e){
		
		}
	}

	@Override
	public GraphComponent load() {
		// TODO Something to load file to 
		GraphComponent result = new GraphComponent();
		try {
			result = fileUtil.readExported();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public JFrame createFrame() {
		JFrame frame = new GraphAutomataFrame(this);
		frame.setTitle(TITLE);
		int pos = 30 * (frames.size() % 5);
		frame.setLocation(pos, pos);
		frame.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frames.add(frame);
		return frame;
	}

	@Override
	public void checkWord(GraphComponent gc) {
		// TODO Auto-generated method stub
		((GraphAutomataComponent) gc).checkString();
	}
}
