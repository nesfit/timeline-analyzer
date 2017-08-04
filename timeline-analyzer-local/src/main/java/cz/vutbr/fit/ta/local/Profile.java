/**
 * Profile.java
 *
 * Created on 1. 2. 2017, 15:14:45 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.io.File;
import java.util.List;

/**
 * 
 * @author burgetr
 */
abstract public class Profile
{
    public String name;
    public File path;
    
    
    public Profile(String name, File path)
    {
        this.name = name;
        this.path = path;
    }
    
    public String toString()
    {
        return name + " (" + path.getPath() + ")";
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public File getPath()
    {
        return path;
    }

    public void setPath(File path)
    {
        this.path = path;
    }
    
    abstract public List<Cookie> getCookies();
    
}
