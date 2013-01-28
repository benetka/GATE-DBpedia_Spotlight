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
	 *  @param 	documentText			String of the text to annotate.
	 *  @param 	confidence				Disambiguation parameter.
	 *  @param 	support					Integer denoting the number of Wikipedia inlinks.
	 *  @param 	types					Specification of RDF types such as DBpedia:Company.
	 *  @param 	sparql					SPARQL query to filter results.
	 *  @param 	policy					The policy can be to 'blacklist' or 'whitelist'.
	 *  @param 	coreferenceResolution	Coreference resolution.
	 *  @param 	disambiguator			Disambiguator (document,..).
	 *  
	 *  @return	string			Annotated text.
	 */
	public String query(
			String documentText, 
			double confidence, 
			int support,
    		String types, 
    		String sparql, 
    		String policy, 
    		Boolean coreferenceResolution, 
    		String disambiguator);
	
}
