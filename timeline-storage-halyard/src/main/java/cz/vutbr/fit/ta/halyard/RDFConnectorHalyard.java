/**
 * RDFConnectorHalyard.java
 *
 * Created on 26. 7. 2019, 11:18:11 by burgetr
 */
package cz.vutbr.fit.ta.halyard;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;

import com.msd.gin.halyard.common.HalyardTableUtils;

import cz.vutbr.fit.ta.core.RDFConnector;

/**
 * A RDF connector for the Halyard storage.
 * 
 * @author burgetr
 */
public class RDFConnectorHalyard implements RDFConnector
{
    private static final int DEFAULT_SPLIT_BITS = 3;
    
    private String tableName;
    private Configuration hConf;
    private HTable hTable;        
    

    public RDFConnectorHalyard(String tableName) throws IOException
    {
        this.tableName = tableName;
        connectHbase();
    }

    public String getTableName()
    {
        return tableName;
    }

    @Override
    public void add(Resource s, IRI p, Value o) throws IOException
    {
        add(s, p, o, null);
    }


    @Override
    public void add(Resource s, IRI p, Value o, Resource context) throws IOException
    {
        final KeyValue[] kvs = HalyardTableUtils.toKeyValues(s, p, o, null, false, System.currentTimeMillis());
        for (KeyValue kv : kvs)
        {
            hTable.put(new Put(kv.getRowArray(), kv.getRowOffset(), kv.getRowLength(), kv.getTimestamp()).add(kv));
        }
    }


    @Override
    public void add(Model m) throws IOException
    {
        for (Statement st : m)
        {
            add(st.getSubject(), st.getPredicate(), st.getObject(), st.getContext());
        }
    }


    @Override
    public void add(Model m, Resource context) throws IOException
    {
        for (Statement st : m)
        {
            add(st.getSubject(), st.getPredicate(), st.getObject(), context);
        }
    }
    
    @Override
    public void close() throws IOException
    {
        hTable.close();
    }

    //===================================================================================
    
    private void connectHbase() throws IOException
    {
        hConf = HBaseConfiguration.create();
        hTable = HalyardTableUtils.getTable(hConf, tableName, false, DEFAULT_SPLIT_BITS);        
    }
    
}
