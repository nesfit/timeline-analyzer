/**
 * FBTimeline.java
 *
 * Created on 7. 3. 2018, 13:08:35 by burgetr
 */
package cz.vutbr.fit.ta.fb.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public class FBTimeline extends Timeline
{

    public FBTimeline(IRI iri)
    {
        super(iri);
    }
    
    @Override
    public String getLabel()
    {
        return "FB:" + getSourceId();
    }

}
