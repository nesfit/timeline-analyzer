package cz.vutbr.fit.ta.halyard;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;

import com.msd.gin.halyard.common.HalyardTableUtils;

public class HalyardTest 
{
    
    private static final int DEFAULT_SPLIT_BITS = 3;

    
    public static void main( String[] args )
    {
        String tableName = "test";
        
        Configuration conf = HBaseConfiguration.create();
        try (HTable hTable = HalyardTableUtils.getTable(conf, tableName, false, DEFAULT_SPLIT_BITS)) {
            
            int number = 0;
            ResultScanner scanner = hTable.getScanner(new Scan());
            for (Result rs = scanner.next(); rs != null; rs = scanner.next()) {
                number++;
            }
            System.out.println("Count: " + number);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
