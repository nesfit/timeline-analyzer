/**
 * TestLocal.java
 *
 * Created on 7. 8. 2017, 11:02:52 by burgetr
 */
package cz.vutbr.fit.ta.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;

import com.github.radkovo.rdf4j.builder.TargetModel;

import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.halyard.RDFConnectorHalyard;
import cz.vutbr.fit.ta.local.FirefoxBrowser;
import cz.vutbr.fit.ta.local.LocalProfileSource;
import cz.vutbr.fit.ta.local.OS;
import cz.vutbr.fit.ta.local.Profile;
import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public class TestLocalHalyard
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try {

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
                
                LocalProfileSource src = new LocalProfileSource(ff, prof, fromDate, toDate);
                Timeline timeline = src.getTimeline();
                
                TargetModel target = new TargetModel(new LinkedHashModel());
                target.add(timeline);
                //System.out.println(model);
                System.out.println("Model created, " + target.getModel().size() + " triples");
                
                System.out.println("Start at " + (new Date()));
                RDFConnector rdfcon = new RDFConnectorHalyard("test");
                rdfcon.add(target.getModel(), getContext());
                rdfcon.close();
                System.out.println("Finished at " + (new Date()));
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    protected static Resource getContext()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();        
        String stamp = df.format(today);
        return ResourceFactory.createResourceIRI("local", "context", stamp);
    }
    


}
