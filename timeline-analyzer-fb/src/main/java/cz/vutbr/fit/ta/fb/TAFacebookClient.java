/**
 * TAFacebookClient.java
 *
 * Created on 2. 3. 2018, 13:58:24 by burgetr
 */
package cz.vutbr.fit.ta.fb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.restfb.DefaultFacebookClient;
import com.restfb.Version;

/**
 * 
 * @author burgetr
 */
public class TAFacebookClient extends DefaultFacebookClient
{
    
    public TAFacebookClient() 
    {
        super(Version.VERSION_2_12);
        Properties prop = loadProperties();
        String appId = prop.getProperty("fb.appId");
        String appSecret = prop.getProperty("fb.appSecret");

        AccessToken accessToken = this.obtainAppAccessToken(appId, appSecret);
        this.accessToken = accessToken.getAccessToken();
    }
    
    
    private static Properties loadProperties()
    {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "fb.properties";
            input = TAFacebookClient.class.getClassLoader().getResourceAsStream(filename);
            if (input == null)
                return null;

            prop.load(input);
            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
