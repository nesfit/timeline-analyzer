/**
 * 
 */
package cz.vutbr.fit.ta.local.model;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.FileDownloadEvent;

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

}
