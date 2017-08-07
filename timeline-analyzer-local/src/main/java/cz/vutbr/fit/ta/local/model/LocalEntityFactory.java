/**
 * LocalEntityFactory.java
 *
 * Created on 7. 8. 2017, 14:04:57 by burgetr
 */
package cz.vutbr.fit.ta.local.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.local.HistoryItem;
import cz.vutbr.fit.ta.local.Profile;
import cz.vutbr.fit.ta.ontology.Content;
import cz.vutbr.fit.ta.ontology.Entry;
import cz.vutbr.fit.ta.ontology.GeoContent;
import cz.vutbr.fit.ta.ontology.Image;
import cz.vutbr.fit.ta.ontology.TAFactory;
import cz.vutbr.fit.ta.ontology.TextContent;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.ontology.URLContent;

/**
 * 
 * @author burgetr
 */
public class LocalEntityFactory implements TAFactory
{
    private static final LocalEntityFactory instance = new LocalEntityFactory();
    
    public static LocalEntityFactory getInstance()
    {
        return instance;
    }
    
    protected LocalEntityFactory()
    {
    }

    @Override
    public GeoContent createGeoContent(IRI iri)
    {
        return null;
    }

    @Override
    public Content createContent(IRI iri)
    {
        return null;
    }

    @Override
    public TextContent createTextContent(IRI iri)
    {
        return new LocalTextContent(iri);
    }

    public TextContent createTextContent(String id)
    {
        return new LocalTextContent(ResourceFactory.createResourceIRI("local", "text", id));
    }
    
    @Override
    public Image createImage(IRI iri)
    {
        return new LocalImage(iri);
    }

    @Override
    public Entry createEntry(IRI iri)
    {
        return new LocalEntry(iri);
    }

    public Entry createEntry(HistoryItem item)
    {
        return createEntry(ResourceFactory.createResourceIRI("local", "entry", item.getSourceId()));
    }
    
    @Override
    public URLContent createURLContent(IRI iri)
    {
        return new URLContent(iri);
    }
    
    public URLContent createURLContent(String id)
    {
        return createURLContent(ResourceFactory.createResourceIRI("local", "url", id));
    }

    @Override
    public Timeline createTimeline(IRI iri)
    {
        return new LocalTimeline(iri);
    }
    
    public Timeline createTimeline(Profile profile)
    {
        return createTimeline(ResourceFactory.createResourceIRI("local", "timeline", profile.getName()));
    }

}
