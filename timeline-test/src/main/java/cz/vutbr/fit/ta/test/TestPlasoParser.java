/**
 * TestPlasoParser.java
 *
 * Created on 4. 9. 2019, 14:36:12 by burgetr
 */
package cz.vutbr.fit.ta.test;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;

import com.github.radkovo.rdf4j.builder.TargetModel;

import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.core.RDFConnectorRDF4J;
import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.splaso.PlasoEntry;
import cz.vutbr.fit.ta.splaso.PlasoParser;
import cz.vutbr.fit.ta.splaso.SparkPlasoSource;

/**
 * 
 * @author burgetr
 */
public class TestPlasoParser
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        final String REPO = "http://localhost:8080/rdf4j-server/repositories/testplaso";
        
        try
        {
            InputStream is = new FileInputStream("/tmp/eee");
            
            PlasoParser p = new PlasoParser();
            List<PlasoEntry> entries = p.parseInputStream(is);
            is.close();
            
            System.out.println("Read " + entries.size() + " entries");
            SparkPlasoSource src = new SparkPlasoSource("plasotest", entries);
            Timeline timeline = src.getTimeline();
            
            TargetModel target = new TargetModel(new LinkedHashModel());
            target.add(timeline);
            //System.out.println(model);
            System.out.println("Model created, " + target.getModel().size() + " triples");
            
            System.out.println("Start at " + (new Date()));
            RDFConnector rdfcon = new RDFConnectorRDF4J(REPO);
            rdfcon.add(target.getModel(), getContext());
            rdfcon.close();
            System.out.println("Finished at " + (new Date()));
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    protected static Resource getContext()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();        
        String stamp = df.format(today);
        return ResourceFactory.createResourceIRI("plaso", "context", stamp);
    }

}
