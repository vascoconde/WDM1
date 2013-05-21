package stackEval;

import java.util.ArrayList;
import java.util.Map;

public class Match {

	int start;
	int state;
	Match parent;
	Map <PatternNode, ArrayList<Match>> children;
	TPEStack st;

	
	public Match(int currentPre, Match top, TPEStack s) {
		// TODO Auto-generated constructor stub
	}


	public int getStatus() {
		//todo
		return 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	public void close() {
		// TODO Auto-generated method stub
		
	}
	

}