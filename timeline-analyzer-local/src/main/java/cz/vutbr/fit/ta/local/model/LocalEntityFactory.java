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
        return null;
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

    @Override
    public SocialNetworkObject createSocialNetworkObject(IRI iri)
    {
        return null; //abstract
    }

    @Override
    public LocalFile createLocalFile(IRI iri)
    {
        return new LocalLocalFile(iri);
    }
    
    public LocalFile createLocalFile(String taskId, String path)
    {
        return createLocalFile(ResourceFactory.createFileIRI(taskId, path));
    }

    @Override
    public CreationEvent createCreationEvent(IRI iri)
    {
        return null; //not used
    }

    @Override
    public Object createObject(IRI iri)
    {
        return null; //abstract
    }

    @Override
    public Event createEvent(IRI iri)
    {
        return null; //abstract
    }

    @Override
    public FileDownloadEvent createFileDownloadEvent(IRI iri)
    {
        return new LocalFileDownloadEvent(iri);
    }

    @Override
    public URLVisitEvent createURLVisitEvent(IRI iri)
    {
        return new LocalURLVisitEvent(iri);
    }

    @Override
    public WebResource createWebResource(IRI iri)
    {
        return new LocalWebResource(iri);
    }
    
    public WebResource createWebResource(String urlString)
    {
        return createWebResource(ResourceFactory.createUrlIRI(urlString));
    }
    
}
