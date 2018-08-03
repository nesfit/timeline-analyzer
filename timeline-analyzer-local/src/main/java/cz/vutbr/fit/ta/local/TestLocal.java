/**
 * TestLocal.java
 *
 * Created on 7. 8. 2017, 11:02:52 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;

import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.core.RDFConnectorSesame;
import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public class TestLocal
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try {
            final String REPO = "http://localhost:8080/rdf4j-server/repositories/test2";

            OS os = new OS();
            System.out.println(os.toString());
            FirefoxBrowser ff = new FirefoxBrowser(os);
            if (ff.getProfiles().size() > 0)
            {
                Profile prof = ff.getProfiles().get(0);
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fromDate = sdf.parse("01/08/2017");
                Date toDate = new Date();
                
                //List<HistoryItem> items = prof.getVisited(fromDate, toDate);
                /*List<HistoryItem> items = prof.getDownloaded(fromDate, toDate);
                for (HistoryItem item : items)
                {
                    System.out.println(item);
                }*/
                
                LocalProfileSource src = new LocalProfileSource(prof, fromDate, toDate);
                Timeline timeline = src.getTimeline();
                
                Model model = new LinkedHashModel();
                timeline.addToModel(model);
                //System.out.println(model);
                System.out.println("Model created, " + model.size() + " triples");
                
                System.out.println("Start at " + (new Date()));
                RDFConnector rdfcon = new RDFConnectorSesame(REPO);
                rdfcon.add(model);
                rdfcon.close();
                System.out.println("Finished at " + (new Date()));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
