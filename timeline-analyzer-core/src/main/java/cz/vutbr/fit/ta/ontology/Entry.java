package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import java.util.HashSet;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * An entry in the timeline.
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Entry>}
 */
public class Entry extends SocialNetworkObject
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Entry");

	/**
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#sourceTimeline>}
	 */
	private Timeline sourceTimeline;

	/**
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#contains>}
	 */
	private Set<Content> contains;

	/**
	 * An identifier of the entry in the source timeline (e.g. Twitter id).
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#sourceId>}
	 */
	private String sourceId;

	/**
	 * Tags specifying the properties of the entry (e.g. 'download', 'visit',
	 * etc.).
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#tags>}
	 */
	private String tags;


	public Entry(IRI iri) {
		super(iri);
		contains = new HashSet<Content>();
	}

	@Override
	public IRI getClassIRI() {
		return Entry.CLASS_IRI;
	}

	public Timeline getSourceTimeline() {
		return sourceTimeline;
	}

	public void setSourceTimeline(Timeline sourceTimeline) {
		this.sourceTimeline = sourceTimeline;
	}

	public Set<Content> getContains() {
		return contains;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	@Override
	public void addToModel(Model model) {
		super.addToModel(model);
		addObject(model, TA.sourceTimeline, sourceTimeline);
		addCollectionWithData(model, TA.contains, contains);
		addValue(model, TA.sourceId, sourceId);
		addValue(model, TA.tags, tags);
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
		//load collection contains
		final Set<IRI> containsIRIs = getObjectIRIs(m, TA.contains);
		contains = new HashSet<>();
		for (IRI iri : containsIRIs) {
			Content item = factory.createContent(iri);
			item.loadFromModel(m, factory);
			contains.add(item);
		}
		sourceId = loadStringValue(m, TA.sourceId);
		tags = loadStringValue(m, TA.tags);
	}
}
