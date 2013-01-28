/*
 * DsResource.java
 *
 * Jan Rybak, 23/1/2013
 *
 * Part of DBpedia Spotlight tagger, plugin for GATE.
 *
 */

package gate.DBpedia.Spotlight;

import gate.creole.ResourceInstantiationException;
import gate.util.GateRuntimeException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/** 
 * Requesting DBpedia Spotlight endpoint with GET method
 */
public class DsGETRequest implements IDsRequest {
	
	/** Encoding */
	private final String CHARSET = "UTF-8";
	
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

    
    /** DBpedia Spotlight endpoints URL, 
     * for example: http://spotlight.dbpedia.org/rest/annotate/ */
    private String dbpediaUrl;

    /**
	 * Constructor method.
     * @return 
	 */
    public DsGETRequest(String dbpediaUrl) {
    	this.dbpediaUrl = dbpediaUrl;
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
	  	
		// settings
		HttpURLConnection connection = null;
		URL requestURL;
	
		try {
			
			String url = dbpediaUrl.trim();
			
			if (url.endsWith("/")) {
				url = url.substring(0, url.length()-2);
			}
			
		    //Create connection
			url +=  "?text=" + URLEncoder.encode(documentText, CHARSET) +
			        "&confidence=" + URLEncoder.encode(Double.toString(confidence), CHARSET) +
			        "&support=" + URLEncoder.encode(Integer.toString(support), CHARSET);
	
			try {
				requestURL = new URL(url);
			} catch(MalformedURLException e) {
				throw new ResourceInstantiationException("Wrong URL format.");
			}
			
			/*
			//TEST:
			URL requestURL = new URL("http://spotlight.dbpedia.org/rest/annotate?text=President%20Michelle%20Obama%20called%20Thursday%20on%20Congress%20to%20extend%20a%20tax%20break%20for%20students%20included%20in%20last%20year%27s%20economic%20stimulus%20package,%20arguing%20that%20the%20policy%20provides%20more%20generous%20assistance.&confidence=0.2&support=20");
			*/
		    connection = (HttpURLConnection) requestURL.openConnection();
		    connection.addRequestProperty("Accept", ACCEPT);
		    connection.connect();
	
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