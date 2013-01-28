/*
 * DigestDsResources.java
 *
 * Jan Rybak, 23/1/2013
 *
 * Part of DBpedia Spotlight tagger, plugin for GATE.
 *
 */

package gate.DBpedia.Spotlight;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;


/** 
 * Class mapping XML document returned from DBpedia Spotlight endpoint
 * to java beans DsAnnotation and DsResource.
 */
public class DigestDsResources {

	/** InputStream used to read file contents */
	private InputStream is;

	/** File with XML. */
	private File file;	
	
	/** Content of file with XML. */
	private String xmlString;
	
	public DigestDsResources(InputStream is) {
		this.is = is;
	}
	
	public DigestDsResources(File file) {
		this.file = file;
	}
	
	public DigestDsResources(String xmlString) {
		this.xmlString = xmlString.trim();
	}	

    public DsAnnotation digest() {
        Digester digester = new Digester();
        digester.addObjectCreate("Annotation", DsAnnotation.class);

        //Set the attribute values as properties
        digester.addSetProperties("Annotation");

        //A new Student instance for the student tag
        digester.addObjectCreate("Annotation/Resources/Resource", DsResource.class);

        //Set the attribute values as properties
        digester.addSetProperties("Annotation/Resources/Resource");

        //Next student
        digester.addSetNext("Annotation/Resources/Resource", "addResource");

        
        InputStream inputStream = null; 
        
        if (is != null) {
        	inputStream = is;
        	
        } else if (file != null) {
        	inputStream = this.getClass().getClassLoader()
        			.getResourceAsStream(file.getPath());
        	
        } else if (xmlString.length() > 0) {
        	inputStream = new ByteArrayInputStream(xmlString.getBytes());
        	
        } else {
        	// no filename or inputstream has been provided
        	return null;
        }
        
        //Parse the XML file to get an Annotation instance
        DsAnnotation a = null;
		try {
			a = (DsAnnotation) digester.parse(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		return a;
    }
}
