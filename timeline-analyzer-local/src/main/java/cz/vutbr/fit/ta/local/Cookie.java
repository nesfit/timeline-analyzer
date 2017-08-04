/**
 * Cookie.java
 *
 * Created on 1. 2. 2017, 16:30:11 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.util.Date;

/**
 * 
 * @author burgetr
 */
public class Cookie
{
    private String baseDomain;
    private String name;
    private String value;
    private String host;
    private String path;
    private Date expiry;
    private Date lastAccessed;
    private Date created;
    private boolean secure;
    private boolean httpOnly;
    
    
    public Cookie(String baseDomain, String name, String value, String host,
            String path, Date expiry, Date lastAccessed, Date created,
            boolean secure, boolean httpOnly)
    {
        this.baseDomain = baseDomain;
        this.name = name;
        this.value = value;
        this.host = host;
        this.path = path;
        this.expiry = expiry;
        this.lastAccessed = lastAccessed;
        this.created = created;
        this.secure = secure;
        this.httpOnly = httpOnly;
    }

    public String getBaseDomain()
    {
        return baseDomain;
    }

    public void setBaseDomain(String baseDomain)
    {
        this.baseDomain = baseDomain;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Date getExpiry()
    {
        return expiry;
    }

    public void setExpiry(Date expiry)
    {
        this.expiry = expiry;
    }

    public Date getLastAccessed()
    {
        return lastAccessed;
    }

    public void setLastAccessed(Date lastAccessed)
    {
        this.lastAccessed = lastAccessed;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public boolean isSecure()
    {
        return secure;
    }

    public void setSecure(boolean secure)
    {
        this.secure = secure;
    }

    public boolean isHttpOnly()
    {
        return httpOnly;
    }

    public void setHttpOnly(boolean httpOnly)
    {
        this.httpOnly = httpOnly;
    }

    @Override
    public String toString()
    {
        return "Cookie [name=" + name + ", value=" + value + ", baseDomain="
                + baseDomain + ", host=" + host + ", path=" + path + ", expiry="
                + expiry + ", lastAccessed=" + lastAccessed + ", created="
                + created + ", secure=" + secure + ", httpOnly=" + httpOnly
                + "]";
    }
    
}
