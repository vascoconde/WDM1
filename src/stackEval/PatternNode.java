package stackEval;

import java.util.ArrayList;

public class PatternNode {

	public String name;
	public ArrayList<TPEStack> children;

	public PatternNode(String name) {
		this.name = name;
		children = new ArrayList<TPEStack>();
	}
	
	public void addChildren(TPEStack node) {
		children.add(node);
	}
	
	public ArrayList<TPEStack> getChildren() {
		return children;
	}

}
