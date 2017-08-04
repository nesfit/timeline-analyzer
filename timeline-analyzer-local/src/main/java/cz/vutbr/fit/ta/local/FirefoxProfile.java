/**
 * FirefoxProfile.java
 *
 * Created on 1. 2. 2017, 15:25:09 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 * 
 * @author burgetr
 */
public class FirefoxProfile extends Profile
{
    private static Logger log = LoggerFactory.getLogger(FirefoxProfile.class);
    
    private final String COOKIE_TABLE = "moz_cookies";
    
    private List<Cookie> cookies;
    

    public FirefoxProfile(String name, File path)
    {
        super(name, path);
        cookies = null;
    }

    @Override
    public List<Cookie> getCookies()
    {
        if (cookies == null)
            cookies = readCookies();
        return cookies;
    }

    //========================================================================================================
    
    private List<Cookie> readCookies()
    {
        File dbFile = new File(getPath(), "cookies.sqlite");
        if (dbFile.exists())
        {
            List<Cookie> ret = new ArrayList<>();
            SqlJetDb db = null;
            try {
                db = SqlJetDb.open(dbFile, false);
            } catch (SqlJetException e) {
                log.error("Error opening sqlite db {} : {}", dbFile, e);
            }
            
            try {
                ISqlJetTable table = db.getTable(COOKIE_TABLE);
                db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
                readCookiesFromCursor(table.order(table.getPrimaryKeyIndexName()), ret);
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
        else
            return null;
    }

    protected void readCookiesFromCursor(ISqlJetCursor cursor, List<Cookie> dest) throws NumberFormatException, SqlJetException 
    {
        try {
            if (!cursor.eof()) 
            {
                do 
                {
                    //CREATE TABLE moz_cookies (id INTEGER PRIMARY KEY, baseDomain TEXT, originAttributes TEXT NOT NULL DEFAULT '', name TEXT, value TEXT, host TEXT, path TEXT, expiry INTEGER, 
                    ///lastAccessed INTEGER, creationTime INTEGER, isSecure INTEGER, isHttpOnly INTEGER, appId INTEGER DEFAULT 0, inBrowserElement INTEGER DEFAULT 0, CONSTRAINT moz_uniqueid UNIQUE (name, host, path, originAttributes));
                    
                    Date exp = new Date();
                    exp.setTime(cursor.getInteger(7) * 1000);
                    Date last = new Date();
                    last.setTime(cursor.getInteger(8) / 1000);
                    Date created = new Date();
                    created.setTime(cursor.getInteger(9) / 1000);
                    Cookie cookie = new Cookie(
                            cursor.getString(1),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6), 
                            exp,
                            last, 
                            created, 
                            cursor.getBoolean(10), 
                            cursor.getBoolean(11));
                    
                    dest.add(cookie);
                    //System.out.println(cookie);
                    
                } while (cursor.next());
            }
        } finally {
            cursor.close();
        }
    }
    
}
