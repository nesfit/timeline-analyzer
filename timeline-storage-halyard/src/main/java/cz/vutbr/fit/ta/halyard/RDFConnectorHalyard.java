/**
 * RDFConnectorHalyard.java
 *
 * Created on 26. 7. 2019, 11:18:11 by burgetr
 */
package cz.vutbr.fit.ta.halyard;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
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
    private static final String HBASE_CONFIGURATION_ZOOKEEPER_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT = "hbase.zookeeper.property.clientPort";
    
    private static final int DEFAULT_SPLIT_BITS = 3;
    
    private String tableName;
    private Configuration hConf;
    private Connection hConn;
    private Table hTable;        
    

    /**
     * Connects HBase with the default connection parametres.
     * @param tableName the name of table to use for storing the data
     * @throws IOException
     */
    public RDFConnectorHalyard(String tableName) throws IOException
    {
        this.tableName = tableName;
        connectHbase();
    }

    /**
     * Connects HBase with the specified zookeeper parametres
     * @param hbaseZookeeperQuorum the zookeeper quorum (e.g. "server1,server2")
     * @param hbaseZookeeperClientPort the zookeeper port, e.g. 2181
     * @param tableName the name of table to use for storing the data
     * @throws IOException
     */
    public RDFConnectorHalyard(String hbaseZookeeperQuorum, int hbaseZookeeperClientPort, String tableName) throws IOException
    {
        this.tableName = tableName;
        connectHbase(hbaseZookeeperQuorum, hbaseZookeeperClientPort);
    }

    /**
     * Connects HBase using the hbase-site.xml config gile
     * @param configPath path to hbase-site.xml
     * @param tableName the name of table to use for storing the data
     * @throws IOException
     */
    public RDFConnectorHalyard(String configPath, String tableName) throws IOException
    {
        this.tableName = tableName;
        connectHbase(configPath);
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
        hConn = ConnectionFactory.createConnection(hConf);
        hTable = HalyardTableUtils.getTable(hConn, tableName, false, DEFAULT_SPLIT_BITS);        
    }
    
    private void connectHbase(String hbaseZookeeperQuorum, int hbaseZookeeperClientPort) throws IOException
    {
        hConf = HBaseConfiguration.create();
        hConf.set(HBASE_CONFIGURATION_ZOOKEEPER_QUORUM, hbaseZookeeperQuorum);
        hConf.setInt(HBASE_CONFIGURATION_ZOOKEEPER_CLIENTPORT, hbaseZookeeperClientPort);
        hConn = ConnectionFactory.createConnection(hConf);
        hTable = HalyardTableUtils.getTable(hConn, tableName, false, DEFAULT_SPLIT_BITS);        
    }
    
    private void connectHbase(String configFilePath) throws IOException
    {
        hConf = HBaseConfiguration.create();
        hConf.addResource(new Path(configFilePath));
        hConn = ConnectionFactory.createConnection(hConf);
        hTable = HalyardTableUtils.getTable(hConn, tableName, false, DEFAULT_SPLIT_BITS);        
    }
    
}
