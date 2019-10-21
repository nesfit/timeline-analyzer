package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import io.github.radkovo.rdf4j.builder.EntityFactory;
import io.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * Text contained in an entry..
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#TextContent>}
 */
public class TextContent extends Content
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#TextContent");

	/**
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#text>}
	 */
	private String text;


	public TextContent(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return TextContent.CLASS_IRI;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void addToModel(TargetModel target) {
		super.addToModel(target);
		addValue(target, TA.text, text);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		final Model m = model.filter(getIRI(), null, null);
		text = loadStringValue(m, TA.text);
	}
}
