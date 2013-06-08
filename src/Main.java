
import java.util.ArrayList;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import stackEval.Match;
import stackEval.StackEval;
import stackEval.TPEStack;
import stackEval.TreePattern;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// Now use the parser factory to create a SAXParser object
			XMLReader sp = XMLReaderFactory.createXMLReader();

			// Tree patter creation
			
			TreePattern tp = new TreePattern("person", false);
			TPEStack root = tp.root;
			
			// ------------ root.addChildren(<name>, <optional>, <anyDescendancy>);
			//TPEStack any = root.addChildren("*", false, false);
			TPEStack any = root.addChildren("*", false, false);
			TPEStack any2 = any.addChildren("*", false, false);
			//TPEStack one = any2.addChildren("one", false, false);
			
			//Create an instance of this class; it defines all the handler methods
			StackEval handler = new StackEval(root);
			
			sp.setContentHandler(handler);
			sp.parse("data/people.xml");

			printResults(root);

			//handler.readList();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
public static void printResults(TPEStack root) {
		
		ArrayList<TPEStack> temp = new ArrayList<TPEStack>();
		temp.add(root);
		TPEStack s = null;
		String print = "\n----------------------------------\n Results: \n----------------------------------\n";
		int counter = temp.size();
		for(int i=0; i<counter; i++) {
			s = temp.get(i);
			print += String.format("%8s [", s.name);
			for(Match m : s.getMatches()) {
				print += String.format(" %s,", m.getStart());
			}
			print+="]\n";
			temp.addAll(s.children);
			counter += s.children.size();
		}
		System.out.println(print+"----------------------------------");
	}

}
