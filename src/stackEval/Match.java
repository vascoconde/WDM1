package stackEval;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Match {
	public enum State {OPEN, CLOSED};
	int currentPre;
	State state;
	Match parent; /* Ancestor conditions */
	Map<PatternNode, Stack<Match>> children; /* Descendant Conditions */
	TPEStack stack;

	public Match(int currentPre, Match top, TPEStack s) {
		this.currentPre = currentPre;
		this.stack = s;
		this.state = State.OPEN;
		this.parent = top;
		this.children = new HashMap<PatternNode, Stack<Match>>();
	}
	
	public int getStart() { return currentPre;}
	public Match getParent() { return parent; }
	public TPEStack getTPEStack() { return stack; }
	public Map<PatternNode, Stack<Match>> getChildren() { return children; }

	public boolean isOpen() {
		if(state.equals(State.OPEN))
			return true;
		else
			return false;
	}
	
	public void close() {
		this.state = State.CLOSED;
	}
	
}