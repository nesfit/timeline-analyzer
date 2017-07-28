/**
 * TwitterEntry.java
 *
 * Created on 28. 7. 2017, 13:27:04 by burgetr
 */
package cz.vutbr.fit.ta.twitter.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Entry;

/**
 * 
 * @author burgetr
 */
public class TwitterEntry extends Entry
{

    public TwitterEntry(IRI iri)
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
