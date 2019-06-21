package cz.vutbr.fit.ta.ontology;

import java.util.Set;
import java.util.HashSet;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import com.github.radkovo.rdf4j.builder.EntityFactory;
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

	/** Inverse collection for Image.linksResource. */
	private Set<Image> images;


	public WebResource(IRI iri) {
		super(iri);
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

	public Set<Image> getImages() {
		return (images == null) ? new HashSet<>() : images;
	}

	public void addImage(Image image) {
		if (images == null) images = new HashSet<>();
		images.add(image);
		image.setLinksResource(this);
	}

	@Override
	public void addToModel(Model model) {
		super.addToModel(model);
		addValue(model, TA.sourceUrl, sourceUrl);
		addCollectionData(model, images);
	}

	@Override
	public void loadFromModel(Model model, EntityFactory efactory) {
		super.loadFromModel(model, efactory);
		if (!(efactory instanceof TAFactory))
			throw new IllegalArgumentException("factory must be instance of TAFactory");
		final TAFactory factory = (TAFactory) efactory;

		final Model m = model.filter(getIRI(), null, null);
		sourceUrl = loadStringValue(m, TA.sourceUrl);
	}
}
