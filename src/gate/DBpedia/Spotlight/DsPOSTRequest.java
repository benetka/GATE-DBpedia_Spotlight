/*
 * DsResource.java
 *
 * Jan Rybak, 23/1/2013
 *
 * Part of DBpedia Spotlight tagger, plugin for GATE.
 *
 */

package gate.DBpedia.Spotlight;

import gate.util.GateRuntimeException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/** 
 * Requesting DBpedia Spotlight endpoint with GET method
 */
public class DsPOSTRequest implements IDsRequest {
	
	/** Encoding */
	private final String CHARSET = "UTF-8";
	
	/** This class uses POST method, GET method is used by DsGETRequest */
    private final String METHOD = "POST";
    
    /** This content type is necessary when using POST method. */
    private final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    
    /**
     * Primarily we parse XML documents.<br /> 
     * 
     * Other possible formats:
     * <ul>
     * <li>text/html</li>
     * <li>application/xhtml+xml</li>
     * <li>application/json</li>
     * </ul>
     *  
     * */
    private final String ACCEPT = "text/xml";
    
    /** Default language */
    private final String LANGUAGE = "en-US";
    
    /** DBpedia Spotlight endpoints URL, 
     * for example: http://spotlight.dbpedia.org/rest/annotate/ */
    private URL dbpediaUrl;

    /**
	 * Constructor method. 
	 */
    public DsPOSTRequest(String dbpediaUrlString) {
    	dbpediaUrlString = dbpediaUrlString.trim();
		
		if (!dbpediaUrlString.endsWith("/")) {
			dbpediaUrlString += "/";
		}
			
    	try {
			this.dbpediaUrl = new URL(dbpediaUrlString);
		
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    		throw new GateRuntimeException("Wrong URL format. \n");
    	}
    }
    
    /**
	 * Establishes connection with given endpoint, sends request
	 * using POST method and reads response.
	 * 
	 *  @param 	documentText	String of the text to annotate.
	 *  @param 	confidence		DBpedia Spotlight parameter.
	 *  @param 	support			DBpedia Spotlight parameter.
	 *  @return	string			Annotated text.
	 */
    public String query(String documentText, double confidence, int support) {
	  	
		HttpURLConnection connection = null;
	
		try {
		    
			//Establish connection
			String urlParameters =
					"disambiguator=" + URLEncoder.encode("Document", CHARSET) +
					"&confidence=" + URLEncoder.encode(Double.toString(confidence), CHARSET) +
			        "&support=" + URLEncoder.encode(Integer.toString(support), CHARSET) +
			        "&text=" + URLEncoder.encode(documentText, CHARSET);
	
	    	connection = (HttpURLConnection) dbpediaUrl.openConnection();
		    connection.setRequestMethod(METHOD);
		    connection.setRequestProperty("Content-Type", CONTENT_TYPE);
		    connection.setRequestProperty("Accept", ACCEPT);
		    connection.setRequestProperty("Content-Length", "" + 
		               Integer.toString(urlParameters.getBytes().length));
		    connection.setRequestProperty("Content-Language", LANGUAGE);  
					
		    connection.setUseCaches (false);
		    connection.setDoInput(true);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		                  connection.getOutputStream ());
		    wr.writeBytes (urlParameters);
		    wr.flush ();
		    wr.close ();

		    //Get Response	
		    InputStream is = connection.getInputStream();
		    
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    String line;
		    StringBuffer response = new StringBuffer(); 
		    
		    while((line = rd.readLine()) != null) {
		    	response.append(line);
		    	response.append('\r');
		    }
		    rd.close();
		    
		    // return response
		    return response.toString();
	
		} catch (Exception e) {
			e.printStackTrace();
			throw new GateRuntimeException("Error during communication with DBpedia Spotlight server!");
	
		} finally {
			if (connection != null) {
				connection.disconnect(); 
		    }
		}
    }
}