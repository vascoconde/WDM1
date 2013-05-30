
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

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

			//Now use the parser factory to create a SAXParser object
			XMLReader sp = XMLReaderFactory.createXMLReader();

			TreePattern tp = new TreePattern("person");
			tp.root.getP().addChildren(new TPEStack(new PatternNode("email"), tp.root));
			
			
			//Create an instance of this class; it defines all the handler methods
			StackEval handler = new StackEval();

			sp.setContentHandler(handler);
			sp.parse("data/accounts.xml");

			//handler.readList();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
