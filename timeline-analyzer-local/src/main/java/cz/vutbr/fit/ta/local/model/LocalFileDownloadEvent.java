/**
 * 
 */
package cz.vutbr.fit.ta.local.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.FileDownloadEvent;
import cz.vutbr.fit.ta.ontology.LocalFile;
import cz.vutbr.fit.ta.ontology.Object;

/**
 * @author burgetr
 *
 */
public class LocalFileDownloadEvent extends FileDownloadEvent
{

    public LocalFileDownloadEvent(IRI iri)
    {
        super(iri);
    }

    @Override
    public String getLabel()
    {
        String name = "???";
        for (Object obj : getRefersTo())
        {
            if (obj instanceof LocalFile)
            {
                name = ((LocalFile) obj).getFileName();
                if (name.length() == 0)
                    name = ((LocalFile) obj).getPath();
            }
        }
        return "Downloaded file " + name; 
    }

}
