package stackEval;

import java.util.ArrayList;

public class PatternNode {

	public String name;
	public ArrayList<TPEStack> children;

	public PatternNode(String name) {
		this.name = name;
		children = new ArrayList<TPEStack>();
	}
	
	public ArrayList<TPEStack> getChildren() { return children; }

	public TPEStack addChildren(String name, TPEStack parent) {
		TPEStack s = new TPEStack(new PatternNode(name), parent);
		children.add(s);
		return s;
	}
	
}
