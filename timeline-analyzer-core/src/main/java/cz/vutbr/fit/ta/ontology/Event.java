package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import java.util.HashSet;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * An event with a subject and time assigned.
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Event>}
 */
public class Event extends com.github.radkovo.rdf4j.builder.RDFEntity
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Event");

	/**
	 * Assigns a source timeline to an event.
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#sourceTimeline>}
	 */
	private Timeline sourceTimeline;

	/**
	 * Assigns an object to an event.
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#refersTo>}
	 */
	private Set<Object> refersTo;

	/**
	 * A timestamp for an event.
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#timestamp>}
	 */
	private java.util.Date timestamp;


	public Event(IRI iri) {
		super(iri);
		refersTo = new HashSet<Object>();
	}

	@Override
	public IRI getClassIRI() {
		return Event.CLASS_IRI;
	}

	public Timeline getSourceTimeline() {
		return sourceTimeline;
	}

	public void setSourceTimeline(Timeline sourceTimeline) {
		this.sourceTimeline = sourceTimeline;
	}

	public Set<Object> getRefersTo() {
		return refersTo;
	}

	public java.util.Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(java.util.Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public void addToModel(Model model) {
		super.addToModel(model);
		addObject(model, TA.sourceTimeline, sourceTimeline);
		addCollection(model, TA.refersTo, refersTo);
		addValue(model, TA.timestamp, timestamp);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		if (!(efactory instanceof TAFactory))
			throw new IllegalArgumentException("factory must be instance of TAFactory");
		final TAFactory factory = (TAFactory) efactory;

		final Model m = model.filter(getIRI(), null, null);
		//load object sourceTimeline
		final Set<IRI> sourceTimelineIRIs = getObjectIRIs(m, TA.sourceTimeline);
		if (!sourceTimelineIRIs.isEmpty()) {
			final IRI iri = sourceTimelineIRIs.iterator().next();
			sourceTimeline = factory.createTimeline(iri);
			sourceTimeline.loadFromModel(m, factory);
		} else {
			sourceTimeline = null;
		}
		//load collection refersTo
		final Set<IRI> refersToIRIs = getObjectIRIs(m, TA.refersTo);
		refersTo = new HashSet<>();
		for (IRI iri : refersToIRIs) {
			Object item = factory.createObject(iri);
			item.loadFromModel(m, factory);
			refersTo.add(item);
		}
		timestamp = loadDateValue(m, TA.timestamp);
	}
}
