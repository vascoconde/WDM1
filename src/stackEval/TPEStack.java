package stackEval;

import java.util.ArrayList;
import java.util.Stack;

class TPEStack {
	PatternNode p;
	Stack <Match> matches;
	TPEStack spar;
 
	public ArrayList<TPEStack> getDescendantStacks()
	{
		// gets the stacks for all descendants of p
		return null;

	}
	public void push(Match m){ 
		matches.push(m); 
	}
	
	public Match top(){ 
		return matches.peek(); 
	}
	
	public Match pop(){ 
		return matches.pop(); 
	}
} 