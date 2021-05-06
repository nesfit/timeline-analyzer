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
import cz.vutbr.fit.ta.splaso.PlasoEntry;
import io.github.radkovo.rdf4j.builder.TargetModel;

/**
 * 
 * @author burgetr
 */
public class PlasoURLVisitEvent extends URLVisitEvent
{
    private PlasoEntry entry;

    public PlasoURLVisitEvent(IRI iri, PlasoEntry entry)
    {
        super(iri);
        this.entry = entry;
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

    @Override
    public void addToModel(TargetModel target)
    {
        super.addToModel(target);
        entry.addToModel(this, target);
    }

}
