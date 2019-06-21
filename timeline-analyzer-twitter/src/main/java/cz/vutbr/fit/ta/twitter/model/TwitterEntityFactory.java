/**
 * TwitterEntityFactory.java
 *
 * Created on 28. 7. 2017, 13:29:15 by burgetr
 */
package cz.vutbr.fit.ta.twitter.model;

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
public class TwitterEntityFactory implements TAFactory
{
    private static final TwitterEntityFactory instance = new TwitterEntityFactory();
    
    public static TwitterEntityFactory getInstance()
    {
        return instance;
    }
    
    protected TwitterEntityFactory()
    {
    }

    @Override
    public Content createContent(IRI iri)
    {
        return null; //content is never created, use sublcasses
    }

    @Override
    public TextContent createTextContent(IRI iri)
    {
        return new TwitterTextContent(iri);
    }

    public TextContent createTextContent(long statusId)
    {
        return createTextContent(ResourceFactory.createResourceIRI("twitter", "entryText", String.valueOf(statusId)));
    }
    
    @Override
    public Image createImage(IRI iri)
    {
        return new TwitterImage(iri);
    }
    
    public Image createImage(long imageId)
    {
        return createImage(ResourceFactory.createResourceIRI("twitter", "image", String.valueOf(imageId)));
    }

    @Override
    public Entry createEntry(IRI iri)
    {
        return new TwitterEntry(iri);
    }

    public Entry createEntry(long statusId)
    {
        return createEntry(ResourceFactory.createResourceIRI("twitter", "entry", String.valueOf(statusId)));
    }
    
    @Override
    public Timeline createTimeline(IRI iri)
    {
        return new TwitterTimeline(iri);
    }
    
    public Timeline createTimeline(String username)
    {
        return createTimeline(ResourceFactory.createResourceIRI("twitter", "timeline", username));
    }

    @Override
    public GeoContent createGeoContent(IRI iri)
    {
        return new TwitterGeoContent(iri);
    }

    public GeoContent createGeoContent(long statusId)
    {
        return new TwitterGeoContent(ResourceFactory.createResourceIRI("twitter", "geo", String.valueOf(statusId)));
    }

    @Override
    public URLContent createURLContent(IRI iri)
    {
        return new TwitterURLContent(iri);
    }

    public URLContent createURLContent(long statusId, int position)
    {
        return new TwitterURLContent(ResourceFactory.createResourceIRI("twitter", "url", statusId + "-" + position));
    }

    @Override
    public SocialNetworkObject createSocialNetworkObject(IRI iri)
    {
        return null; //abstract
    }

    @Override
    public LocalFile createLocalFile(IRI iri)
    {
        return null; //not used
    }

    @Override
    public CreationEvent createCreationEvent(IRI iri)
    {
        return new TwitterCreationEvent(iri);
    }

    public CreationEvent createCreationEvent(long statusId)
    {
        return createCreationEvent(ResourceFactory.createResourceIRI("twitter", "created", String.valueOf(statusId)));
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
        return null;
    }

    @Override
    public WebResource createWebResource(IRI iri)
    {
        return new TwitterWebResource(iri);
    }

    public WebResource createWebResource(String urlString)
    {
        return createWebResource(ResourceFactory.createUrlIRI(urlString));
    }
    
    @Override
    public URLVisitEvent createURLVisitEvent(IRI iri)
    {
        return null;
    }

}
