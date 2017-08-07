/**
 * SqliteReader.java
 *
 * Created on 7. 8. 2017, 11:21:11 by burgetr
 */
package cz.vutbr.fit.ta.local.sql;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 * A reader that reads arbitrary items from a sqlite database. 
 * 
 * @author burgetr
 */
public class SqliteReader<T>
{
    private static Logger log = LoggerFactory.getLogger(SqliteReader.class);

    public SqliteReader()
    {
    }
    
    /**
     * Reads all items from a database.
     * @param dbFile the database file
     * @param tableName the table name
     * @param itemFactory the factory used for creating a collection of items from a sqlite cursor
     * @return
     */
    public List<T> readItemsFromTable(File dbFile, String tableName, SqlItemFactory<T> itemFactory)
    {
        List<T> ret = new ArrayList<>();
        SqlJetDb db = null;
        try {
            db = SqlJetDb.open(dbFile, false);
        } catch (SqlJetException e) {
            log.error("Error opening sqlite db {} : {}", dbFile, e);
        }
        
        try {
            ISqlJetTable table = db.getTable(tableName);
            db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
            itemFactory.readItemsFromCursor(table.order(table.getPrimaryKeyIndexName()), ret);
        } catch (SqlJetException e) {
            log.error("Error reading from sqlite db {} : {}", dbFile, e);
        } finally {
            try {
                db.commit();
            } catch (SqlJetException e) {
                log.error("Error finishing transaction for db {} : {}", dbFile, e);
            }
        }

        try {
            db.close();
        } catch (SqlJetException e) {
            log.error("Error closing db {} : {}", dbFile, e);
        }
        
        return ret;
    }
    
}
