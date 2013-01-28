/*
 * DsResource.java
 *
 * Jan Rybak, 23/1/2013
 *
 * Part of DBpedia Spotlight tagger, plugin for GATE.
 *
 */

package gate.DBpedia.Spotlight;

/** 
 * Class representing DBpedia Spotlight Resource
 */
public class DsResource {
	
	/** The string of the DBpedia URI. */
	private String URI;
	
	/** Disambiguation parametr - integer that means the number of Wikipedia inlinks */
    private String support;
    
    /** List of DBpediaType objects. Each of these objects 
	 * holds a string with a type from the 
	 * [http://wiki.dbpedia.org/Ontology DBpedia Ontology]. */
    private String types;
    
    /** Surface Form object that hold as name the surface form string 
	 * (String in the text) */
    private String surfaceForm;
    
    /** Offset from the start of the text */
    private String offset;
    
    /** Similarity score - double that holds the similarity score 
	 * returned by Lucene */
    private String similarityScore;
    
    /** Percentage of second rank - double that holds 
	 * (sim_score_second_rank / sim_score_first_rank)  */
    private String percentageOfSecondRank;

    /**
	 * Constructor method.
	 */
    public DsResource() {
    }
    
    
	/** 
	 * Getters and setters 
	 * 
	 * */
    
    public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		URI = uRI;
	}

	public String getSupport() {
		return support;
	}

	public void setSupport(String support) {
		this.support = support;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}

	public String getSurfaceForm() {
		return surfaceForm;
	}

	public void setSurfaceForm(String surfaceForm) {
		this.surfaceForm = surfaceForm;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getSimilarityScore() {
		return similarityScore;
	}

	public void setSimilarityScore(String similarityScore) {
		this.similarityScore = similarityScore;
	}

	public String getPercentageOfSecondRank() {
		return percentageOfSecondRank;
	}

	public void setPercentageOfSecondRank(String percentageOfSecondRank) {
		this.percentageOfSecondRank = percentageOfSecondRank;
	}

	public Long start() {
		return new Long(offset);
	}

	public Long end() {
		return Long.parseLong(
				String.valueOf(
						(Integer.parseInt(offset) + getSurfaceForm().length())));
	}


	public String toString() {
        StringBuffer buf = new StringBuffer(60);

        buf.append("\nResource URI >> " + this.getURI());

        return buf.toString();
    }
}