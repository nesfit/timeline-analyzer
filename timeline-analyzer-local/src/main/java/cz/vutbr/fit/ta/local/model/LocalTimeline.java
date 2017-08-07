/**
 * LocalTimeline.java
 *
 * Created on 7. 8. 2017, 14:07:09 by burgetr
 */
package cz.vutbr.fit.ta.local.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public class LocalTimeline extends Timeline
{

    public LocalTimeline(IRI iri)
    {
        super(iri);
    }

    @Override
    public String getLabel()
    {
        return "Local profile " + getSourceId();
    }
    
}
