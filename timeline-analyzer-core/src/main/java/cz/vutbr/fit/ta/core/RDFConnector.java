/**
 * RDFConnector.java
 *
 * Created on 26. 7. 2019, 11:02:12 by burgetr
 */
package cz.vutbr.fit.ta.core;

import java.io.Closeable;
import java.io.IOException;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Value;

/**
 * The basic interface of each RDF connector
 * 
 * @author burgetr
 */
public interface RDFConnector extends Closeable
{

    /**
     * Adds single tripple to the repository.
     * @param s
     * @param p
     * @param o
     */
    public void add(Resource s, IRI p, Value o) throws IOException;

    /**
     * Adds single tripple to the repository.
     * @param s
     * @param p
     * @param o
     * @param context
     */
    public void add(Resource s, IRI p, Value o, Resource context) throws IOException; 

    /**
     * Adds the whole model to the repository.
     * @param m
     */
    public void add(Model m) throws IOException;
    
    /**
     * Adds the whole model to the repository.
     * @param m
     * @param context
     */
    public void add(Model m, Resource context) throws IOException;

}
