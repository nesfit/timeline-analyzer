package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import io.github.radkovo.rdf4j.builder.EntityFactory;
import io.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * downloaded.
 * <p>
 * An event of the file download from a URL.
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#FileDownloadEvent>}
 */
public class FileDownloadEvent extends Event
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#FileDownloadEvent");


	public FileDownloadEvent(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return FileDownloadEvent.CLASS_IRI;
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
