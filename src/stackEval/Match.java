package stackEval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Match {
	public enum State {OPEN, CLOSED};
	int start;
	State state;
	Match parent;
	Map<PatternNode, ArrayList<Match>> children;
	TPEStack stack;

	
	public Match(int currentPre, Match top, TPEStack s) {
		this.start = currentPre;
		this.stack = s;
		this.state = State.OPEN;
		this.parent = top;
		this.children = new HashMap<PatternNode, ArrayList<Match>>();
	}
	
	public int getStart() { return start;}
	public Match getParent() { return parent; }
	public TPEStack getTPEStack() { return stack; }
	public Map<PatternNode, ArrayList<Match>> getChildren() { return children; }

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