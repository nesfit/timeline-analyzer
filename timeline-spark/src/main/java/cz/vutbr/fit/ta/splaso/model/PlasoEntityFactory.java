/**
 * PlasoEntityFactory.java
 *
 * Created on 7. 8. 2019, 13:14:53 by burgetr
 */
package cz.vutbr.fit.ta.splaso.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.ontology.Content;
import cz.vutbr.fit.ta.ontology.CreationEvent;
import cz.vutbr.fit.ta.ontology.Entry;
import cz.vutbr.fit.ta.ontology.Event;
import cz.vutbr.fit.ta.ontology.FileDownloadEvent;
import cz.vutbr.fit.ta.ontology.GeoContent;
import cz.vutbr.fit.ta.ontology.Image;
import cz.vutbr.fit.ta.ontology.LocalFile;
import cz.vutbr.fit.ta.ontology.Object;
import cz.vutbr.fit.ta.ontology.SocialNetworkObject;
import cz.vutbr.fit.ta.ontology.TAFactory;
import cz.vutbr.fit.ta.ontology.TextContent;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.ontology.URLContent;
import cz.vutbr.fit.ta.ontology.URLVisitEvent;
import cz.vutbr.fit.ta.ontology.WebResource;

/**
 * 
 * @author burgetr
 */
public class PlasoEntityFactory implements TAFactory
{
    private static final PlasoEntityFactory instance = new PlasoEntityFactory();
    
    public static PlasoEntityFactory getInstance()
    {
        return instance;
    }

    @Override
    public GeoContent createGeoContent(IRI iri)
    {
        return null;
    }

    @Override
    public TextContent createTextContent(IRI iri)
    {
        return null;
    }

    @Override
    public Image createImage(IRI iri)
    {
        return null;
    }

    @Override
    public Entry createEntry(IRI iri)
    {
        return null;
    }

    @Override
    public LocalFile createLocalFile(IRI iri)
    {
        return null;
    }

    @Override
    public Timeline createTimeline(IRI iri)
    {
        return new PlasoTimeline(iri);
    }

    public Timeline createTimeline(String profileId)
    {
        return createTimeline(ResourceFactory.createResourceIRI("plaso", "timeline", profileId));
    }
    
    @Override
    public FileDownloadEvent createFileDownloadEvent(IRI iri)
    {
        return null;
    }

    @Override
    public SocialNetworkObject createSocialNetworkObject(IRI iri)
    {
        return null;
    }

    @Override
    public Content createContent(IRI iri)
    {
        return null;
    }

    @Override
    public WebResource createWebResource(IRI iri)
    {
        return new PlasoWebResource(iri);
    }

    @Override
    public URLVisitEvent createURLVisitEvent(IRI iri)
    {
        return new PlasoURLVisitEvent(iri);
    }

    @Override
    public CreationEvent createCreationEvent(IRI iri)
    {
        return null;
    }

    @Override
    public Object createObject(IRI iri)
    {
        return null;
    }

    @Override
    public URLContent createURLContent(IRI iri)
    {
        return new PlasoURLContent(iri);
    }

    @Override
    public Event createEvent(IRI iri)
    {
        return null;
    }

}
