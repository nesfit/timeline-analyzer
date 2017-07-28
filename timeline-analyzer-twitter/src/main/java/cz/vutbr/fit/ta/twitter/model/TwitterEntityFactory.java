/**
 * TwitterEntityFactory.java
 *
 * Created on 28. 7. 2017, 13:29:15 by burgetr
 */
package cz.vutbr.fit.ta.twitter.model;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.ontology.Content;
import cz.vutbr.fit.ta.ontology.Entry;
import cz.vutbr.fit.ta.ontology.Image;
import cz.vutbr.fit.ta.ontology.TAFactory;
import cz.vutbr.fit.ta.ontology.TextContent;
import cz.vutbr.fit.ta.ontology.Timeline;

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

    @Override
    public Image createImage(IRI iri)
    {
        return new TwitterImage(iri);
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

}
