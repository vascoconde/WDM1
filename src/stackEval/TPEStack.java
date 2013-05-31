package stackEval;

import java.util.ArrayList;
import java.util.Stack;

public class TPEStack {
	PatternNode p;
	Stack <Match> matches;
	TPEStack spar;
 
	public TPEStack(PatternNode p, TPEStack parentStack){
		this.p = p;
		this.spar = parentStack;
		this.matches = new Stack<Match>();
	}
	
	public PatternNode getP() {
		return p;
	}
	
	public Stack<Match> getMatches() {
		return matches;
	}

	public ArrayList<TPEStack> getDescendantStacks()
	{
		ArrayList<TPEStack> temp = new ArrayList<TPEStack>(p.children) ;
		System.out.println(p.name+" childrens: "+p.children);
		TPEStack s = null;
		int counter = temp.size();
		System.out.println("Descendants de: "+this.p.name);
		for(int i=0; i<counter; i++) {
			s = temp.get(i);
			System.out.println(s.p.name+" childrens: "+s.p.children);
			temp.addAll(s.p.children);
			counter += s.p.children.size();
		}
		System.out.println();
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

	public TPEStack addChildren(String name) {
		return p.addChildren(name, this);
	}
} 