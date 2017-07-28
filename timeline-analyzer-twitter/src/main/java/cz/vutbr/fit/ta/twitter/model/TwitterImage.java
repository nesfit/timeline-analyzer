/**
 * TwitterImage.java
 *
 * Created on 28. 7. 2017, 13:28:22 by burgetr
 */
package cz.vutbr.fit.ta.twitter.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Image;

/**
 * 
 * @author burgetr
 */
public class TwitterImage extends Image
{

    public TwitterImage(IRI iri)
    {
        super(iri);
    }

    @Override
    public IRI createIRI()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
