package stackEval;

import java.util.ArrayList;
import java.util.Stack;

public class TPEStack {
	public String name;
	public ArrayList<TPEStack> children;
	Stack <Match> matches;
	TPEStack spar;
 
	public TPEStack(String name, TPEStack parentStack){
		this.name = name;
		children = new ArrayList<TPEStack>();
		this.spar = parentStack;
		this.matches = new Stack<Match>();
	}
	
	public ArrayList<TPEStack> getChildren() { return children; }


	public Stack<Match> getMatches() {
		return matches;
	}

	public ArrayList<TPEStack> getDescendantStacks()
	{
		ArrayList<TPEStack> temp = new ArrayList<TPEStack>(children) ;
		//System.out.println(name+" childrens: "+children);
		TPEStack s = null;
		int counter = temp.size();
		//System.out.println("Descendants de: "+this.name);
		for(int i=0; i<counter; i++) {
			s = temp.get(i);
			//System.out.println(s.name+" childrens: "+s.children);
			temp.addAll(s.children);
			counter += s.children.size();
		}
		//System.out.println();
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
		TPEStack s = new TPEStack(name, this);
		children.add(s);
		return s;
	}
	
} 