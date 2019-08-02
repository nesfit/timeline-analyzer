/**
 * TAInterface.java
 *
 * Created on 2. 8. 2019, 12:07:28 by burgetr
 */
package cz.vutbr.fit.ta.spark;

import java.io.IOException;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

import cz.vutbr.fit.ta.halyard.RDFConnectorHalyard;

/**
 * The TA interface to be used from (py)spark
 * 
 * @author burgetr
 */
public class TAInterface
{
    private final static ValueFactory vf = SimpleValueFactory.getInstance();
    
    private String tableName;
    
    private RDFConnectorHalyard con;
    private Resource context;
    

    public TAInterface(String tableName, String contextIri) throws IOException
    {
        this.tableName = tableName;
        con = new RDFConnectorHalyard(tableName);
        context = vf.createIRI(contextIri);
    }

    public String getTableName()
    {
        return tableName;
    }
    
    public void close() throws IOException
    {
        con.close();
    }
    
    public void addObj(String subjectIri, String predicateIri, String objectIri) throws IOException
    {
        con.add(vf.createIRI(subjectIri), vf.createIRI(predicateIri), vf.createIRI(objectIri), context);
    }
    
    public void addString(String subjectIri, String predicateIri, String value) throws IOException
    {
        con.add(vf.createIRI(subjectIri), vf.createIRI(predicateIri), vf.createLiteral(value), context);
    }
    
    public void addInt(String subjectIri, String predicateIri, int value) throws IOException
    {
        con.add(vf.createIRI(subjectIri), vf.createIRI(predicateIri), vf.createLiteral(value), context);
    }
    
}
