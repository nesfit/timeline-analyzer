/**
 * FirefoxProfile.java
 *
 * Created on 1. 2. 2017, 15:25:09 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;

import cz.vutbr.fit.ta.local.sql.SqlItemFactory;
import cz.vutbr.fit.ta.local.sql.SqliteReader;

/**
 * 
 * @author burgetr
 */
public class FirefoxProfile extends Profile
{
    private static Logger log = LoggerFactory.getLogger(FirefoxProfile.class);
    
    private static final String COOKIE_FILE = "cookies.sqlite";
    private static final String COOKIE_TABLE = "moz_cookies";
    private static final String PLACES_FILE = "places.sqlite";
    private static final String PLACES_TABLE = "moz_places";
    private static final String HISTORY_FILE = "places.sqlite";
    private static final String HISTORY_TABLE = "moz_historyvisits";
    private static final String DOWNLOADS_FILE = "places.sqlite";
    private static final String DOWNLOADS_TABLE = "moz_annos";
    private static final String ATTRIBUTES_TABLE = "moz_anno_attributes";
    private static final String DOWNLOAD_URI_ANNOTATION = "downloads/destinationFileURI";
    
    private List<Cookie> cookies;
    private List<Place> places;
    

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

    @Override
    public List<HistoryItem> getVisited(Date fromDate, Date toDate)
    {
        getPlaces();
        File dbFile = new File(getPath(), HISTORY_FILE);
        if (dbFile.exists())
        {
            SqliteReader<HistoryItem> reader = new SqliteReader<>();
            return reader.readItemsFromTable(dbFile, HISTORY_TABLE, new SqlItemFactory<HistoryItem>()
            {
                @Override
                public void readItemsFromCursor(ISqlJetCursor cursor, List<HistoryItem> dest) throws NumberFormatException, SqlJetException
                {
                    try {
                        if (!cursor.eof()) 
                        {
                            do 
                            {
                                //CREATE TABLE moz_historyvisits (id INTEGER PRIMARY KEY, from_visit INTEGER, place_id INTEGER, visit_date INTEGER, visit_type INTEGER, session INTEGER);
                                int id = (int) cursor.getInteger(0);
                                int placeId = (int) cursor.getInteger(2);
                                Date visited = new Date();
                                visited.setTime(cursor.getInteger(3) / 1000);
                                
                                if (visited.after(fromDate) && visited.before(toDate)) 
                                {
                                    Place place = getPlaces().get(placeId);
                                    if (place != null)
                                    {
                                        HistoryItem item = new HistoryItem(id, HistoryItem.Type.VISIT, visited, place.url);
                                        item.setTitle(place.title);
                                        item.setCount(place.count);
                                        dest.add(item);
                                    }
                                    else
                                        log.error("No place for id {}", placeId);
                                }
                                
                            } while (cursor.next());
                        }
                    } finally {
                        cursor.close();
                    }
                }
            });
        }
        else
            return null;
    }

    @Override
    public List<HistoryItem> getDownloaded(Date fromDate, Date toDate)
    {
        getPlaces();
        File dbFile = new File(getPath(), DOWNLOADS_FILE);
        if (dbFile.exists())
        {
            // Determine the 'downloads/destinationFileURI' annotation type
            SqliteReader<Integer> areader = new SqliteReader<>();
            List<Integer> annos = areader.readItemsFromTable(dbFile, ATTRIBUTES_TABLE, new SqlItemFactory<Integer>()
            {
                @Override
                public void readItemsFromCursor(ISqlJetCursor cursor, List<Integer> dest) throws NumberFormatException, SqlJetException
                {
                    try {
                        if (!cursor.eof()) 
                        {
                            do 
                            {
                                int id = (int) cursor.getInteger(0);
                                String name = cursor.getString(1);
                                if (DOWNLOAD_URI_ANNOTATION.equals(name))
                                    dest.add(id);
                            } while (cursor.next());
                        }
                    } finally {
                        cursor.close();
                    }
                }
            });
            int dnlattr = !annos.isEmpty() ? annos.get(0) : 8;
            if (annos.isEmpty())
                log.error("Couldn't determine {} annotation type, using guessed value of 8", DOWNLOAD_URI_ANNOTATION);
            else
                log.info("{} annotation is {}", DOWNLOAD_URI_ANNOTATION, dnlattr);
            // Find the download records
            SqliteReader<HistoryItem> reader = new SqliteReader<>();
            return reader.readItemsFromTable(dbFile, DOWNLOADS_TABLE, new SqlItemFactory<HistoryItem>()
            {
                @Override
                public void readItemsFromCursor(ISqlJetCursor cursor, List<HistoryItem> dest) throws NumberFormatException, SqlJetException
                {
                    try {
                        if (!cursor.eof()) 
                        {
                            do 
                            {
                                /*CREATE TABLE moz_annos (id INTEGER PRIMARY KEY,place_id INTEGER NOT NULL,anno_attribute_id INTEGER,
                                        mime_type VARCHAR(32) DEFAULT NULL,content LONGVARCHAR, flags INTEGER DEFAULT 0,
                                        expiration INTEGER DEFAULT 0,type INTEGER DEFAULT 0,dateAdded INTEGER DEFAULT 0,
                                        lastModified INTEGER DEFAULT 0);*/
                                
                                int id = (int) cursor.getInteger(0);
                                int placeId = (int) cursor.getInteger(1);
                                String content = cursor.getString(4);
                                int type = (int) cursor.getInteger(2);
                                Date visited = new Date();
                                visited.setTime(cursor.getInteger(8) / 1000);
                                System.out.println(type + " " + visited + " " + placeId + " " + content);
                                
                                if (visited.after(fromDate) && visited.before(toDate) && type == dnlattr) 
                                {
                                    Place place = getPlaces().get(placeId);
                                    HistoryItem curItem = new HistoryItem(id, HistoryItem.Type.DOWNLOAD, visited, place.url);
                                    curItem.setTitle(place.title);
                                    curItem.setFilePath(content);
                                    dest.add(curItem);
                                }
                                
                            } while (cursor.next());
                        }
                    } finally {
                        cursor.close();
                    }
                }
            });
        }
        else
            return null;
    }
    
