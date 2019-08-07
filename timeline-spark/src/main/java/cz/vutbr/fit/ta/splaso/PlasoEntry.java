/**
 * PlasoEntry.java
 *
 * Created on 7. 8. 2019, 13:25:02 by burgetr
 */
package cz.vutbr.fit.ta.splaso;

import java.util.HashMap;
import java.util.Map;

/**
 * An event entry obtained from plaso. It contains the event map and the event_data map.
 * 
 * @author burgetr
 */
public class PlasoEntry
{
    private Map<String, String> event;
    private Map<String, String> eventData;

    
    public PlasoEntry()
    {
        event = new HashMap<>();
        eventData = new HashMap<>();
    }

    public PlasoEntry(Map<String, String> event, Map<String, String> eventData)
    {
        this.event = event;
        this.eventData = eventData;
    }

    public Map<String, String> getEvent()
    {
        return event;
    }

    public Map<String, String> getEventData()
    {
        return eventData;
    }
    
}
