/**
 * PlasoTimeline.java
 *
 * Created on 7. 8. 2019, 13:19:09 by burgetr
 */
package cz.vutbr.fit.ta.splaso.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public class PlasoTimeline extends Timeline
{

    public PlasoTimeline(IRI iri)
    {
        super(iri);
    }

    @Override
    public String getLabel()
    {
        return "Plaso profile " + getSourceId();
    }
    
}
