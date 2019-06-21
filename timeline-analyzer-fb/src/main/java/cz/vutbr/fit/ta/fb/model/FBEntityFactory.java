/**
 * FBEntityFactory.java
 *
 * Created on 7. 3. 2018, 13:13:03 by burgetr
 */
package cz.vutbr.fit.ta.fb.model;

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
public class FBEntityFactory implements TAFactory
{
    private static final FBEntityFactory instance = new FBEntityFactory();
    
    public static FBEntityFactory getInstance()
    {
        return instance;
    }
    
    protected FBEntityFactory()
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
        return new FBTextContent(iri);
    }

    public TextContent createTextContent(String postId)
    {
        return createTextContent(ResourceFactory.createResourceIRI("fb", "entryText", postId));
    }
    
    @Override
    public Image createImage(IRI iri)
    {
        return new FBImage(iri);
    }
    
    public Image createImage(String imageId)
    {
        return createImage(ResourceFactory.createResourceIRI("fb", "image", imageId));
    }

    @Override
    public Entry createEntry(IRI iri)
    {
        return new FBEntry(iri);
    }

    public Entry createEntry(String postId)
    {
        return createEntry(ResourceFactory.createResourceIRI("fb", "entry", postId));
    }
    
    @Override
    public Timeline createTimeline(IRI iri)
    {
        return new FBTimeline(iri);
    }
    
    public Timeline createTimeline(String profileId)
    {
        return createTimeline(ResourceFactory.createResourceIRI("fb", "timeline", profileId));
    }

    @Override
    public GeoContent createGeoContent(IRI iri)
    {
        return new FBGeoContent(iri);
    }

    public GeoContent createGeoContent(String postId)
    {
        return new FBGeoContent(ResourceFactory.createResourceIRI("fb", "geo", postId));
    }

    @Override
    public URLContent createURLContent(IRI iri)
    {
        return new FBURLContent(iri);
    }

    public URLContent createURLContent(String postId, int index)
    {
        return new FBURLContent(ResourceFactory.createResourceIRI("fb", "url", postId + "-" + index));
    }

    @Override
    public SocialNetworkObject createSocialNetworkObject(IRI iri)
    {
        return null; //use subclasses
    }

    @Override
    public LocalFile createLocalFile(IRI iri)
    {
        return null; //not used by FB
    }

    @Override
    public Object createObject(IRI iri)
    {
        return null; //use subclasses
    }

    @Override
    public Event createEvent(IRI iri)
    {
        return null; //use subclasses
    }

    @Override
    public CreationEvent createCreationEvent(IRI iri)
    {
        return new FBCreationEvent(iri);
    }

    public CreationEvent createCreationEvent(String postId)
    {
        return createCreationEvent(ResourceFactory.createResourceIRI("fb", "created", postId));
    }

    @Override
    public FileDownloadEvent createFileDownloadEvent(IRI iri)
    {
        return null;
    }

    @Override
    public WebResource createWebResource(IRI iri)
    {
        return new FBWebResource(iri);
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
