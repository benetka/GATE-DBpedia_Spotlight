/*
 * DigestResource.java
 *
 * Jan Rybak, 23/1/2013
 *
 * Part of DBpedia Spotlight tagger, plugin for GATE.
 *
 */

package gate.DBpedia.Spotlight;

public interface IDsRequest {
    /**
	 * Establishes connection with given endpoint, sends request
	 * and returns String response.
	 * 
	 *  @param 	documentText	String of the text to annotate.
	 *  @param 	confidence		DBpedia Spotlight parameter.
	 *  @param 	support			DBpedia Spotlight parameter.
	 *  @return	string			Annotated text.
	 */
	public String query(String documentText, double confidence, int support);
}
