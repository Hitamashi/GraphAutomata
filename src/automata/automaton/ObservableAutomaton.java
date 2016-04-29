package automata.automaton;

import java.util.Observable;
import java.util.Observer;

public class ObservableAutomaton<T> extends DeterministicAutomaton<T> {

	public ObservableAutomaton(Transition<T>[] transitions) throws NotDeterministTransitionException,
			UnknownInitialStateException, NotDeterministInitialStateException, UnknownTerminalStateException, NoTransitionException {
		super(transitions);
		// TODO Auto-generated constructor stub
	}

	private Observable observable = new Observable() {
		@Override
		public void notifyObservers(Object arg) {
			setChanged();
			super.notifyObservers(arg);
		}
	};

	@Override
	protected State<?> changeCurrentState(Transition<T> t) {
		observable.notifyObservers(t);
		return super.changeCurrentState(t);
	}

	public void addObserver(Observer o) {
		observable.addObserver(o);
	}
}
