/**
 * 
 */
package cz.vutbr.fit.ta.local.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Object;
import cz.vutbr.fit.ta.ontology.URLVisitEvent;
import cz.vutbr.fit.ta.ontology.WebResource;

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
