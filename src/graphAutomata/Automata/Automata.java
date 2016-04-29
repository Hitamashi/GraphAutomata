package graphAutomata.Automata;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.text.IconView;

import automata.automaton.ObservableAutomaton;
import automata.automaton.State;
import automata.automaton.StateImpl;
import automata.automaton.Transition;
import automata.automaton.TransitionImpl;
import graph.editor.Edge;
import graph.editor.Vertex;
import graph.editor.VertexInit;
import graph.editor.VertexTerminal;
import graphAutomata.WordRecognizeFrame;

public class Automata {
	private List<State<Vertex>> states;
	
	private List<Transition<String>> transitions;
	
	private ObservableAutomaton<String> observable;

	protected List<String> transString;
	
	public Automata() {
		this.states = new ArrayList<>();
		this.transitions = new ArrayList<>();
		this.transString = new ArrayList<>();
	}
	
	public void addState(Vertex v)
	{
		State<Vertex> state = null;
		if (v instanceof VertexInit) {
			state = new StateImpl<Vertex>(true, false, v);
		} else if (v instanceof VertexTerminal) {
			state = new StateImpl<Vertex>(false, true, v);
		} else {
			state = new StateImpl<Vertex>(false, false, v);
		}
		states.add(state);
	}
	
	public void addTransition(Vertex stateFrom, Vertex stateTo, String transition) {
		State<Vertex> from = null, to = null;
		for (State<Vertex> state : states) {
			if (state.getVertex().getLabel() == stateFrom.getLabel()) {
				from = state;
				//if (to != null) break;
			}
			if (state.getVertex().getLabel() == stateTo.getLabel()) {
				to = state;
				//if (from != null) break;
			}
		}
		if (from != null && to != null) {
			transitions.add(new TransitionImpl<String>(from, to, transition));
		}				
	}
	
	public void transformToState(List<Vertex> vertices, List<Edge> edges)
	{
		this.transitions.clear();
		this.states.clear();
		
		for (Vertex v : vertices) {
			addState(v);
		}
		
		for (Vertex v : vertices) {
			findTransition(v, edges);
		}
	}
	
	public void findTransition(Vertex shape, List<Edge> edges) {
		/**
		 * Find Shape which current shape point to
		 */
		for (Edge edge : edges) {
			Vertex currentShape = edge.getDest();
			if(edge.getSource().getLabel() == shape.getLabel())
				addTransition(shape, currentShape, edge.getLabel());
		}
	}
	
	public boolean verifyAutomata() {
		try {
			observable = new ObservableAutomaton<String>(convertTransitionsListToArray(transitions));
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	private Transition<String>[] convertTransitionsListToArray(List<Transition<String>> list) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		Transition<String>[] array = new Transition[list.size()];
		for(int i = 0; i<list.size(); i++){
            array[i] = list.get(i);
        }
        return array;
	}
	
	public boolean recognizeAutomata(String word, List<Vertex> vertices, List<Edge> edges) {
		transString.clear();
		transformToState(vertices, edges);
		if (word.length() < 1) {
			verifyAutomata();
			return false;
		}
		if (this.verifyAutomata()) {
			observable.addObserver(new Observer() {
				public void update(Observable arg0, Object arg1) {
					//((Transition<String>) arg1).label().setColor(Color.green);
					@SuppressWarnings("unchecked")
					Transition<String> trans = ((Transition<String>) arg1);
					Vertex from = (Vertex) trans.source().getVertex();
					Vertex to = (Vertex) trans.target().getVertex();
					//GraphWithEditor.showPath(from, to, shapes, trans.label());
					//TODO Something to visualize
					if(transString.size() < 1){
						transString.add(from.getLabel());
						transString.add(to.getLabel());
					}
					else{
						transString.add(to.getLabel());
					}
				}
			});
			if (observable.recognize(arrayString(word))) {
				//TODO Announce good word
				WordRecognizeFrame.showWRF(word, transString, true);
				return true;
				
			} else {
				//TODO Announce bad word
				WordRecognizeFrame.showWRF(word, transString, false);
				return false;
			}
		}
		return false;
	}
	
	public String[] arrayString(String word) {
		char[] character = word.toCharArray();
		String[] string = new String[character.length];
		for (int i=0; i< character.length; i++) {
			string[i] = String.valueOf(character[i]);
		}
		return string;
	}
}
