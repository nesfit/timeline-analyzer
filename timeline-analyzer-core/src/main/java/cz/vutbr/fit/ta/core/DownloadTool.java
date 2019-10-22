/**
 * DownloadTool.java
 *
 * Created on 22. 10. 2019, 12:15:42 by burgetr
 */
package cz.vutbr.fit.ta.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A base for the configurable download tools.
 * 
 * @author burgetr
 */
public class DownloadTool
{
    private static Logger log = LoggerFactory.getLogger(DownloadTool.class);
    
    private static final String KEY_REPO_URL = "repo.url";
    private static final String KEY_DOWNLOAD_LIMIT = "download.limit";
    private static final String CONFIG_FILE = "config.properties";

    private Properties configFile;
    
    private String repositoryUrl = "http://localhost:8080/rdf4j-server/repositories/ta";
    private int limit = 1000;


    public DownloadTool()
    {
        configFile = loadConfigFile();
        loadConfigValues();
        log.info("Using repository URL: {}", repositoryUrl);
    }
    
    public String getRepositoryUrl()
    {
        return repositoryUrl;
    }

    public int getLimit()
    {
        return limit;
    }

    protected void loadConfigValues()
    {
        repositoryUrl = getConfig(KEY_REPO_URL, repositoryUrl);
        try {
            limit = Integer.parseInt(getConfig(KEY_DOWNLOAD_LIMIT, String.valueOf(limit)));
        } catch (NumberFormatException e) {
            log.error("Incorrect config value of {}: {}", KEY_DOWNLOAD_LIMIT, e.getMessage());
        }
    }

    protected String getConfig(String key, String defaultValue)
    {
        String ret = System.getProperty(key);
        if (ret == null && configFile != null)
            ret = configFile.getProperty(key);
        return (ret == null) ? defaultValue : ret;
    }
    
    private Properties loadConfigFile()
    {
        try (InputStream input = new FileInputStream(CONFIG_FILE)) 
        {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        } catch (IOException ex) {
            return null;
        }
    }
}
