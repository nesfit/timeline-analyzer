/**
 * DownloadTool.java
 *
 * Created on 22. 10. 2019, 12:15:42 by burgetr
 */
package cz.vutbr.fit.ta.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private static final String KEY_DOWNLOAD_START_DATE = "download.startDate";
    private static final String KEY_DOWNLOAD_END_DATE = "download.endDate";
    private static final String KEY_DOWNLOAD_LIMIT = "download.limit";
    private static final String CONFIG_FILE = "config.properties";
    
    private static final String DEFAULT_START_DATE = "01/08/2017"; // some reasonable start date

    private Properties configFile;
    
    private String repositoryUrl = "http://localhost:8080/rdf4j-server/repositories/ta";
    private Date startDate;
    private Date endDate;
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

    public Date getStartDate()
    {
        return startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public int getLimit()
    {
        return limit;
    }

    protected void loadConfigValues()
    {
        repositoryUrl = getConfig(KEY_REPO_URL, repositoryUrl);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            startDate = sdf.parse(DEFAULT_START_DATE);
            startDate = getConfig(KEY_DOWNLOAD_START_DATE, startDate, sdf);
        } catch (ParseException e) {
            log.error("Incorrect config value of {}: {}", KEY_DOWNLOAD_START_DATE, e.getMessage());
        }
        
        try {
            endDate = new Date(); //use current
            endDate = getConfig(KEY_DOWNLOAD_END_DATE, endDate, sdf);
        } catch (ParseException e) {
            log.error("Incorrect config value of {}: {}", KEY_DOWNLOAD_END_DATE, e.getMessage());
        }
        
        try {
            limit = getConfig(KEY_DOWNLOAD_LIMIT, limit);
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
    
    protected int getConfig(String key, int defaultValue) throws NumberFormatException
    {
        String ret = System.getProperty(key);
        if (ret == null && configFile != null)
            ret = configFile.getProperty(key);
        return (ret == null) ? defaultValue : Integer.parseInt(ret);
    }
    
    protected Date getConfig(String key, Date defaultValue, DateFormat dateFormat) throws ParseException
    {
        String ret = System.getProperty(key);
        if (ret == null && configFile != null)
            ret = configFile.getProperty(key);
        return (ret == null) ? defaultValue : dateFormat.parse(ret);
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
