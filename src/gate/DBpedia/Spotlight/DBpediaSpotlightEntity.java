/*
 *  DBpediaSpotlightEntity.java
 *
 *
 * Copyright (c) 2000-2001, The University of Sheffield.
 *
 * This file is part of GATE (see http://gate.ac.uk/), and is free
 * software, licenced under the GNU Library General Public License,
 * Version 2, June1991.
 *
 * A copy of this licence is included in the distribution in the file
 * licence.html, and is also available at http://gate.ac.uk/gate/licence.html.
 *
 *  jenda, 23/1/2013
 *
 *  $Id: DBpediaSpotlight.jav 9992 2008-10-31 16:53:29Z ian_roberts $
 *
 * For details on the configuration options, see the user guide:
 * http://gate.ac.uk/cgi-bin/userguide/sec:creole-model:config
 */

package gate.DBpedia.Spotlight;

import java.util.ArrayList;



/** 
 * Class representing DBpedia Spotlight Entity
 */
public class DBpediaSpotlightEntity {

	/** DBpedia URI */
	private String URI;
	  
	/** Disambiguation parametr - support */
	private double support;
	  
	/** Types in DBpedia */
	private ArrayList<String> types;
	  
	/** String in the text */
	private String surfaceForm;  
	  
	/** Offset from the start of the text */
	private int start;  
	
	/** End position of the string in the text */
	private int end;
	
	/** Similarity score */
	private double similarityScore;
	
	/** Percentage of second rank  */
	private double percentageOfSecondRank;
	
	/**
	 * Constructor method.
	 * @param uRI
	 * @param support
	 * @param types
	 * @param surfaceForm
	 * @param offset
	 * @param similarityScore
	 * @param percentageOfSecondRank
	 */
	public DBpediaSpotlightEntity(String uRI, double support,
			String surfaceForm, int offset, double similarityScore, 
			double percentageOfSecondRank) {
		super();
		URI = uRI;
		this.support = support;
		this.types = new ArrayList<String>();		// TODO: parse types from string representation 
		this.surfaceForm = surfaceForm;
		this.start = offset;
		this.end = offset + surfaceForm.length();
		this.similarityScore = similarityScore;
		this.percentageOfSecondRank = percentageOfSecondRank;
	}

	/** Getters and setters */
	
	public String getURI() {
		return URI;
	}
	
	public void setURI(String uRI) {
		URI = uRI;
	}
	
	public double getSupport() {
		return support;
	}
	
	public void setSupport(double support) {
		this.support = support;
	}
	
	public ArrayList<String> getTypes() {
		return types;
	}
	
	public void setTypes(ArrayList<String> types) {
		this.types = types;
	}
	
	public String getSurfaceForm() {
		return surfaceForm;
	}
	
	public void setSurfaceForm(String surfaceForm) {
		this.surfaceForm = surfaceForm;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int offset) {
		this.start = offset;
	}

	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}	
	
	public double getSimilarityScore() {
		return similarityScore;
	}
	
	public void setSimilarityScore(double similarityScore) {
		this.similarityScore = similarityScore;
	}
	
	public double getPercentageOfSecondRank() {
		return percentageOfSecondRank;
	}
	
	public void setPercentageOfSecondRank(double percentageOfSecondRank) {
		this.percentageOfSecondRank = percentageOfSecondRank;
	}  

  
} // class DBpediaSpotlight
