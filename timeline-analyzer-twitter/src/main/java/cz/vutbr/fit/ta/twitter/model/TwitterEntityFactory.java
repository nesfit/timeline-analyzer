/**
 * TwitterEntityFactory.java
 *
 * Created on 28. 7. 2017, 13:29:15 by burgetr
 */
package cz.vutbr.fit.ta.twitter.model;

import org.eclipse.rdf4j.model.IRI;

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

    @Override
    public Content createContent(IRI iri)
    {
        return null;
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

    @Override
    public Timeline createTimeline(IRI iri)
    {
        return new TwitterTimeline(iri);
    }

}
