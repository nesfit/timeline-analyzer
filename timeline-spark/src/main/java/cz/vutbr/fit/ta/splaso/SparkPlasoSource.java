/**
 * SparkPlasoSource.java
 *
 * Created on 7. 8. 2019, 13:12:53 by burgetr
 */
package cz.vutbr.fit.ta.splaso;

import java.util.Date;
import java.util.List;

import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.core.TimelineSource;
import cz.vutbr.fit.ta.ontology.Event;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.ontology.URLVisitEvent;
import cz.vutbr.fit.ta.ontology.WebResource;
import cz.vutbr.fit.ta.splaso.model.PlasoEntityFactory;

/**
 * 
 * @author burgetr
 */
public class SparkPlasoSource extends TimelineSource
{
    private static final String DATA_TYPE_KEY = "data_type";
    private static final String TIMESTAMP_KEY = "timestamp";
    private static final String URL_KEY = "url";
    private static final String TITLE_KEY = "title";
    
    private int idCnt = 0;
    
    private String profileId;
    private List<PlasoEntry> entries;
    
    
    public SparkPlasoSource(String profileId, List<PlasoEntry> entries)
    {
        this.profileId = profileId;
        this.entries = entries;
    }

    @Override
    public Timeline getTimeline()
    {
        final PlasoEntityFactory factory = PlasoEntityFactory.getInstance();
        final Timeline timeline = factory.createTimeline(profileId);
        timeline.setSourceId(profileId);
        
        for (PlasoEntry entry : entries)
        {
            final Event ev = parseEvent(entry);
            if (ev != null)
            {
                timeline.addEvent(ev);
            }
        }
        
        return timeline;
    }

    private Event parseEvent(PlasoEntry entry)
    {
        if (entry.getEventData().get(DATA_TYPE_KEY) != null)
        {
            switch (entry.getEventData().get(DATA_TYPE_KEY))
            {
                case "firefox:places:page_visited":
                    return createPageVisit(entry);
                default:
                    return null;
            }
        }
        else
            return null;
    }
    
    private Event createPageVisit(PlasoEntry entry)
    {
        final PlasoEntityFactory factory = PlasoEntityFactory.getInstance();
        
        URLVisitEvent ev = factory.createURLVisitEvent(ResourceFactory.createResourceIRI("local", "visit", String.valueOf(idCnt++)));
        
        if (entry.getEvent().containsKey(TIMESTAMP_KEY))
        {
            final Date ts = decodeTimestamp(entry);
            ev.setTimestamp(ts);
        }
        
        WebResource wres = createWebResource(entry);
        if (wres != null)
            wres.addEvent(ev);
        return ev;
    }

    private WebResource createWebResource(PlasoEntry entry)
    {
        final PlasoEntityFactory factory = PlasoEntityFactory.getInstance();
        final String urlString = entry.getEventData().get(URL_KEY);
        if (urlString != null)
        {
            WebResource ret = factory.createWebResource(ResourceFactory.createUrlIRI(urlString));
            ret.setSourceUrl(urlString);
            if (entry.getEventData().get(TITLE_KEY) != null)
                ret.setResourceTitle(entry.getEventData().get(TITLE_KEY));
            return ret;
        }
        else
            return null;
    }
    
    private Date decodeTimestamp(PlasoEntry entry)
    {
        final String microts = entry.getEvent().get(TIMESTAMP_KEY); //plaso timestamp is in microseconds
        if (microts != null)
        {
            final long micros = Long.valueOf(microts);
            return new Date(micros / 1000);
        }
        else
            return null;
    }
    
}
