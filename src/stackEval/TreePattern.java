package stackEval;

public class TreePattern {
	
	public TPEStack root;
	
	public TreePattern(String rootName, boolean anyDescendancy) {
		root = new TPEStack(rootName, null, false, anyDescendancy);
	}
	
	public TPEStack getRoot() { return root; }
	
}
