/**
 * PlasoURLVisitEvent.java
 *
 * Created on 7. 8. 2019, 13:16:50 by burgetr
 */
package cz.vutbr.fit.ta.splaso.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Object;
import cz.vutbr.fit.ta.ontology.URLVisitEvent;
import cz.vutbr.fit.ta.ontology.WebResource;

/**
 * 
 * @author burgetr
 */
public class PlasoURLVisitEvent extends URLVisitEvent
{

    public PlasoURLVisitEvent(IRI iri)
    {
        super(iri);
    }

    @Override
    public String getLabel()
    {
        String name = "???";
        for (Object obj : getRefersTo())
        {
            if (obj instanceof WebResource)
            {
                name = ((WebResource) obj).getResourceTitle();
                if (name == null || name.isEmpty())
                    name = ((WebResource) obj).getSourceUrl();
            }
        }
        return "Visited " + name; 
    }

}
