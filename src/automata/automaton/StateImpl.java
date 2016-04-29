package automata.automaton;

public class StateImpl<T> implements State<T> {
	private boolean initial, terminal;
	private T vertex; 
	
	public StateImpl(boolean initial, boolean terminal, T vertex) {
		this.initial = initial;
		this.terminal = terminal;
		this.vertex = vertex;
	}

	@Override
	public boolean initial() {
		// TODO Auto-generated method stub
		return initial;
	}

	@Override
	public boolean terminal() {
		// TODO Auto-generated method stub
		return terminal;
	}

	@Override
	public T getVertex() {
		// TODO Auto-generated method stub
		return vertex;
	}

}
