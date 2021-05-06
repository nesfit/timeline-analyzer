/**
 * PlasoFileDownloadEvent.java
 *
 * Created on 4. 9. 2019, 14:48:35 by burgetr
 */
package cz.vutbr.fit.ta.splaso.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.FileDownloadEvent;
import cz.vutbr.fit.ta.ontology.LocalFile;
import cz.vutbr.fit.ta.ontology.Object;
import cz.vutbr.fit.ta.splaso.PlasoEntry;
import io.github.radkovo.rdf4j.builder.TargetModel;

/**
 * 
 * @author burgetr
 */
public class PlasoFileDownloadEvent extends FileDownloadEvent
{
    private PlasoEntry entry;

    public PlasoFileDownloadEvent(IRI iri, PlasoEntry entry)
    {
        super(iri);
        this.entry = entry;
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
    
    @Override
    public void addToModel(TargetModel target)
    {
        super.addToModel(target);
        entry.addToModel(this, target);
    }

}
