package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import java.util.HashSet;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import com.github.radkovo.rdf4j.builder.TargetModel;
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
	public void addToModel(TargetModel target) {
		super.addToModel(target);
		addCollection(target, TA.contains, contains);
		addValue(target, TA.sourceId, sourceId);
		addValue(target, TA.tags, tags);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		if (!(efactory instanceof TAFactory))
			throw new IllegalArgumentException("factory must be instance of TAFactory");
		final TAFactory factory = (TAFactory) efactory;

		final Model m = model.filter(getIRI(), null, null);
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