    public List<Place> getPlaces()
    {
        if (places == null)
            places = readPlaces();
        return places;
    }
    
    //========================================================================================================
    
    //CREATE TABLE moz_cookies (id INTEGER PRIMARY KEY, baseDomain TEXT, originAttributes TEXT NOT NULL DEFAULT '', name TEXT, value TEXT, host TEXT, path TEXT, expiry INTEGER, 
    ///lastAccessed INTEGER, creationTime INTEGER, isSecure INTEGER, isHttpOnly INTEGER, appId INTEGER DEFAULT 0, inBrowserElement INTEGER DEFAULT 0, CONSTRAINT moz_uniqueid UNIQUE (name, host, path, originAttributes));
    
    private List<Cookie> readCookies()
    {
        File dbFile = new File(getPath(), COOKIE_FILE);
        if (dbFile.exists())
        {
            SqliteReader<Cookie> reader = new SqliteReader<>();
            return reader.readItemsFromTable(dbFile, COOKIE_TABLE, new SqlItemFactory<Cookie>()
            {
                @Override
                public void readItemsFromCursor(ISqlJetCursor cursor, List<Cookie> dest) throws NumberFormatException, SqlJetException
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
            });
        }
        else
            return null;
    }

    
    //========================================================================================================
    
    /*
    CREATE TABLE moz_places (id INTEGER PRIMARY KEY, url LONGVARCHAR, title LONGVARCHAR, rev_host LONGVARCHAR, visit_count INTEGER DEFAULT 0, 
            hidden INTEGER DEFAULT 0 NOT NULL, typed INTEGER DEFAULT 0 NOT NULL, favicon_id INTEGER, frecency INTEGER DEFAULT -1 NOT NULL, 
            last_visit_date INTEGER, guid TEXT, foreign_count INTEGER DEFAULT 0 NOT NULL, url_hash INTEGER DEFAULT 0 NOT NULL);
    */

    public List<Place> readPlaces()
    {
        File dbFile = new File(getPath(), PLACES_FILE);
        if (dbFile.exists())
        {
            SqliteReader<Place> reader = new SqliteReader<>();
            List<Place> places = reader.readItemsFromTable(dbFile, PLACES_TABLE, new SqlItemFactory<FirefoxProfile.Place>()
            {
                @Override
                public void readItemsFromCursor(ISqlJetCursor cursor, List<Place> dest) throws NumberFormatException, SqlJetException
                {
                    try {
                        if (!cursor.eof()) 
                        {
                            do 
                            {
                                Place place = new Place();
                                place.id = (int) cursor.getInteger(0);
                                try {
                                    place.url = new URL(cursor.getString(1));
                                } catch (MalformedURLException e) {
                                    place.url = null;
                                    log.error("Invalid url {}", cursor.getString(1));
                                }
                                place.title = cursor.getString(2);
                                place.count = (int) cursor.getInteger(4);
                                place.lastVisit = new Date();
                                place.lastVisit.setTime(cursor.getInteger(9) / 1000);
                                
                                while (dest.size() < place.id) //add blank places if some are missing in the sequence
                                    dest.add(null);
                                if (place.url != null)
                                    dest.add(place);
                                //System.out.println(place.id + " " + place.lastVisit + " " + place.title);
                                
                            } while (cursor.next());
                        }
                    } finally {
                        cursor.close();
                    }
                }
            });
            return places;
        }
        else
            return null;
    }
    
    class Place {
        public int id;
        public URL url;
        public String title;
        public int count;
        public Date lastVisit;
    }
    
}
