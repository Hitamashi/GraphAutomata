package automata.automaton;

public class NoTransitionException extends Exception {
	public NoTransitionException() {
	}

	public String getMessage() {
		return "No transitions";
	}
}
