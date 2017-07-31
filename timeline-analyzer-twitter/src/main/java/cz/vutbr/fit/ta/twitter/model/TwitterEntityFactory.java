/**
 * TwitterEntityFactory.java
 *
 * Created on 28. 7. 2017, 13:29:15 by burgetr
 */
package cz.vutbr.fit.ta.twitter.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.core.ResourceFactory;
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

}
