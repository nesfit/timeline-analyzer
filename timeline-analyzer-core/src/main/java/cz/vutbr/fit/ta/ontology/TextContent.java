package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;
/**
 * Text contained in an entry..
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#TextContent>}
 */
abstract public class TextContent extends Content
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#TextContent");

	/**
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#text>}
	 */
	private String text;


	public TextContent(IRI iri) {
		super(iri);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void addToModel(Model model) {
		addValue(model, TA.text, text);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		final Model m = model.filter(getIRI(), null, null);
		text = loadStringValue(m, TA.text);
	}
}
