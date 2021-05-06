/**
 * 
 */
package cz.vutbr.fit.ta.splaso.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Event;
import cz.vutbr.fit.ta.splaso.PlasoEntry;
import io.github.radkovo.rdf4j.builder.TargetModel;

/**
 * @author burgetr
 *
 */
public class PlasoGenericEvent extends Event
{
    private PlasoEntry entry;

    public PlasoGenericEvent(IRI iri, PlasoEntry entry)
    {
        super(iri);
        this.entry = entry;
    }

    @Override
    public void addToModel(TargetModel target)
    {
        super.addToModel(target);
        entry.addToModel(this, target);
    }

}
