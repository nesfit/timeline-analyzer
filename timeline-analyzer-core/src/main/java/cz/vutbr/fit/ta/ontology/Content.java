package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import io.github.radkovo.rdf4j.builder.EntityFactory;
import io.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * A particular content of an entry..
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Content>}
 */
public class Content extends io.github.radkovo.rdf4j.builder.RDFEntity
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Content");


	public Content(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return Content.CLASS_IRI;
	}

	@Override
	public void addToModel(TargetModel target) {
		super.addToModel(target);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		final Model m = model.filter(getIRI(), null, null);
	}
}
