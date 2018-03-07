/**
 * TimelineSource.java
 *
 * Created on 28. 7. 2017, 13:15:10 by burgetr
 */
package cz.vutbr.fit.ta.core;

import java.util.Date;

import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public abstract class TimelineSource
{
    private Date startDate;
    private Date endDate;
    private int limit;
    
    public TimelineSource()
    {
        startDate = null;
        endDate = null;
        limit = 500;
    }
    
    public Date getStartDate()
    {
        return startDate;
    }

    /**
     * Sets the start date of the timeline.
     * @param startDate
     */
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    /**
     * Sets the end date of the timeline.
     * @param endDate
     */
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public int getLimit()
    {
        return limit;
    }

    /**
     * Sets the maximal number of fetched entries. This is ignored when both the start date and
     * the end date are used.
     * @param limit
     */
    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    /**
     * Reads the timeline based on the specified limits.
     * @return
     */
    abstract public Timeline getTimeline();
    
}
