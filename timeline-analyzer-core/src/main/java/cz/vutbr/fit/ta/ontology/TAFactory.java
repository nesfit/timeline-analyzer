package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import io.github.radkovo.rdf4j.builder.EntityFactory;

public interface TAFactory extends EntityFactory{
	public GeoContent createGeoContent(IRI iri);
	public TextContent createTextContent(IRI iri);
	public Image createImage(IRI iri);
	public Entry createEntry(IRI iri);
	public LocalFile createLocalFile(IRI iri);
	public Timeline createTimeline(IRI iri);
	public FileDownloadEvent createFileDownloadEvent(IRI iri);
	public SocialNetworkObject createSocialNetworkObject(IRI iri);
	public Content createContent(IRI iri);
	public WebResource createWebResource(IRI iri);
	public URLVisitEvent createURLVisitEvent(IRI iri);
	public CreationEvent createCreationEvent(IRI iri);
	public Object createObject(IRI iri);
	public URLContent createURLContent(IRI iri);
	public Event createEvent(IRI iri);
}
