package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#SocialNetworkObject>}
 */
public class SocialNetworkObject extends Object
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#SocialNetworkObject");


	public SocialNetworkObject(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return SocialNetworkObject.CLASS_IRI;
	}

	@Override
	public void addToModel(Model model) {
		super.addToModel(model);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		final Model m = model.filter(getIRI(), null, null);
	}
}