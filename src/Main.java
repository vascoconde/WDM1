
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import stackEval.Match;
import stackEval.StackEval;
import stackEval.TPEStack;
import stackEval.TreePattern;

public class Main {

	public static int level;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {

			// Now use the parser factory to create a SAXParser object
			XMLReader sp = XMLReaderFactory.createXMLReader();

			// Tree patter creation
			// ------------ root.addChildren(<name>, <optional>, <anyDescendancy>);

			//Query 1
			
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack email = root.addChildren("email", false, false);
			TPEStack name = root.addChildren("name", false, false);
			TPEStack last = name.addChildren("last", false, false);
			

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
			TPEStack att = any.addChildren("@*", false, true);
			*/

			//Query 2
			/*
			TreePattern tp = new TreePattern("person", true);
			TPEStack root = tp.root;
			TPEStack any = root.addChildren("*", false, true);
			TPEStack att = any.addChildren("@*", false, false);
			att.setPredicateValue("Jones");
			*/

			//Create an instance of this class; it defines all the handler methods
			StackEval handler = new StackEval(root);

			sp.setContentHandler(handler);
			sp.parse("data/people2Att.xml");

			printResults(root);

			/*
			level = 0;
			ArrayList<ArrayList<Integer>> info = recursiveResults(root,root.getMatches().size());

			System.out.println("Info size: "+info.size());


			String s = "";
			for(ArrayList<Integer> list : info) {
				for(Integer match : list) {
					s += match+", ";
				}
				s += "\n";
			}
			System.out.println("\n"+s);
			*/

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

	@SuppressWarnings("unchecked")
	public static ArrayList<ArrayList<Integer>> recursiveResults(TPEStack s, Integer nMatches) {

		level++;
		System.out.println("LEVEL "+level+" : Function called for stack "+s.name+" with nMatches="+nMatches);

		ArrayList<ArrayList<Integer>> info = new ArrayList<ArrayList<Integer>>();
		Match mChild = null;
		Integer childNumber;

		if(nMatches!=null && nMatches != 0) {
			
			Integer matchesToCopy = new Integer(nMatches);
			Stack<Match> listOfMatches = (Stack<Match>)s.getMatches().clone();
			
			while(nMatches > 0) {
				
				mChild = s.getMatches().firstElement();
				childNumber = mChild.getStart();
				System.out.println("LEVEL "+level+" : Analyzing mChild "+mChild.getStart());
				// Level stack search
				ArrayList<ArrayList<ArrayList<Integer>>> childsInfo = new ArrayList<ArrayList<ArrayList<Integer>>>(s.children.size());
				ArrayList<ArrayList<Integer>> temp;

				for(TPEStack child : s.children) {
					System.out.println("LEVEL "+level+" : NUMERO DE MATCHES PARA CHILD "+child.name+" é "+mChild.getChildren().get(child));
					temp = recursiveResults(child, mChild.getChildren().get(child));
					System.out.println("LEVEL "+level+" : TEMP: "+temp);
					childsInfo.add(temp);
				}

				System.out.println("LEVEL "+level+" : childsInfo: "+childsInfo);

				//Agregar arrays
				ArrayList<ArrayList<Integer>> agregate = new ArrayList<ArrayList<Integer>>();
				for(ArrayList<ArrayList<Integer>> listLevel : childsInfo) {
					for(ArrayList<Integer> listMatch : listLevel) {
						agregate = agregateArrays((ArrayList<Integer>)listMatch.clone(),childsInfo.subList(1, childsInfo.size()));
					}
				}
				
				System.out.println("LEVEL "+level+" : AGREGATE: "+agregate);
				System.out.println("LEVEL "+level+" : RESULT: "+info);
				int count = 0;
				System.out.println("LEVEL "+level+" : matchesToCopy: "+matchesToCopy);
				while(matchesToCopy > 0) {
					System.out.println("LEVEL "+level+" : matchesToCopy: "+matchesToCopy);
					Match m = listOfMatches.firstElement();
					ArrayList<Integer> matches = new ArrayList<Integer>();
					//int count = 0;
					for(ArrayList<Integer> list : agregate) {
						System.out.println("LEVEL "+level+" : list: "+list);
						list.add(0, m.getStart());
						System.out.println("LEVEL "+level+" : beforeRESULT: "+info+" with count="+count);
						info.add(list);
						//System.out.println("LEVEL "+level+" : afterRESULT: "+info+" with count="+count);
						count++;
					}
					if(count==0){
						System.out.println("LEVEL "+level+" : HERE WITH: "+count);
						matches.add(m.getStart());
						info.add(0,matches);
					}
					matchesToCopy--;
					listOfMatches.remove(0);

				}

				nMatches--;
				s.getMatches().remove(0);
			}
		} else {
			ArrayList<Integer> nullArray = new ArrayList<Integer>();
			nullArray.add(null);
			info.add(nullArray);
		}

		System.out.println("LEVEL "+level+" : RESULT: "+info);

		level--;
		return info;

	}

	private static ArrayList<ArrayList<Integer>> agregateArrays(ArrayList<Integer> listMatch,
			List<ArrayList<ArrayList<Integer>>> subList) {

		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> temp = new ArrayList<ArrayList<Integer>>();

		if(subList.isEmpty()) {
			result.add(listMatch);
		} else {
			for(ArrayList<ArrayList<Integer>> list : subList) {
				for(ArrayList<Integer> matches : list)
					temp = agregateArrays(matches, subList.subList(1, subList.size()));
				for(ArrayList<Integer> matchesList : temp) {
					matchesList.addAll(0, listMatch);
				}
				result.addAll(temp);
			}
		}
		return result;
	}

}
