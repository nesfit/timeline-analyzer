/**
 * Browser.java
 *
 * Created on 1. 2. 2017, 14:41:57 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.util.List;

/**
 * Represents the capabilities and resources of an abstract browser.
 * 
 * @author burgetr
 */
public abstract class Browser
{
    private OS os;
    
    public Browser(OS os)
    {
        this.os = os;
    }
    
    public OS getOs()
    {
        return os;
    }
    
    public abstract String getProfilePath();
 
    public abstract List<Profile> getProfiles();
    
}
