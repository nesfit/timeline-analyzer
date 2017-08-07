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
    
    private Type type;
    private Date date;
    private URL url;
    private int count;
    private String title;
    
    public HistoryItem(Type type, Date date, URL url)
    {
        this.type = type;
        this.date = date;
        this.url = url;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        String ret = getType() + " " + getDate();
        if (getTitle() != null)
            ret += " " + getTitle();
        if (getUrl() != null)
            ret += " [" + getUrl() + "]";
        return ret;
    }
    
}
