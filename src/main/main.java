package main;
import java.util.ArrayList;
import java.util.List;

import graph.editor.GraphEditor;
import graphAutomata.GraphAutomataEditor;
import graphAutomata.WordRecognizeFrame;

public class main {
	public static void main(String[] args) {
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GraphAutomataEditor().createFrame();
			}
		});
		

		
		//WordRecognizeFrame f1 = new WordRecognizeFrame("aba", states, true);
		//f1.config();
	}

}
