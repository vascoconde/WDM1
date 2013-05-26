package stackEval;

import java.util.ArrayList;
import java.util.Map;

public class Match {
	public enum State {OPEN, CLOSED};
	int start;
	State state;// 1:Open or 0:Closed
	Match parent;
	Map <PatternNode, ArrayList<Match>> children;
	TPEStack stack;

	
	public Match(int currentPre, Match top, TPEStack s) {
		// TODO Auto-generated constructor stub
	}


	public State getStatus() {
		return state;
	}


	public void close() {
		// TODO Auto-generated method stub
		
	}


	public TPEStack getTPEStack() {
		return stack;
	}
	

}