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

		if(qName.equals(rootStack.name)) {
			Match m = new Match(currentPre, null, null);
			if((preOfOpenNodes.size()==1) || rootStack.getAnyDescendancy()) {
				System.out.println(qName+" Child of document root with anyDescendancy="+rootStack.getAnyDescendancy());
				rootStack.push(m);
			}
		} else {
			Map<TPEStack, Integer> pChildren;
			for(TPEStack s : rootStack.getDescendantStacks()){
				////System.out.println("Analysing "+s.name+" stack for qName "+qName);
				if(!s.getAnyDescendancy() && s.spar.matches.size()!=0 && preOfOpenNodes.size()!= 0  && s.spar.top().currentPre != preOfOpenNodes.peek()) {
					continue;
				}
				if(qName.equals(s.name) && s.spar.matches.size()!=0 && s.spar.top().isOpen()){
					
					//System.out.println("##### MATCH FOUND!!" + qName);
					Match m = new Match(currentPre, s.spar.top(), s);
					// create a match satisfying the ancestor conditions
					// of query node s.p
					s.push(m);
					pChildren = s.spar.top().children;
					if(pChildren.containsKey(s))
						pChildren.put(s, pChildren.get(s)+1);
					else
						pChildren.put(s, 1);
				}
			}
		}
		
		for (int i = 0; i < attributes.getLength(); i++){
			String a = attributes.getQName(i);
			//System.out.format("Attribute: %s\n", a);
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
		////System.out.println("End Element");

  		// we need to find out if the element ending now corresponded
  		// to matches in some stacks
  		// first, get the pre number of the element that ends now:
  		int preOflastOpen = preOfOpenNodes.pop();
  		// now look for Match objects having this pre number:
  		
  		//TODO Caso especial da root da TP
		if(qName.equals(rootStack.name)) {
			if((preOfOpenNodes.size()==1) || rootStack.getAnyDescendancy()) {
				computeEndElementMatches(rootStack);
			}
		} else {
	  		for(TPEStack s : rootStack.getDescendantStacks()){
	  			if(!s.getAnyDescendancy() && s.spar.matches.size()!=0 && preOfOpenNodes.size()!= 0 && s.spar.top().currentPre != preOfOpenNodes.peek()) {
					continue;
				}
	  			if (s.name.equals(qName) && s.spar.matches.size()!=0 && s.top().isOpen() && s.top().currentPre == preOflastOpen) {
	  				computeEndElementMatches(s);
	  			}
	  		}
		}
	}
		
	private void computeEndElementMatches(TPEStack s) {
		// all descendants of this Match have been traversed by now.
			Match m = s.top();
			// check if m has child matches for all children
			// of its pattern node
			for (TPEStack sChild : s.getChildren()){
				// pChild is a child of the query node for which m was created
				if (m.children.get(sChild) == null && !sChild.isOptional()){
					////System.out.println("REMOVE!");
					// m lacks a child Match for the pattern node pChild
					// we remove m from its Stack, detach it from its parent etc.
					remove(m, s);
				}
			}
			m.close();
	}

	private void remove(Match m, TPEStack s) {
		s.matches.pop();
		for(TPEStack stack : m.children.keySet()) {
			for(int i = 0; i<m.children.get(stack); i++)
				remove(stack.matches.peek(), stack.matches.peek().stack);
		}
	}
}


