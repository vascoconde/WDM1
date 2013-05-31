
import java.util.Stack;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import stackEval.Match;
import stackEval.PatternNode;
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
			TreePattern tp = new TreePattern("person");
			TPEStack root = tp.root;
			TPEStack mail = root.addChildren("mail");
			TPEStack name = root.addChildren("name");
			TPEStack last = name.addChildren("last");
			
			//Create an instance of this class; it defines all the handler methods
			StackEval handler = new StackEval(root);
			
			sp.setContentHandler(handler);
			sp.parse("data/people.xml");

			printResults("Person",root.getMatches());
			printResults("Name",name.getMatches());
			printResults("Email",mail.getMatches());
			printResults("Last",last.getMatches());

			//handler.readList();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void printResults(String name, Stack<Match> s) {
		String print = "";
		print += name+" [";
		for(Match m : s) {
			print += String.format(" %s,", m.getStart());
		}
		print+="]";
		System.out.println(print);
	}

}
