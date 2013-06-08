package stackEval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

public class TPEStack {
	public String name;
	public ArrayList<TPEStack> children;
	public ArrayList<TPEStack> attributes;
	Stack <Match> matches;
	TPEStack spar;
	private boolean isOptional;
	private boolean anyDescendancy;
	private boolean returnResult;
	private String predicateValue;
	private String elementText;
 
	public TPEStack(String name, TPEStack parentStack, boolean isOptional, boolean anyDescendancy){
		this.name = name;
		this.children = new ArrayList<TPEStack>();
		this.spar = parentStack;
		this.matches = new Stack<Match>();
		this.isOptional = isOptional;
		this.anyDescendancy = anyDescendancy;
		this.returnResult = false;
		predicateValue = null;
	}
	
	public ArrayList<TPEStack> getChildren() { return children; }
	public ArrayList<TPEStack> getAttributes() { return attributes; }
	public Stack<Match> getMatches() { return matches; }
	public boolean isOptional() { return isOptional; }
	public boolean getAnyDescendancy() { return anyDescendancy; }
	public boolean returnResult() { return returnResult; }
	public String getPredicateValue() { return predicateValue; }

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
	
	public Match firstOpen(){
		ArrayList<Match> a = new ArrayList<Match>(matches);
		Collections.reverse(a);
		for(Match m : a) {
			if(m.isOpen())
				return m;
		}
		return null; 
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

	public TPEStack addChildren(String name, boolean isOptional, boolean anyDescendancy) {
		TPEStack s = new TPEStack(name, this, isOptional, anyDescendancy);
		children.add(s);
		return s;
	}
	
	public TPEStack addAttribute(String name, boolean isOptional, boolean anyDescendancy) {
		TPEStack s = new TPEStack(name, this, isOptional, anyDescendancy);
		attributes.add(s);
		return s;
	}

	public void setPredicateValue(String string) {
		predicateValue = string;		
	}
	
	public void setReturnResult(boolean value) {
		returnResult = value;		
	}
	
	public void setElementText(String value) {
		elementText = value;
	}

	public String getText() {
		return elementText;
	}
	
} 