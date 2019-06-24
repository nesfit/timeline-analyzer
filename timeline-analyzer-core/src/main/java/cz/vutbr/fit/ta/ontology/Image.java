package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
import com.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * An image contained in an entry..
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#Image>}
 */
public class Image extends Content
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#Image");

	/**
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#linksResource>}
	 */
	private WebResource linksResource;


	public Image(IRI iri) {
		super(iri);
	}

	@Override
	public IRI getClassIRI() {
		return Image.CLASS_IRI;
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
		addObject(target, TA.linksResource, linksResource);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		if (!(efactory instanceof TAFactory))
			throw new IllegalArgumentException("factory must be instance of TAFactory");
		final TAFactory factory = (TAFactory) efactory;

		final Model m = model.filter(getIRI(), null, null);
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
