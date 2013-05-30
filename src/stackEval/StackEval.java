package stackEval;

import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StackEval extends DefaultHandler {

	//TreePattern q;
	TPEStack rootStack; // stack for the root of q
	// pre number of the last element which has started:
	int currentPre = 0;
	// pre numbers for all elements having started but not ended yet:
	Stack <Integer> preOfOpenNodes;

	public void characters(char[] buffer, int start, int length) {
		//System.out.format("Characters:%s\n", new String(buffer, start, length));
	}

	public StackEval(){
		rootStack = new TPEStack(null,null);
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

		System.out.format("Start Element: %s\n", qName);

		for(TPEStack s : rootStack.getDescendantStacks()){
			if(qName == s.p.name && s.spar.top().isOpen()){
				Match m = new Match(currentPre, s.spar.top(), s);
				// create a match satisfying the ancestor conditions
				// of query node s.p
				s.push(m); 
				preOfOpenNodes.push(currentPre);
			}
			currentPre ++;
		}
		for (int i = 0; i < attributes.getLength(); i++){
			String a = attributes.getQName(i);
			System.out.format("Attribute: %s", a);
			// similarly look for query nodes possibly matched
			// by the attributes of the currently started element
			for (TPEStack s : rootStack.getDescendantStacks()){

				if (a == s.p.name && s.getSpar().top().isOpen()){
					Match ma = new Match(currentPre, s.spar.top(), s);
					s.push(ma);
				}

			}
			currentPre ++;
		}


	}

	/*
	 * When the parser encounters the end of an element, it calls this method
	 */
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		System.out.println("End Element");

		/*
  		// we need to find out if the element ending now corresponded
  		// to matches in some stacks
  		// first, get the pre number of the element that ends now:
  		//int preOflastOpen = preOfOpenNodes.pop();
  		// now look for Match objects having this pre number:
  		for(TPEStack s :  rootStack.getDescendantStacks()){
  			if (s.p.name == localName && s.top().status == open && s.top().pre == preOfLastOpen) {
  				// all descendants of this Match have been traversed by now.
  				Match m = s.pop();
  				// check if m has child matches for all children
  				// of its pattern node
  				for (PatternNode pChild : s.p.getChildren()){
  					// pChild is a child of the query node for which m was created
  					if (m.children.get(pChild) == null){
  						// m lacks a child Match for the pattern node pChild
  						// we remove m from its Stack, detach it from its parent etc.

  						//remove(m, s);
  					}
  				}
  				m.close();

  			}
  		}
		 */
	}
}


