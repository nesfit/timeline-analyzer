package cz.vutbr.fit.ta.ontology;

import org.eclipse.rdf4j.model.IRI;
import com.github.radkovo.rdf4j.builder.EntityFactory;

public interface TAFactory extends EntityFactory{
	public Content createContent(IRI iri);
	public TextContent createTextContent(IRI iri);
	public Image createImage(IRI iri);
	public Entry createEntry(IRI iri);
	public Timeline createTimeline(IRI iri);
}
