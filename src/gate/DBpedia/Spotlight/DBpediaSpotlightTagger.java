/*
 * DBpediaSpotlightTagger	.java
 *
 * Jan Rybak, 23/1/2013
 *
 * Part of DBpedia Spotlight tagger, plugin for GATE.
 *
 */


package gate.DBpedia.Spotlight;

import java.net.*;

import gate.*;
import gate.creole.*;
import gate.creole.metadata.*;
import gate.util.*;

/** 
 * <p>A named entity annotator using DBpedia Spotlight endpoint.
 *  DBpedia Spotlight is called using its REST interface, via a 
 *  http POST.</p> 
 *  
 *  <p>This GATE Processing resource was written as a part of
 *  Jan Rybak's Master's thesis at the University of West Bohemia, 
 *  Czech Rep. and Norwegian University of Science and Technology.</p>
 * 
 *  @version 0.1  
 *  @author jendarybak@gmail.com
 *  @link http://github.com/jendarybak/GATE-DBpedia_Spotlight
 */
@CreoleResource(name = "DBpedia Spotlight", comment = "DBpedia Spotlight Entity Tagger")
public class DBpediaSpotlightTagger extends AbstractLanguageAnalyser
  implements ProcessingResource {

  
  /**
   * First version of this plugin
   */
  private static final long serialVersionUID = 1L;

  /** Code  */
  private static final String OUTPUT_LABEL = "Spotlight";
  
  /** Default value  */
  private static final int DEFAULT_VALUE = -1;
	

  
  /** The name of the annotation set used for input */
  protected String outputASName;  

  /** Address of DBpedia web service */
  protected String dbpediaUrlString;

  /** Disambiguation parametr - confidence */
  protected String confidenceString;
  protected double confidence;

  /** Disambiguation parametr - integer that means the number of Wikipedia inlinks */
  protected String supportString;
  protected int support;

  /** Specification of RDF types such as DBpedia:Company. */
  protected String types;

  /** SPARQL query to filter results */
  protected String sparql;
  
  /** The policy can be to 'blacklist' or 'whitelist' */
  protected String policy;
  
  /** Coreference resolution */
  protected Boolean coreferenceResolution;
  
  /** Disambiguator */
  protected String disambiguator;
 

  /**
   * Initializes this resource
   * 
   * @return Resource
   * @throws ResourceInstantiationException
   */
  public Resource init() throws ResourceInstantiationException {
    
    // DBpedia Spotlight URL is mandatory
    if (dbpediaUrlString == null || dbpediaUrlString.trim().length() == 0) {
      throw new ResourceInstantiationException("DBpedia Spotlight URL is missing!");
    }
    
    // test format of DS url
    try {
      dbpediaUrlString = dbpediaUrlString.trim();
      
      if (!dbpediaUrlString.endsWith("/")) {
    	  dbpediaUrlString += "/";
      }
      
      new URL(dbpediaUrlString);
      
    } catch(MalformedURLException e) {
      throw new ResourceInstantiationException("Wrong URL format.");
    }
    
    // init confidence parameter
    
    if (confidenceString.trim().length() > 0) {
	    try {
	      confidence = Double.parseDouble(confidenceString);
	    }
	    catch(NumberFormatException e) {
	      throw new ResourceInstantiationException("Confidence parameter must be a number (double).");
	    }
    } else {
    	confidence = DEFAULT_VALUE;
    }

    // init support parameter
    if (confidenceString.trim().length() > 0) {
	    try {
	      support = Integer.parseInt(supportString);
	    }
	    catch(NumberFormatException e) {
	      throw new ResourceInstantiationException("Support parameter must be a number (integer).");
	    }
    } else {
    	support = DEFAULT_VALUE;
    }
    
    return this;
  }
  
  
  /**
   * Method is executed after the init() method has finished its
   * execution. <BR>
   * 
   * @throws ExecutionException
   */
  public void execute() throws ExecutionException {
    // init progress bar
    fireProgressChanged(0);

    // If no document provided to process throw an exception
    if (document == null) {
      fireProcessFinished();
      throw new GateRuntimeException("No document to process!");
    }

    // get the annotationSet name provided by the user, or otherwise use
    // the default method
    AnnotationSet outputAs = (outputASName == null || outputASName.length() == 0) 
    		? document.getAnnotations() : document.getAnnotations(outputASName);

    try {
      // document in plain text
      String documentText = document.getContent().toString();
      
      // connect to DBpedia Spotlight server and send a query 
      IDsRequest request = new DsPOSTRequest(dbpediaUrlString);
      String XMLResponse = request.query(documentText, confidence, support,
    		  types, sparql, policy, coreferenceResolution, disambiguator);
      
      if (XMLResponse == null) {
    	  fireProcessFinished();
    	  throw new GateRuntimeException("No result returned from DBpedia Spotlight!");
      }
      
      // parse DBpedia Spotlight answer
      DigestDsResources d = new DigestDsResources(XMLResponse);
      
      // load content of XML into DsAnnotation object
      DsAnnotation docAnnotation = d.digest();

      // iterate over all 'Resource' entities given by DBpedia Spotlight
      // and annotate current document
      for(DsResource r : docAnnotation.getResources()) {
          FeatureMap fm = gate.Factory.newFeatureMap();
          
          // specify features
          fm.put("URI", r.getURI());
          fm.put("Support", r.getSupport());
          fm.put("Types", r.getTypes());
          fm.put("SurfaceForm", r.getSurfaceForm());
          fm.put("SimilarityScore", r.getSimilarityScore());
          fm.put("PercentageOfSecondRank", r.getPercentageOfSecondRank());
          
          // add feature
          outputAs.add(r.start(), r.end(), OUTPUT_LABEL, fm);
      }
      
    } catch(InvalidOffsetException e) {
      throw new ExecutionException(e);
    }

    // process is done, nice!
    fireProcessFinished();
  }  
  
  
  /** 
   * Getters and setters
   * 
   **/
  
  public String getDbpediaUrlString() {
    return dbpediaUrlString;
  }

  public void setDbpediaUrlString(String dbpediaUrlString) {
    this.dbpediaUrlString = dbpediaUrlString;
  }
  
  public String getConfidenceString() {
    return confidenceString;
  }

  public void setConfidenceString(String confidenceString) {
    this.confidenceString = confidenceString;
  }
  
  public String getSupportString() {
    return supportString;
  }

  public void setSupportString(String supportString) {
    this.supportString = supportString;
  }
  
  /**
   * Returns the name of the AnnotationSet that has been provided to
   * create the AnnotationSet
   */
  public String getOutputASName() {
    return outputASName;
  }

  /**
   * Sets the AnnonationSet name, that is used to create the
   * AnnotationSet
   * 
   * @param outputAS
   */
  public void setOutputASName(String outputAS) {
    this.outputASName = outputAS.trim();
  }

  public double getConfidence() {
	  return confidence;
  }
	
  public void setConfidence(double confidence) {
	this.confidence = confidence;
  }
	
  public String getTypes() {
    return types;
  }
	
  public void setTypes(String types) {
    this.types = types;
  }
	
  public String getSparql() {
    return sparql;
  }
	
  public void setSparql(String sparql) {
    this.sparql = sparql;
  }
	
  public String getPolicy() {
    return policy;
  }
	
  public void setPolicy(String policy) {
    this.policy = policy;
  }
	
  public Boolean getCoreferenceResolution() {
	  return this.coreferenceResolution;
  }
  
  public void setCoreferenceResolution(Boolean coreferenceResolution) {
	  this.coreferenceResolution = coreferenceResolution;
  }  
	
  public String getDisambiguator() {
    return disambiguator;
  }
	
  public void setDisambiguator(String disambiguator) {
    this.disambiguator = disambiguator;
  }  
    
} // class DBpediaSpotlight
