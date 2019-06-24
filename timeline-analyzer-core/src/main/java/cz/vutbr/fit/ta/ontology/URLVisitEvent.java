package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import com.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * visited URL.
 * <p>
 * An event of a URL visit.
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#URLVisitEvent>}
 */
public class URLVisitEvent extends Event
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#URLVisitEvent");


	public URLVisitEvent(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return URLVisitEvent.CLASS_IRI;
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
