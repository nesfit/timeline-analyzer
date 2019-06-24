/**
 * 
 */
package cz.vutbr.fit.ta.twitter.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.Content;
import cz.vutbr.fit.ta.ontology.CreationEvent;
import cz.vutbr.fit.ta.ontology.Entry;
import cz.vutbr.fit.ta.ontology.GeoContent;
import cz.vutbr.fit.ta.ontology.Image;
import cz.vutbr.fit.ta.ontology.Object;
import cz.vutbr.fit.ta.ontology.TextContent;
import cz.vutbr.fit.ta.ontology.URLContent;

/**
 * @author burgetr
 *
 */
public class TwitterCreationEvent extends CreationEvent
{

    public TwitterCreationEvent(IRI iri)
    {
        super(iri);
    }

    @Override
    public String getLabel()
    {
        String name = "???";
        for (Object obj : getRefersTo())
        {
            if (obj instanceof Entry)
            {
                final Entry entry = (Entry) obj;
                name = "";
                for (Content c : entry.getContains())
                {
                    String cstr = "?";
                    if (c instanceof TextContent)
                        cstr = "text";
                    else if (c instanceof URLContent)
                        cstr = "URL";
                    else if (c instanceof Image)
                        cstr = "image";
                    else if (c instanceof GeoContent)
                        cstr = "GEO";
                    if (!name.isEmpty())
                        name += ", ";
                    name += cstr;
                }
            }
        }
        return "Posted " + name; 
    }
    
}
