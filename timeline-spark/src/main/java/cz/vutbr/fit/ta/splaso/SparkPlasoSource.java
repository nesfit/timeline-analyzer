/**
 * SparkPlasoSource.java
 *
 * Created on 7. 8. 2019, 13:12:53 by burgetr
 */
package cz.vutbr.fit.ta.splaso;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.core.TimelineSource;
import cz.vutbr.fit.ta.ontology.Event;
import cz.vutbr.fit.ta.ontology.FileDownloadEvent;
import cz.vutbr.fit.ta.ontology.LocalFile;
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
    public static final String DATA_TYPE_KEY = "data_type";
    public static final String TIMESTAMP_KEY = "timestamp";
    public static final String IDENTIFIER_KEY = "_identifier";
    public static final String URL_KEY = "url";
    public static final String FILE_PATH_KEY = "full_path";
    public static final String TITLE_KEY = "title";
    
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
            switch (entry.getEventData().get(DATA_TYPE_KEY).toString())
            {
                case "firefox:places:page_visited":
                case "chrome:history:page_visited":
                    return createPageVisit(entry);
                case "chrome:history:file_downloaded":
                    return createFileDownload(entry);
                default:
                    return createGenericEvent(entry);
            }
        }
        else
            return null;
    }
    
    private Event createGenericEvent(PlasoEntry entry)
    {
        final PlasoEntityFactory factory = PlasoEntityFactory.getInstance();
        
        Event ev = factory.createGenericEvent(
                ResourceFactory.createResourceIRI("plaso", profileId + "-event", String.valueOf(idCnt++)), entry);
        
        if (entry.getEvent().containsKey(TIMESTAMP_KEY))
        {
            final Date ts = decodeTimestamp(entry);
            ev.setTimestamp(ts);
        }
        
        return ev;
    }
    
    private Event createPageVisit(PlasoEntry entry)
    {
        final PlasoEntityFactory factory = PlasoEntityFactory.getInstance();
        
        URLVisitEvent ev = factory.createURLVisitEvent(
                ResourceFactory.createResourceIRI("plaso", profileId + "-visit", String.valueOf(idCnt++)), entry);
        
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
    
    private Event createFileDownload(PlasoEntry entry)
    {
        final PlasoEntityFactory factory = PlasoEntityFactory.getInstance();

        FileDownloadEvent ev = factory.createFileDownloadEvent(
                ResourceFactory.createResourceIRI("plaso", profileId + "-download", String.valueOf(idCnt++)), entry);
        
        if (entry.getEvent().containsKey(TIMESTAMP_KEY))
        {
            final Date ts = decodeTimestamp(entry);
            ev.setTimestamp(ts);
        }
        
        WebResource wres = createWebResource(entry);
        if (wres != null)
            wres.addEvent(ev);
        
        LocalFile file = createLocalFile(entry);
        if (file != null)
            file.addEvent(ev);
        
        return ev;
    }

    private WebResource createWebResource(PlasoEntry entry)
    {
        final PlasoEntityFactory factory = PlasoEntityFactory.getInstance();
        final String urlString = entry.getEventData().get(URL_KEY).toString();
        if (urlString != null)
        {
            WebResource ret = factory.createWebResource(ResourceFactory.createUrlIRI(urlString));
            ret.setSourceUrl(urlString);
            if (entry.getEventData().get(TITLE_KEY) != null)
                ret.setResourceTitle(entry.getEventData().get(TITLE_KEY).toString());
            return ret;
        }
        else
            return null;
    }
    
    private LocalFile createLocalFile(PlasoEntry entry)
    {
        final PlasoEntityFactory factory = PlasoEntityFactory.getInstance();
        String pathString = entry.getEventData().get(FILE_PATH_KEY).toString();
        //normalize the paths to use a standard slash instead of backslash
        pathString = pathString.replace('\\', '/');
        pathString = pathString.replace("//", "/");
        if (pathString != null)
        {
            LocalFile ret = factory.createLocalFile(ResourceFactory.createFileIRI(profileId, pathString));
            ret.setPath(pathString);
            ret.setFileName(extractFileName(pathString));
            return ret;
        }
        else
            return null;
    }
    
    private Date decodeTimestamp(PlasoEntry entry)
    {
        final Object microts = entry.getEvent().get(TIMESTAMP_KEY); //plaso timestamp is in microseconds
        if (microts != null && (microts instanceof Long || microts instanceof Integer))
        {
            final long micros = (long) microts;
            return new Date(micros / 1000);
        }
        else
            return null;
    }
    
    private String extractFileName(String path)
    {
        final Path p = Paths.get(path);
        return p.getFileName().toString();
    }
    
}
