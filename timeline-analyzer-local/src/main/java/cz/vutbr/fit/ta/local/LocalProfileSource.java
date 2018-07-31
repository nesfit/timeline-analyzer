/**
 * LocalProfileSource.java
 *
 * Created on 7. 8. 2017, 13:45:33 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.util.Date;
import java.util.List;

import cz.vutbr.fit.ta.core.TimelineSource;
import cz.vutbr.fit.ta.local.HistoryItem.Type;
import cz.vutbr.fit.ta.local.model.LocalEntityFactory;
import cz.vutbr.fit.ta.ontology.Entry;
import cz.vutbr.fit.ta.ontology.TextContent;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.ontology.URLContent;

/**
 * 
 * @author burgetr
 */
public class LocalProfileSource extends TimelineSource
{
    private Profile profile;
    private Date dateFrom;
    private Date dateTo;
    
    public LocalProfileSource(Profile profile, Date dateFrom, Date dateTo)
    {
        this.profile = profile;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Profile getProfile()
    {
        return profile;
    }

    @Override
    public Timeline getTimeline()
    {
        List<HistoryItem> items = readItems(profile, dateFrom, dateTo);
        return loadTimeline(items);
    }
    
    //===================================================================================
    
    private Timeline loadTimeline(List<HistoryItem> items)
    {
        LocalEntityFactory factory = LocalEntityFactory.getInstance();
        Timeline timeline = factory.createTimeline(profile);
        timeline.setSourceId(profile.getPath().toString());
        
        for (HistoryItem item : items) 
        {
            Entry entry = factory.createEntry(item);
            entry.setSourceId(String.valueOf(item.getSourceId()));
            entry.setTimestamp(item.getDate());
            timeline.addEntry(entry);
            
            TextContent text = factory.createTextContent(item.getSourceId());
            String title = (item.getTitle() == null) ? "" : item.getTitle();
            if (item.getType() == Type.VISIT)
            {
                entry.setTags("visit");
                text.setText("Visited " + title);
            }
            else
            {
                entry.setTags("download");
                text.setText("Downloaded to " + title);
            }
            entry.getContains().add(text);
            
            if (item.getUrl() != null)
            {
                URLContent urlc = factory.createURLContent(item.getSourceId());
                urlc.setSourceUrl(item.getUrl().toString());
                urlc.setText(item.getUrl().toString());
                entry.getContains().add(urlc);
            }
            //for download entries: add the target url
            if (item.getType() == Type.DOWNLOAD && item.getTitle() != null)
            {
                URLContent urlc = factory.createURLContent(item.getSourceId() + "-target");
                urlc.setSourceUrl(item.getTitle());
                urlc.setText(item.getTitle());
                entry.getContains().add(urlc);
            }
            
        }
        
        return timeline;
    }
    
    private List<HistoryItem> readItems(Profile profile, Date dateFrom, Date dateTo)
    {
        List<HistoryItem> ret = profile.getVisited(dateFrom, dateTo);
        ret.addAll(profile.getDownloaded(dateFrom, dateTo));
        return ret;
    }

}
