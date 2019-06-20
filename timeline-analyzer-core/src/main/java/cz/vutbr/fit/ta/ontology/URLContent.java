package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#URLContent>}
 */
public class URLContent extends Content
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#URLContent");

	/**
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#text>}
	 */
	private String text;

	/**
	 * Source URL of the media..
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#sourceUrl>}
	 */
	private String sourceUrl;


	public URLContent(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return URLContent.CLASS_IRI;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@Override
	public void addToModel(Model model) {
		super.addToModel(model);
		addValue(model, TA.text, text);
		addValue(model, TA.sourceUrl, sourceUrl);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		final Model m = model.filter(getIRI(), null, null);
		text = loadStringValue(m, TA.text);
		sourceUrl = loadStringValue(m, TA.sourceUrl);
	}
}
