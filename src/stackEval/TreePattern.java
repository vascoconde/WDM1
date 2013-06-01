package stackEval;

public class TreePattern {
	
	public TPEStack root;
	
	public TreePattern(String rootName) {
		root = new TPEStack(rootName, null);
	}
	
	public TPEStack getRoot() { return root; }
	
	
	
}
