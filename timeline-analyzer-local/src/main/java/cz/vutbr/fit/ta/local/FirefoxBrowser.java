/**
 * FirefoxBrowser.java
 *
 * Created on 1. 2. 2017, 14:45:20 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author burgetr
 */
public class FirefoxBrowser extends Browser
{
    private static Logger log = LoggerFactory.getLogger(FirefoxBrowser.class);
    
    private String profilePath;
    private List<Profile> profiles;
    

    public FirefoxBrowser(OS os)
    {
        super(os);
        profilePath = findProfilePath();
        profiles = findProfiles();
    }

    @Override
    public String getProfilePath()
    {
        return profilePath;
    }
    
    @Override
    public List<Profile> getProfiles()
    {
        return profiles;
    }
    
    //================================================================================================================================

    private String findProfilePath()
    {
        String profilePath;
        if (getOs().isLinux())
        {
            profilePath = System.getProperty("user.home") + File.separator + ".mozilla" + File.separator + "firefox";
        }
        else if (getOs().isWindows())
        {
            // Pre Win 7
            profilePath = System.getProperty("user.home") + File.separator + "Application Data" + File.separator + "Mozilla" + File.separator + "Firefox" + File.separator + "Profiles";
            // Win 7+
            if (!(new File(profilePath)).exists())
                profilePath = System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Roaming" + File.separator + "Mozilla" + File.separator + "Firefox" + File.separator + "Profiles";
        }
        else
        {
            log.error("Unsupported OS {}", getOs().toString());
            profilePath = System.getProperty("user.home");
        }
        return profilePath;
    }

    private List<Profile> findProfiles()
    {
        List<Profile> ret = new ArrayList<>();
        
        File folder = new File(profilePath);
        File[] fileList = folder.listFiles();
        if (fileList != null)
        {
            for (int i = 0; i < fileList.length; i++) 
            {
                File file = fileList[i];
                if (file.getName().endsWith(".default")) 
                {
                    String name = file.getName().substring(0, file.getName().length() - 8);
                    Profile profile = new FirefoxProfile(name, file);
                    ret.add(profile);
                    log.info("Found firefox profile {}", profile);
                }
            }
        }
        
        return ret;
    }
    
}
