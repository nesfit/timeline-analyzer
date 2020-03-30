/**
 * 
 */
package cz.vutbr.fit.ta.spark.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cz.vutbr.fit.ta.ontology.Event;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.splaso.PlasoEntry;
import cz.vutbr.fit.ta.splaso.PlasoJsonParser;
import cz.vutbr.fit.ta.splaso.PlasoParser;
import cz.vutbr.fit.ta.splaso.SparkPlasoSource;

/**
 * @author burgetr
 *
 */
public class SourceTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            InputStream is = new FileInputStream("/home/burgetr/work/tarzan/extr1.json");
            PlasoParser pp = new PlasoJsonParser();
            List<PlasoEntry> entries = pp.parseInputStream(is);
            is.close();
            
            SparkPlasoSource source = new SparkPlasoSource("profile", entries);
            Timeline timeline = source.getTimeline();
            
            System.out.println(timeline.getEvents().size());
            
            for (Event e : timeline.getEvents())
            {
                System.out.println(e);
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
