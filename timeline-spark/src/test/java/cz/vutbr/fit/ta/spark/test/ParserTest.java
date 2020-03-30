/**
 * 
 */
package cz.vutbr.fit.ta.spark.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import cz.vutbr.fit.ta.splaso.PlasoEntry;
import cz.vutbr.fit.ta.splaso.PlasoJsonParser;

/**
 * @author burgetr
 *
 */
public class ParserTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            PlasoJsonParser pp = new PlasoJsonParser();
            InputStream is = new FileInputStream("/home/burgetr/work/tarzan/extr1.json");
            
            List<PlasoEntry> entries = pp.parseInputStream(is);
            
            for (PlasoEntry entry : entries)
            {
                System.out.println(entry.getSource());
                System.out.println(entry.getEvent());
                System.out.println(entry.getEventData());
                System.out.println("--------");
            }
            
            is.close();

            System.out.println("");
            System.out.println("Single:");
            is = new FileInputStream("/home/burgetr/work/tarzan/single.json");
            PlasoEntry entry = pp.parseSingleEntry(is);
            System.out.println(entry.getSource());
            System.out.println(entry.getEvent());
            System.out.println(entry.getEventData());
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
