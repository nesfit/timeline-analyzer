/**
 * 
 */
package cz.vutbr.fit.ta.local.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.URLVisitEvent;

/**
 * @author burgetr
 *
 */
public class LocalURLVisitEvent extends URLVisitEvent
{

    public LocalURLVisitEvent(IRI iri)
    {
        super(iri);
    }

}
