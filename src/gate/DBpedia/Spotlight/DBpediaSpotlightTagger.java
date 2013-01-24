/*
 *  DBpediaSpotlight.java
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

import java.net.*;
import java.util.ArrayList;

import gate.*;
import gate.creole.*;
import gate.creole.metadata.*;
import gate.util.*;


/** 
 * This class is the implementation of the resource DBPEDIA SPOTLIGHT.
 */
@CreoleResource(name = "DBpedia Spotlight", comment = "DBpedia Spotlight Entity Recognizer")
public class DBpediaSpotlightTagger extends AbstractLanguageAnalyser
  implements ProcessingResource {

  /** The name of the annotation set used for input */
  protected String outputASName;  

  /** Address of DBpedia web service */
  protected String dbpediaUrlString;
  
  /** URL address of DBpedia web service */
  protected URL dbpediaUrl;

  /** Disambiguation parametr - confidence */
  protected String confidenceString;
  protected double confidence;

  /** Disambiguation parametr - support */
  protected String supportString;
  protected double support;


  /**
   * Initializes this resource
   * 
   * @return Resource
   * @throws ResourceInstantiationException
   */
  public Resource init() throws ResourceInstantiationException {
    
    // DBpedia Spotlight URL is mandatory
    if(dbpediaUrlString == null) {
      throw new ResourceInstantiationException("DBpedia Spotlight URL is missing!");
    }
    
    // init dbpedia url and try if it can be connected
    try {
      dbpediaUrl = new URL(dbpediaUrlString);
    }
    catch(MalformedURLException e) {
      throw new ResourceInstantiationException("Wrong URL format.");
    }
    
    // init confidence parameter
    try {
      confidence = Double.parseDouble(confidenceString);
    }
    catch(NumberFormatException e) {
      throw new ResourceInstantiationException("Confidence parameter must be a number (double).");
    }

    // init support parameter
    try {
      support = Double.parseDouble(supportString);
    }
    catch(NumberFormatException e) {
      throw new ResourceInstantiationException("Support parameter must be a number (double).");
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
    AnnotationSet outputAs = (outputASName == null || outputASName.trim()
            .length() == 0) ? document.getAnnotations() : document
            .getAnnotations(outputASName);


    try {
      String docText = document.getContent().toString();
      
      //TODO: load entities (remote request)
      
      //testing
      ArrayList<DBpediaSpotlightEntity> entityList = new ArrayList<DBpediaSpotlightEntity>();
      DBpediaSpotlightEntity entity = new DBpediaSpotlightEntity(
    		  "http://dbpedia.org/resource/Michelle_Obama",
    		  321, 
    		  "Michelle Obama", 
    		  10, 
    		  0.1565164476633072, 
    		  -1.0);
      entityList.add(entity);
      //\end of testing
      
      for(DBpediaSpotlightEntity ent : entityList) {
          FeatureMap fm = gate.Factory.newFeatureMap();
          outputAs.add(new Long(ent.getStart()), new Long(ent.getEnd()), "NER", fm); // TODO: set customised name of output
        }
    }
    catch(InvalidOffsetException e) {
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
   * @param annotationSetName
   */
  public void setOutputASName(String outputAS) {
    this.outputASName = outputAS;
  }  
  
} // class DBpediaSpotlight
