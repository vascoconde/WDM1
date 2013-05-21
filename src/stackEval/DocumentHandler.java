package stackEval;

import java.util.ArrayList;

public interface DocumentHandler {

	void startElement(String localName, ArrayList<Attribute> attributes);

	void endElement(String localName);

}
