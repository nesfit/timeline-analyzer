/**
 * SqlItemFactory.java
 *
 * Created on 7. 8. 2017, 11:18:52 by burgetr
 */
package cz.vutbr.fit.ta.local.sql;

import java.util.List;

import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

/**
 * A factory that creates items from SQLite records.
 * 
 * @author burgetr
 */
public interface SqlItemFactory<T>
{
    
    public void readItemsFromCursor(ISqlJetCursor cursor, List<T> dest) throws NumberFormatException, SqlJetException;
    
}
