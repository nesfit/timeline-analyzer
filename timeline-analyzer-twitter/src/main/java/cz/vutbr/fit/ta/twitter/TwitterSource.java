/**
 * TwitterSource.java
 *
 * Created on 28. 7. 2017, 13:19:19 by burgetr
 */
package cz.vutbr.fit.ta.twitter;

import java.util.List;

import cz.vutbr.fit.ta.core.TimelineSource;
import cz.vutbr.fit.ta.ontology.Entry;
import cz.vutbr.fit.ta.ontology.Image;
import cz.vutbr.fit.ta.ontology.TextContent;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.twitter.model.TwitterEntityFactory;
import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * 
 * @author burgetr
 */
public class TwitterSource extends TimelineSource
{
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
        
        for (Status status : statuses)
        {
            Entry entry = ef.createEntry(status.getId());
            entry.setSourceId(String.valueOf(status.getId()));
            entry.setTimestamp(status.getCreatedAt());
            timeline.addEntry(entry);
            
            //text
            if (status.getText() != null)
            {
                TextContent tc = ef.createTextContent(status.getId());
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
                        Image img = ef.createImage(entity.getId());
                        img.setSourceUrl(entity.getMediaURL());
                    }
                }
            }
            
            
        }
        
        return timeline;
    }
    
    private List<Status> loadTimeline(String user) throws TwitterException
    {
        List<Status> statuses;
        
        //user = twitter.verifyCredentials().getScreenName();
        //statuses = twitter.getUserTimeline();
        User u = twitter.showUser(user);
        
        System.out.println();
        System.out.println("User: " + u);
        
        Paging p = new Paging(1, 10);
        statuses = twitter.getUserTimeline(user, p);
        
        System.out.println("Showing @" + user + "'s user timeline.");
        for (Status status : statuses) {
            System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            System.out.println("   Time: " + status.getCreatedAt());
            System.out.println("   GEO: " + status.getGeoLocation());
            MediaEntity[] media = status.getMediaEntities();
            if (media != null && media.length > 0)
            {
                System.out.println("   Media: ");
                for (MediaEntity entity : media)
                    System.out.println("       - " + entity);
            }
        }
        
        return statuses;
        
    }
    
}
