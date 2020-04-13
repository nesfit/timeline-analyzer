package cz.vutbr.fit.ta.halyard;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

import com.msd.gin.halyard.common.HalyardTableUtils;

public class HalyardTest 
{
    
    private static final int DEFAULT_SPLIT_BITS = 3;

    
    public static int countRows()
    {
        String tableName = "test";
        
        Configuration conf = HBaseConfiguration.create();
        try (Connection conn = ConnectionFactory.createConnection(conf)) {
            Table hTable = HalyardTableUtils.getTable(conn, tableName, false, DEFAULT_SPLIT_BITS);
            int number = 0;
            ResultScanner scanner = hTable.getScanner(new Scan());
            for (Result rs = scanner.next(); rs != null; rs = scanner.next()) {
                number++;
            }
            return number;
            
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    public static void main( String[] args )
    {
        int count = countRows();
        System.out.println("Count: " + count);
    }
}
