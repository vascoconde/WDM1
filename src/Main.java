
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.*;
import org.xml.sax.helpers.XMLReaderFactory;

import stackEval.StackEval;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			//Now use the parser factory to create a SAXParser object
			XMLReader sp = XMLReaderFactory.createXMLReader();

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
