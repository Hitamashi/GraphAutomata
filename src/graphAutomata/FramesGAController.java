package graphAutomata;

import graph.editor.FramesController;
import graph.editor.GraphComponent;

public interface FramesGAController extends FramesController {
	//Save and load File
	public void save(GraphComponent gc);
	public GraphComponent load();
	public void checkWord(GraphComponent gc);
	
	//Build automata
}
