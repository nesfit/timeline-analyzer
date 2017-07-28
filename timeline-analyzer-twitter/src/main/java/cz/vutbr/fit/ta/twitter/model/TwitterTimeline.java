/**
 * TwitterTimeline.java
 *
 * Created on 28. 7. 2017, 13:26:02 by burgetr
 */
package cz.vutbr.fit.ta.twitter.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public class TwitterTimeline extends Timeline
{

    public TwitterTimeline(IRI iri)
    {
        super(iri);
    }

    @Override
    public IRI createIRI()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
