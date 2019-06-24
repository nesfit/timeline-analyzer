/**
 * HistoryItem.java
 *
 * Created on 7. 8. 2017, 10:12:28 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.net.URL;
import java.util.Date;

/**
 * 
 * @author burgetr
 */
public class HistoryItem
{
    public enum Type { VISIT, DOWNLOAD } 
    
    private int id;
    private Type type;
    private Date date;
    private URL url;
    private int count;
    private String title;
    private String filePath;
    
    
    public HistoryItem(int id, Type type, Date date, URL url)
    {
        this.id = id;
        this.type = type;
        this.date = date;
        this.url = url;
    }

    public int getId()
    {
        return id;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public URL getUrl()
    {
        return url;
    }

    public void setUrl(URL url)
    {
        this.url = url;
    }

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSourceId()
    {
        return ((getType() == Type.DOWNLOAD) ? "D" : "V") + getId();
    }
    
    @Override
    public String toString()
    {
        String ret = getType() + "#" + getId() + " " + getDate();
        if (getTitle() != null)
            ret += " " + getTitle();
        if (getUrl() != null)
            ret += " [" + getUrl() + "]";
        return ret;
    }
    
}
