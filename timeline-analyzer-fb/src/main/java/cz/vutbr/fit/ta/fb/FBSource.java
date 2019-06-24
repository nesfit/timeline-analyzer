/**
 * FBSource.java
 *
 * Created on 6. 3. 2018, 11:12:08 by burgetr
 */
package cz.vutbr.fit.ta.fb;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Location;
import com.restfb.types.Post;
import com.restfb.types.StoryAttachment;
import com.restfb.types.Post.Attachments;

import cz.vutbr.fit.ta.core.TimelineSource;
import cz.vutbr.fit.ta.fb.model.FBEntityFactory;
import cz.vutbr.fit.ta.ontology.CreationEvent;
import cz.vutbr.fit.ta.ontology.Entry;
import cz.vutbr.fit.ta.ontology.Event;
import cz.vutbr.fit.ta.ontology.GeoContent;
import cz.vutbr.fit.ta.ontology.Image;
import cz.vutbr.fit.ta.ontology.TextContent;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.ontology.URLContent;
import cz.vutbr.fit.ta.ontology.WebResource;

/**
 * 
 * @author burgetr
 */
public class FBSource extends TimelineSource
{
    private static Logger log = LoggerFactory.getLogger(FBSource.class);

    private String profileId;
    private FacebookClient fb;
    
    
    public FBSource(String profileId)
    {
        this.profileId = profileId;
        fb = new TAFacebookClient();
    }

    public String getProfileId()
    {
        return profileId;
    }

    @Override
    public Timeline getTimeline()
    {
        Connection<Post> feed = loadFeed();
        return createTimeline(feed);
    }

    //================================================================================

    private Timeline createTimeline(Connection<Post> feed)
    {
        final FBEntityFactory ef = FBEntityFactory.getInstance();
        Timeline timeline = ef.createTimeline(profileId);
        timeline.setSourceId(profileId);
        
        int cnt = 0;
        for (List<Post> feedPage : feed)
        {
            for (Post post : feedPage)
            {
                Event event = createEventForEntry(ef, post);
                if (event != null)
                    timeline.addEvent(event);
                
                cnt++;
                if (cnt >= getLimit())
                    break;
            }
            if (cnt >= getLimit())
                break;
        }
        
        return timeline;
    }
    
    private Event createEventForEntry(FBEntityFactory ef, Post post)
    {
        CreationEvent cev = ef.createCreationEvent(post.getId());
        cev.setTimestamp(post.getCreatedTime());

        Entry entry = ef.createEntry(post.getId());
        entry.setSourceId(post.getId());
        entry.addEvent(cev);
        
        //text
        if (post.getMessage() != null)
        {
            TextContent tc = ef.createTextContent(post.getId());
            tc.setText(post.getMessage());
            entry.getContains().add(tc);
        }
        //URLs
        if (post.getLink() != null)
        {
            WebResource wurl = ef.createWebResource(post.getLink());
            wurl.setSourceUrl(post.getLink());
            
            URLContent uc = ef.createURLContent(post.getId(), 0); //only one link per post(?)
            uc.setLinksResource(wurl);
            if (post.getDescription() != null)
                uc.setText(post.getDescription());
            else if (post.getName() != null)
                uc.setText(post.getName());
            else if (post.getCaption() != null)
                uc.setText(post.getCaption());
            else
                uc.setText("???");
            entry.getContains().add(uc);
        }
        //Images
        Attachments atts = post.getAttachments();
        if (atts != null)
        {
            int icnt = 0;
            for (StoryAttachment att : atts.getData())
            {
                if (att.getMedia() != null)
                {
                    StoryAttachment.Image img = att.getMedia().getImage();
                    if (img != null)
                        entry.getContains().add(createImageFromAttachment(ef, img, post.getId(), icnt++));
                }
                //subattachments
                StoryAttachment.Attachments satts = att.getSubAttachments();
                if (satts != null)
                {
                    for (StoryAttachment satt : satts.getData())
                    {
                        if (satt.getMedia() != null)
                        {
                            StoryAttachment.Image img = satt.getMedia().getImage();
                            if (img != null)
                                entry.getContains().add(createImageFromAttachment(ef, img, post.getId(), icnt++));
                        }
                    }
                }
            }
        }
        //GEO entries
        if (post.getPlace() != null && post.getPlace().getLocation() != null)
        {
            Location loc = post.getPlace().getLocation();
            if (loc.getLatitude() != null && loc.getLongitude() != null)
            {
                GeoContent gc = ef.createGeoContent(post.getId());
                gc.setLatitude(loc.getLatitude());
                gc.setLongitude(loc.getLongitude());
                entry.getContains().add(gc);
            }
        }
        
        return cev;
    }
    
    private Image createImageFromAttachment(FBEntityFactory ef, StoryAttachment.Image img, String postId, int imgCnt)
    {
        WebResource wurl = ef.createWebResource(img.getSrc());
        wurl.setSourceUrl(img.getSrc());
        
        Image ret = ef.createImage(postId + "-img-" + imgCnt);
        ret.setLinksResource(wurl);
        return ret;
    }
    
    private Connection<Post> loadFeed()
    {
        String dest = getProfileId() + "/feed";

        List<Parameter> plist = new ArrayList<>();
        plist.add(Parameter.with("fields", "created_time,message,caption,description,full_picture,link,name,object_id,place,type,status_type,attachments"));
        if (getStartDate() != null)
            plist.add(Parameter.with("since", formatDateLimit(getStartDate())));
        if (getEndDate() != null)
            plist.add(Parameter.with("until", formatDateLimit(getEndDate())));
        Parameter[] params = new Parameter[plist.size()];
        params = plist.toArray(params);
        
        log.info("Fetching {} with params {}, limit {} entries", dest, plist, getLimit());
        
        Connection<Post> feed = fb.fetchConnection(dest, Post.class, params);
        return feed;
    }
    
    private String formatDateLimit(Date date)
    {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-mm-dd");
        return fmt.format(date);
    }
    
}
