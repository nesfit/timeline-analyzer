package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import java.util.HashSet;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import io.github.radkovo.rdf4j.builder.EntityFactory;
import io.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * A generic investigated object.
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Object>}
 */
public class Object extends io.github.radkovo.rdf4j.builder.RDFEntity
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Object");

	/** Inverse collection for Event.refersTo. */
	private Set<Event> events;


	public Object(IRI iri) {
		super(iri);
		events = new HashSet<>();
	}

	@Override
	public IRI getClassIRI() {
		return Object.CLASS_IRI;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void addEvent(Event event) {
		if (events == null) events = new HashSet<>();
		events.add(event);
		event.getRefersTo().add(this);
	}

	@Override
	public void addToModel(TargetModel target) {
		super.addToModel(target);
		target.addAll(events);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		if (!(efactory instanceof TAFactory))
			throw new IllegalArgumentException("factory must be instance of TAFactory");
		final TAFactory factory = (TAFactory) efactory;

		final Model m = model.filter(getIRI(), null, null);
	}
}
