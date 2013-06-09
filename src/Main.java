
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
			
			//TreePattern tp = new TreePattern("*", true);
			//TPEStack root = tp.root;
			
			// ------------ root.addChildren(<name>, <optional>, <anyDescendancy>);
			//TPEStack last = root.addChildren("last", false, true);
			//TPEStack name = root.addChildren("name", false, false);
			//TPEStack last = name.addChildren("last", false, false);
			//last.setPredicateValue("McCain");
			//TPEStack one = any2.addChildren("one", false, false);
			
			/* 
			//Query 1
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack email = root.addChildren("email", false, false);
			TPEStack name = root.addChildren("name", false, false);
			TPEStack last = name.addChildren("last", false, false);
			*/
			
			//Query 2
			/*
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack email = root.addChildren("email", true, false);
			TPEStack name = root.addChildren("name", false, false);
			TPEStack last = name.addChildren("last", false, false);
			*/
			
			//Query 3
			/*
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack any = root.addChildren("*", false, false);
			TPEStack last = any.addChildren("last", false, false);
			*/
			
			//Query 4
			/*
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack last = root.addChildren("last", false, true);
			*/
			
			//Query 5
			/*
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack name = root.addChildren("name", false, false);
			TPEStack any = name.addChildren("*", false, true);
			*/
			
			//Query 6
			/*
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack any1 = root.addChildren("*", false, false);
			TPEStack any2 = any1.addChildren("*", false, false);
			*/
			
			//Query 7
			/*
			TreePattern tp = new TreePattern("*", true);
			TPEStack root = tp.root;
			TPEStack any2 = root.addChildren("last", false, true);
			*/
			
			//Query 8
			/*
			TreePattern tp = new TreePattern("*", true);
			TPEStack root = tp.root;
			TPEStack any1 = root.addChildren("*", false, true);
			TPEStack any2 = any1.addChildren("last", false, false);
			*/
			
			//Attribute Queries
			
			//Query 1
			/*
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack any = root.addChildren("*", false, true);
			TPEStack att = any.addChildren("@*", false, false);
			*/
			
			//Query 2
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack any = root.addChildren("*", false, true);
			TPEStack att = any.addChildren("@*", false, false);
			att.setPredicateValue("J");
			
			//Create an instance of this class; it defines all the handler methods
			StackEval handler = new StackEval(root);
			
			sp.setContentHandler(handler);
			sp.parse("data/people2Att.xml");

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
