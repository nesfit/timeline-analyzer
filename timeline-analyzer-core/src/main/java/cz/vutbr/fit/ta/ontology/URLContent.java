package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import io.github.radkovo.rdf4j.builder.EntityFactory;
import io.github.radkovo.rdf4j.builder.TargetModel;
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
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#linksResource>}
	 */
	private WebResource linksResource;


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

	public WebResource getLinksResource() {
		return linksResource;
	}

	public void setLinksResource(WebResource linksResource) {
		this.linksResource = linksResource;
	}

	@Override
	public void addToModel(TargetModel target) {
		super.addToModel(target);
		addValue(target, TA.text, text);
		addObject(target, TA.linksResource, linksResource);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		if (!(efactory instanceof TAFactory))
			throw new IllegalArgumentException("factory must be instance of TAFactory");
		final TAFactory factory = (TAFactory) efactory;

		final Model m = model.filter(getIRI(), null, null);
		text = loadStringValue(m, TA.text);
		//load object linksResource
		final Set<IRI> linksResourceIRIs = getObjectIRIs(m, TA.linksResource);
		if (!linksResourceIRIs.isEmpty()) {
			final IRI iri = linksResourceIRIs.iterator().next();
			linksResource = factory.createWebResource(iri);
			linksResource.loadFromModel(m, factory);
		} else {
			linksResource = null;
		}
	}
}
