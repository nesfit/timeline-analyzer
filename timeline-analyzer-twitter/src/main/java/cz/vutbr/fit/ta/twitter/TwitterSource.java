/**
 * TwitterSource.java
 *
 * Created on 28. 7. 2017, 13:19:19 by burgetr
 */
package cz.vutbr.fit.ta.twitter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.ta.core.TimelineSource;
import cz.vutbr.fit.ta.ontology.CreationEvent;
import cz.vutbr.fit.ta.ontology.Entry;
import cz.vutbr.fit.ta.ontology.GeoContent;
import cz.vutbr.fit.ta.ontology.Image;
import cz.vutbr.fit.ta.ontology.TextContent;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.ontology.URLContent;
import cz.vutbr.fit.ta.ontology.WebResource;
import cz.vutbr.fit.ta.twitter.model.TwitterEntityFactory;
import twitter4j.GeoLocation;
import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;

/**
 * 
 * @author burgetr
 */
public class TwitterSource extends TimelineSource
{
    private static Logger log = LoggerFactory.getLogger(TwitterSource.class);
    
    private String username;
    private Twitter twitter;
    

    public TwitterSource(String username)
    {
        this.username = username;
        twitter = new TwitterFactory().getInstance();
    }
    
    public String getUsername()
    {
        return username;
    }

    @Override
    public Timeline getTimeline()
    {
        try {
            List<Status> statuses = loadTimeline(username);
            Timeline t = createTimeline(statuses);
            return t;
        } catch (TwitterException te) {
            te.printStackTrace();
            return null;
        }
    }
    
    //================================================================================
    
    private Timeline createTimeline(List<Status> statuses)
    {
        final TwitterEntityFactory ef = TwitterEntityFactory.getInstance(); 
        Timeline timeline = ef.createTimeline(username);
        timeline.setSourceId(username);
        
        for (Status status : statuses)
        {
            CreationEvent cev = ef.createCreationEvent(status.getId());
            cev.setTimestamp(status.getCreatedAt());
            
            Entry entry = ef.createEntry(status.getId());
            entry.setSourceId(String.valueOf(status.getId()));
            entry.addEvent(cev);
            
            timeline.addEvent(cev);
            
            //text
            if (status.getText() != null)
            {
                TextContent tc = ef.createTextContent(status.getId());
                tc.setText(status.getText());
                entry.getContains().add(tc);
            }
            //images
            MediaEntity[] media = status.getMediaEntities();
            if (media != null)
            {
                for (MediaEntity entity : media)
                {
                    if (entity.getType().equals("photo"))
                    {
                        WebResource wurl = ef.createWebResource(entity.getMediaURL());
                        wurl.setSourceUrl(entity.getMediaURL());
                        
                        Image img = ef.createImage(entity.getId());
                        img.setLinksResource(wurl);
                        entry.getContains().add(img);
                        cev.getRefersTo().add(wurl);
                    }
                }
            }
            //URLs
            URLEntity[] urls = status.getURLEntities();
            if (urls != null)
            {
                for (URLEntity entity : urls)
                {
                    WebResource wurl = ef.createWebResource(entity.getExpandedURL());
                    wurl.setSourceUrl(entity.getExpandedURL());
                    
                    URLContent url = ef.createURLContent(status.getId(), entity.getStart());
                    url.setLinksResource(wurl);
                    url.setText(entity.getDisplayURL());
                    entry.getContains().add(url);
                    cev.getRefersTo().add(wurl);
                }
            }
            //GEO entries
            GeoLocation loc = status.getGeoLocation();
            if (loc != null)
            {
                GeoContent geo = ef.createGeoContent(status.getId());
                geo.setLatitude(loc.getLatitude());
                geo.setLongitude(loc.getLongitude());
                entry.getContains().add(geo);
            }
            
        }
        
        return timeline;
    }
    
    private List<Status> loadTimeline(String user) throws TwitterException
    {
        List<Status> statuses;
        
        if (getStartDate() != null || getEndDate() != null)
            log.warn("Start/end dates are not yet supported by TwitterSource");
        
        //user = twitter.verifyCredentials().getScreenName();
        //statuses = twitter.getUserTimeline();
        //User u = twitter.showUser(user);
        
        log.info("Loading @{}'s user timeline, limit {} entries.", user, getLimit());
        
        Paging p = new Paging(1, getLimit());
        statuses = twitter.getUserTimeline(user, p);
        
        for (Status status : statuses) 
        {
            log.info("@" + status.getUser().getScreenName() + " - " + status.getText());
            log.info("   Time: " + status.getCreatedAt());
            log.info("   GEO: " + status.getGeoLocation());
            MediaEntity[] media = status.getMediaEntities();
            if (media != null && media.length > 0)
            {
                log.info("   Media: ");
                for (MediaEntity entity : media)
                    log.info("       - " + entity);
            }
        }
        
        return statuses;
    }
    
}
