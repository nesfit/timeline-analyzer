/**
 * LocalProfileSource.java
 *
 * Created on 7. 8. 2017, 13:45:33 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.util.Date;
import java.util.List;

import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.core.TimelineSource;
import cz.vutbr.fit.ta.local.HistoryItem.Type;
import cz.vutbr.fit.ta.local.model.LocalEntityFactory;
import cz.vutbr.fit.ta.ontology.FileDownloadEvent;
import cz.vutbr.fit.ta.ontology.LocalFile;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.ontology.URLVisitEvent;
import cz.vutbr.fit.ta.ontology.WebResource;

/**
 * 
 * @author burgetr
 */
public class LocalProfileSource extends TimelineSource
{
    private Browser browser;
    private Profile profile;
    private Date dateFrom;
    private Date dateTo;
    
    public LocalProfileSource(Browser browser, Profile profile, Date dateFrom, Date dateTo)
    {
        this.browser = browser;
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
        timeline.setSourceId(profile.getPath().toString()); //TODO add task id?
        
        for (HistoryItem item : items) 
        {
            WebResource wurl = null;
            if (item.getUrl() != null)
            {
                wurl = factory.createWebResource(item.getUrl().toString());
                wurl.setSourceUrl(item.getUrl().toString());
                if (item.getTitle() != null)
                    wurl.setResourceTitle(item.getTitle());
            }
            
            if (item.getType() == Type.VISIT)
            {
                URLVisitEvent ev = factory.createURLVisitEvent(ResourceFactory.createResourceIRI("local", "visit", item.getSourceId()));
                ev.setTimestamp(item.getDate());
                if (wurl != null)
                    wurl.addEvent(ev);
                timeline.addEvent(ev);
            }
            else if (item.getType() == Type.DOWNLOAD)
            {
                FileDownloadEvent ev = factory.createFileDownloadEvent(ResourceFactory.createResourceIRI("local", "download", item.getSourceId()));
                ev.setTimestamp(item.getDate());
                if (wurl != null)
                    wurl.addEvent(ev);
                if (item.getFilePath() != null)
                {
                    LocalFile file = factory.createLocalFile(timeline.getSourceId(), item.getFilePath());
                    file.setPath(item.getFilePath());
                    file.setFileName(browser.getOs().extractFilenameFromPath(item.getFilePath()));
                    file.addEvent(ev);
                }
                timeline.addEvent(ev);
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
