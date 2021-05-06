/**
 * PlasoEntry.java
 *
 * Created on 7. 8. 2019, 13:25:02 by burgetr
 */
package cz.vutbr.fit.ta.splaso;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.rdf4j.model.IRI;

import cz.vutbr.fit.ta.ontology.vocabulary.TA;
import io.github.radkovo.rdf4j.builder.RDFEntity;
import io.github.radkovo.rdf4j.builder.TargetModel;

/**
 * An event entry obtained from plaso. It contains the event map and the event_data map.
 * 
 * @author burgetr
 */
public class PlasoEntry
{
    private Map<String, String> event;
    private Map<String, String> eventData;
    private String source; // source file from which the event was extracted

    /** Special event data keys that are not dumped to RDF */
    private static final Set<String> specialKeys;
    static {
        specialKeys = new HashSet<>();
        specialKeys.add(SparkPlasoSource.TIMESTAMP_KEY);
        specialKeys.add(SparkPlasoSource.IDENTIFIER_KEY);
        specialKeys.add("__class__");
    }
    
    public static final String PROP_IRI_PREFIX = TA.NAMESPACE + "x-"; 
    
    
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

    public PlasoEntry(Map<String, String> event, Map<String, String> eventData, String src)
    {
        this.event = event;
        this.eventData = eventData;
        this.source = src;
    }

    public Map<String, String> getEvent()
    {
        return event;
    }

    public Map<String, String> getEventData()
    {
        return eventData;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }
    
    public IRI createKeyIRI(String key)
    {
        return RDFEntity.vf.createIRI(PROP_IRI_PREFIX + key);
    }
    
    public void addToModel(RDFEntity subject, TargetModel target)
    {
        for (String key : event.keySet())
        {
            if (!specialKeys.contains(key))
            {
                subject.addValue(target, createKeyIRI(key), event.get(key));
            }
        }
        for (String key : eventData.keySet())
        {
            if (!specialKeys.contains(key))
            {
                subject.addValue(target, createKeyIRI(key), eventData.get(key));
            }
        }
    }
    
}
