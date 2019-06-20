package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import java.util.HashSet;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * A sequence of entries displayed in a signle time line..
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Timeline>}
 */
public class Timeline extends SocialNetworkObject
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Timeline");

	/**
	 * An identifier of the entry in the source timeline (e.g. Twitter id).
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#sourceId>}
	 */
	private String sourceId;

	/** Inverse collection for Entry.sourceTimeline. */
	private Set<Entry> entries;


	public Timeline(IRI iri) {
		super(iri);
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

	public Set<Entry> getEntries() {
		return (entries == null) ? new HashSet<>() : entries;
	}

	public void addEntry(Entry entry) {
		if (entries == null) entries = new HashSet<>();
		entries.add(entry);
		entry.setSourceTimeline(this);
	}

	@Override
	public void addToModel(Model model) {
		super.addToModel(model);
		addValue(model, TA.sourceId, sourceId);
		addCollectionData(model, entries);
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
