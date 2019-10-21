package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import java.util.HashSet;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import io.github.radkovo.rdf4j.builder.EntityFactory;
import io.github.radkovo.rdf4j.builder.TargetModel;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * A web resource with a URL.
 * <p>
 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#WebResource>}
 */
public class WebResource extends Object
{
	public static final IRI CLASS_IRI = vf.createIRI("http://nesfit.github.io/ontology/ta.owl#WebResource");

	/**
	 * Source URL of the media..
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#sourceUrl>}
	 */
	private String sourceUrl;

	/**
	 * A title of a web resource (web page title, if present).
	 * <p>
	 * IRI: {@code <http://nesfit.github.io/ontology/ta.owl#resourceTitle>}
	 */
	private String resourceTitle;

	/** Inverse collection for Image.linksResource. */
	private Set<Image> images;


	public WebResource(IRI iri) {
		super(iri);
		images = new HashSet<>();
	}

	@Override
	public IRI getClassIRI() {
		return WebResource.CLASS_IRI;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getResourceTitle() {
		return resourceTitle;
	}

	public void setResourceTitle(String resourceTitle) {
		this.resourceTitle = resourceTitle;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void addImage(Image image) {
		if (images == null) images = new HashSet<>();
		images.add(image);
		image.setLinksResource(this);
	}

	@Override
	public void addToModel(TargetModel target) {
		super.addToModel(target);
		addValue(target, TA.sourceUrl, sourceUrl);
		addValue(target, TA.resourceTitle, resourceTitle);
		target.addAll(images);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		if (!(efactory instanceof TAFactory))
			throw new IllegalArgumentException("factory must be instance of TAFactory");
		final TAFactory factory = (TAFactory) efactory;

		final Model m = model.filter(getIRI(), null, null);
		sourceUrl = loadStringValue(m, TA.sourceUrl);
		resourceTitle = loadStringValue(m, TA.resourceTitle);
	}
}
