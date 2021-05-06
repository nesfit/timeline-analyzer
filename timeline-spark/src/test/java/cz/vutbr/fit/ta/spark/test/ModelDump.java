/**
 * 
 */
package cz.vutbr.fit.ta.spark.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.splaso.PlasoEntry;
import cz.vutbr.fit.ta.splaso.PlasoJsonParser;
import cz.vutbr.fit.ta.splaso.PlasoParser;
import cz.vutbr.fit.ta.splaso.SparkPlasoSource;
import io.github.radkovo.rdf4j.builder.TargetModel;

/**
 * @author burgetr
 *
 */
public class ModelDump
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
            
            TargetModel target = new TargetModel(new LinkedHashModel());
            target.add(timeline);
            //System.out.println(target.getModel());
            System.out.println("Model created, " + target.getModel().size() + " triples");
            
            String outfile = "/tmp/model.ttl";
            FileOutputStream out = new FileOutputStream(outfile);
            try {
                Rio.write(target.getModel(), out, RDFFormat.TURTLE);
                System.out.println("Dumped to " + outfile);
            }
            finally {
                out.close();
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
