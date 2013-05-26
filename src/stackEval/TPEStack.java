package stackEval;

import java.util.ArrayList;
import java.util.Stack;

class TPEStack {
	PatternNode p;
	Stack <Match> matches;
	TPEStack spar;
 
	public TPEStack(PatternNode p, TPEStack parentStack){
		this.p = p;
		this.spar = parentStack;
		this.matches = new Stack<Match>();
	}
	
	public ArrayList<TPEStack> getDescendantStacks()
	{
		ArrayList<TPEStack> temp = new ArrayList<TPEStack>();
		for(Match m : matches) {
			temp.add(m.getTPEStack());
		}
		return temp;

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

	public TPEStack getSpar() {
		return spar;
	}
} 