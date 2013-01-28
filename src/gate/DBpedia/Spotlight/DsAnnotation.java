/*
 * DsAnnotation.java
 *
 * Jan Rybak, 23/1/2013
 *
 * Part of DBpedia Spotlight tagger, plugin for GATE.
 *
 */

package gate.DBpedia.Spotlight;

import java.util.Vector;

public class DsAnnotation {
    private Vector<DsResource> resources;
    private String text;
    private String confidence;
    private String support;
    private String types;
    private String sparql;
    private String policy;
    
    public DsAnnotation() {
        resources = new Vector<DsResource>();
    }

    public void addResource(DsResource dsResource) {
        resources.addElement(dsResource);
    }

    public Vector<DsResource> getResources() {
        return resources;
    }

    public void setResources(Vector<DsResource> resources) {
        this.resources = resources;
    }

    /*
     * Getters and Setters 
     * 
     */

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getConfidence() {
		return confidence;
	}

	public void setConfidence(String confidence) {
		this.confidence = confidence;
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

	public String toString() {
        StringBuffer buf = new StringBuffer(60);

        buf.append("Academy name>> " + this.getText());

        Vector<DsResource> stud = this.getResources();
        buf.append("\n\n**Resources**");

        //Iterate through vectors. Append content to StringBuffer.
        for (int i = 0; i < stud.size(); i++) {
            buf.append(stud.get(i));
        }

        return buf.toString();
    }
}
