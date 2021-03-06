package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import java.util.HashSet;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import io.github.radkovo.rdf4j.builder.EntityFactory;
import io.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * A sequence of events displayed in a signle time line..
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Timeline>}
 */
public class Timeline extends io.github.radkovo.rdf4j.builder.RDFEntity
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Timeline");

	/**
	 * An identifier of the entry in the source timeline (e.g. Twitter id).
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#sourceId>}
	 */
	private String sourceId;

	/** Inverse collection for Event.sourceTimeline. */
	private Set<Event> events;


	public Timeline(IRI iri) {
		super(iri);
		events = new HashSet<>();
	}

	@Override
	public IRI getClassIRI() {
		return Timeline.CLASS_IRI;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public void addEvent(Event event) {
		if (events == null) events = new HashSet<>();
		events.add(event);
		event.setSourceTimeline(this);
	}

	@Override
	public void addToModel(TargetModel target) {
		super.addToModel(target);
		addValue(target, TA.sourceId, sourceId);
		target.addAll(events);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		if (!(efactory instanceof TAFactory))
			throw new IllegalArgumentException("factory must be instance of TAFactory");
		final TAFactory factory = (TAFactory) efactory;

		final Model m = model.filter(getIRI(), null, null);
		sourceId = loadStringValue(m, TA.sourceId);
	}
}
