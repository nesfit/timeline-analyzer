package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;
/**
 * A sequence of entries displayed in a signle time line..
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Timeline>}
 */
abstract public class Timeline extends com.github.radkovo.rdf4j.builder.RDFEntity
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Timeline");

	/**
	 * An identifier of the entry in the source timeline (e.g. Twitter id).
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#sourceId>}
	 */
	private String sourceId;


	public Timeline(IRI iri) {
		super(iri);
	}

	public String getSourceId() {
		return sourceId;
	}

	@Override
	public void addToModel(Model model) {
		addValue(model, TA.sourceId, sourceId);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		final Model m = model.filter(getIRI(), null, null);
		sourceId = loadStringValue(m, TA.sourceId);
	}
}
