package stackEval;

import java.util.ArrayList;

public class TreePattern {
	
	public TPEStack root;
	
	public TreePattern(String rootName, boolean anyDescendancy) {
		root = new TPEStack(rootName, null, false, anyDescendancy);
	}
	
	public TPEStack getRoot() { return root; }
	
	/*
	public TPEStack getTuple() {
		ArrayList<ArrayList<ArrayList<Integer>>> tuples = new ArrayList<ArrayList<ArrayList<Integer>>>();
		
		ArrayList<TPEStack> allTPEStacks = new ArrayList<TPEStack>();
		allTPEStacks.add(root);
		allTPEStacks.addAll(root.getDescendantStacks());
	
		//childrenMatches(tuples, root.children);
		
		while(root.matches.size() > 0){
			int i = 0;//Index of stack
			for(TPEStack stack : allTPEStacks) {
				ArrayList<ArrayList<Integer>> tuple = new ArrayList<ArrayList<Integer>>();
				tuples.add(tuple);
				ArrayList<Integer> element = new ArrayList<Integer>();
				element.add(root.matches.pop().currentPre);
				int nextPre = root.matches.peek().currentPre;
				tuple.add(element);
				for(TPEStack s : m.children.keySet()) {
					tuples.get(0).add(s.);
	
					tuples.add(new ArrayList<ArrayList<Integer>>());
					for(Match m2 : s.matches) {
						
						s.matches
						tuples.add(new ArrayList<ArrayList<Integer>>());
					}
	
				}
			} 
			i++;
		}
		return tuples;
	}*/
	
	/*
	public void childrenMatches(ArrayList<ArrayList<Integer>> tuples, ArrayList<TPEStack> listTPEStacks) {
		for(TPEStack m : listTPEStacks) {
			tuples.add(new ArrayList())
		}
		
	}
	*/

	
}
