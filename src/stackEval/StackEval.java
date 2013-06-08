package stackEval;

import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StackEval extends DefaultHandler {

	TreePattern q;
	// stack for the root of q
	public TPEStack rootStack;
	// pre number of the last element which has started:
	int currentPre = 1;
	// pre numbers for all elements having started but not ended yet:
	Stack <Integer> preOfOpenNodes;

	public void characters(char[] buffer, int start, int length) {
		////System.out.format("Characters:%s\n", new String(buffer, start, length));
	}

	public StackEval(TPEStack root){
		rootStack = root;
		preOfOpenNodes = new Stack<Integer>();
	}

	/*
	 * Every time the parser encounters the beginning of a new element,
	 * it calls this method, which resets the string buffer
	 * qName -> Element Name
	 */ 
	@Override
	public void startElement(String uri, String localName, String qName, 
			Attributes attributes) throws SAXException {

		//System.out.format("Start Element: %s\n", qName);

		if((qName.equals(rootStack.name) || rootStack.name.equals("*")) && ((preOfOpenNodes.size()==0) || rootStack.getAnyDescendancy())) {
			Match m = new Match(currentPre, null, null);
			System.out.println(qName+" Child of document root with anyDescendancy="+rootStack.getAnyDescendancy());
			rootStack.push(m);
		} //else {
			System.out.println("Analysing "+qName);
			Map<TPEStack, Integer> pChildren;
			for(TPEStack s : rootStack.getDescendantStacks()){
				////System.out.println("Analysing "+s.name+" stack for qName "+qName);
				if(!s.getAnyDescendancy() && isNotChildOfLastOpenElement(s)) {
					System.out.println("ignored "+s.name+" because dependency");
					continue;
				}
				System.out.println("Testing stack "+s.name);
				if((qName.equals(s.name) || (s.name.equals("*")) && isDescendantLastOpenElement(s,currentPre)) && s.spar.matches.size()!=0 && s.spar.top().isOpen()){
					System.out.println("##### MATCH FOUND in " + qName + " with stack "+s.name);

					Match mParent = null;
					for(Match m : s.spar.matches) {
						if(m.currentPre == preOfOpenNodes.peek()) {
							mParent = m;
						}
					}

					
					// create a match satisfying the ancestor conditions
					// of query node s.p
					Match m;
					if(mParent!=null)
						m = new Match(currentPre, mParent, s);
					else
						m = new Match(currentPre, s.spar.top(), s);

					s.push(m);

					if(mParent!=null)
						pChildren = mParent.children;
					else
						pChildren = s.spar.top().children;

					if(pChildren.containsKey(s)) {
						System.out.println("Added match in "+mParent.currentPre);
						pChildren.put(s, pChildren.get(s)+1);
					}
					else {
						System.out.println("Added match in "+mParent.currentPre);
						pChildren.put(s, 1);
					}

				}
			}
		//}

		for (int i = 0; i < attributes.getLength(); i++){
			String a = attributes.getQName(i);
			// similarly look for query nodes possibly matched
			// by the attributes of the currently started element
			for (TPEStack s : rootStack.getDescendantStacks()){
				if (a == s.name && s.getSpar().top().isOpen()){
					Match ma = new Match(currentPre, s.spar.top(), s);
					s.push(ma);
				}
			}
		}

		preOfOpenNodes.push(currentPre);
		currentPre++;

	}

	/*
	 * When the parser encounters the end of an element, it calls this method
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		// we need to find out if the element ending now corresponded
		// to matches in some stacks
		// first, get the pre number of the element that ends now:
		int preOflastOpen = preOfOpenNodes.pop();
		
		System.out.println("END ELEMENT: "+qName);
		System.out.println("preOflastOpen: "+preOflastOpen);

		// now look for Match objects having this pre number:
		if((qName.equals(rootStack.name) || rootStack.name.equals("*")) && ((preOfOpenNodes.size()==0) || rootStack.getAnyDescendancy())) {
			System.out.println("END ROOT IF size=0 "+qName);
			computeEndElementMatches(rootStack);
		} //else {
			for(TPEStack s : rootStack.getDescendantStacks()){
				if(!s.getAnyDescendancy() && isNotChildOfLastOpenElement(s)) {
					System.out.println("Skip to: "+s.name);
					continue;
				}
				System.out.println("testing --> "+s.name);
				//if((qName.equals(s.name) || (s.name.equals("*")) && isDescendantLastOpenElement2(s,preOflastOpen))) {
				if((qName.equals(s.name) || (s.name.equals("*")) && isDescendantLastOpenElement(s,preOflastOpen))) {
					System.out.println("Condition true for "+s.name);
					if(s.spar.matches.size()!=0 && s.spar.top().isOpen()){
						System.out.println("Going to compute end element matches in "+qName);
						computeEndElementMatches(s);
					}
				}
			}
		//}
	}

	private void computeEndElementMatches(TPEStack s) {
		
		// all descendants of this Match have been traversed by now.
		
		
		String text = "\n";
		for(Match mx : s.matches) {
			text += mx.currentPre+" is ";
			if(mx.isOpen()) {
				text += "open";
			} else {
				text += "closed";
			}
			text += "\n";
		}
		System.out.println(text);
		
		Match m = s.firstOpen();
		System.out.println("MATCH ANALYZED: "+m.currentPre+" that was on top of stack "+s.name);

		// check if m has child matches for all children
		// of its pattern node
		for (TPEStack sChild : s.getChildren()){
			// pChild is a child of the query node for which m was created
			System.out.println("testing sChild: "+sChild.name);
			System.out.println("m.child.get... "+m.children.get(sChild));
			if ((m.children.get(sChild) == null || m.children.get(sChild) == 0) && !sChild.isOptional()){
				System.out.println("TRUE");
				////System.out.println("REMOVE!");
				// m lacks a child Match for the pattern node pChild
				// we remove m from its Stack, detach it from its parent etc.
				remove(m, s);
			}
		}
		m.close();
	}

	private void remove(Match m, TPEStack s) {
		s.matches.remove(s.firstOpen());
		//Match removed = s.matches.pop();
		//System.out.println("ET VOILA: "+removed.currentPre);
		
		Map<TPEStack, Integer> pChildren;
		if(s.spar!=null && s.spar.matches.size()!=0) {
			pChildren = s.spar.firstOpen().children;
			if(pChildren.containsKey(s))
				pChildren.put(s, pChildren.get(s)-1);
		}
		for(TPEStack stack : m.children.keySet()) {
			for(int i = 0; i<m.children.get(stack); i++)
				remove(stack.matches.peek(), stack.matches.peek().stack);
		}
	}

	private boolean isNotChildOfLastOpenElement(TPEStack s) {
		boolean found = true;
		if(s.spar.matches.size()!=0 && preOfOpenNodes.size()!= 0) {
			System.out.println("s.spar.top().currentPre: "+s.spar.top().currentPre);
			System.out.println("preOfOpenNodes.peek(): "+preOfOpenNodes.peek());
			found = false;
			for(Match m : s.spar.matches) {
				//if(s.spar.top().currentPre != preOfOpenNodes.peek())
				if(m.currentPre == preOfOpenNodes.peek()) {
					found = true;
				}
			}
		}
		return !found;
	}

	private boolean isNotChildOfLastOpenElementEnd(TPEStack s, int i) {
		if(s.spar.matches.size()!=0 && preOfOpenNodes.size()!= 0) {
			if(s.spar.top().currentPre != i)
				return true;
		}
		return false;
	}

	private boolean isDescendantLastOpenElement(TPEStack s, int currentNodeID) {
		for(Match m : s.spar.matches) {
			if(preOfOpenNodes.size()!= 0 && m.currentPre == preOfOpenNodes.peek()) {
				return true;
			}
		}
		//boolean r = s.spar.matches.size()!=0 && preOfOpenNodes.size()!= 0  &&  s.spar.top().currentPre <= currentNodeID;
		//System.out.println("currentNodeID: "+currentNodeID+"\nresult: "+r);
		
		return false;
	}

	private boolean isDescendantLastOpenElement2(TPEStack s, int currentNodeID) {
		boolean r = s.matches.size()!=0 && preOfOpenNodes.size()!= 0  &&  currentNodeID >= s.spar.top().currentPre ;
		System.out.println("currentNodeID: "+currentNodeID+"\nresult: "+r);
		return r;
	}

}


