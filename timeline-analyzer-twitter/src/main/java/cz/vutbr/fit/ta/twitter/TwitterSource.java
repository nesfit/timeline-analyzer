/**
 * TwitterSource.java
 *
 * Created on 28. 7. 2017, 13:19:19 by burgetr
 */
package cz.vutbr.fit.ta.twitter;

import cz.vutbr.fit.ta.core.TimelineSource;
import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public class TwitterSource extends TimelineSource
{
    private String username;
    

    public TwitterSource(String username)
    {
        this.username = username;
    }
    
    @Override
    public Timeline getTimeline()
    {
        return null;
    }
    
}
