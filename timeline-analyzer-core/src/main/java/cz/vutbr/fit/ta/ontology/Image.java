package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;
/**
 * An image contained in an entry..
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Image>}
 */
abstract public class Image extends Content
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Image");


	public Image(IRI iri) {
		super(iri);
	}

	@Override
	public void addToModel(Model model) {
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		final Model m = model.filter(getIRI(), null, null);
	}
}
