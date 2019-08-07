/**
 * Test.java
 *
 * Created on 31. 7. 2017, 14:05:14 by burgetr
 */
package cz.vutbr.fit.ta.test;

import java.io.IOException;
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
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.twitter.TwitterSource;

/**
 * Modified TestTwitter that uses the Halyard connector.  
 * @author burgetr
 */
public class TestTwitterHalyard
{
    private static Resource context;
    
    public static void downloadTimeline(String username, RDFConnector rdfcon) throws IOException
    {
        TwitterSource twitter = new TwitterSource(username);
        twitter.setLimit(1000);
        Timeline timeline = twitter.getTimeline();
        
        System.out.println("Got timeline of " + twitter.getUsername() + " of " + timeline.getEvents().size() + " events");
        
        TargetModel target = new TargetModel(new LinkedHashModel());
        target.add(timeline);
        //System.out.println(target.getModel());
        System.out.println("Model created, " + target.getModel().size() + " triples");
        
        rdfcon.add(target.getModel(), context);
    }
    
    protected static Resource getContext()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();        
        String stamp = df.format(today);
        return ResourceFactory.createResourceIRI("twitter", "context", stamp);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            RDFConnector rdfcon = new RDFConnectorHalyard("test");
            context = getContext();
            
            //downloadTimeline("iROZHLAScz", rdfcon);
            //downloadTimeline("RESPEKT_CZ", rdfcon);
            downloadTimeline("DVTVcz", rdfcon);
            /*downloadTimeline("veselovskyma", rdfcon);
            downloadTimeline("ekonom_cz", rdfcon);
            downloadTimeline("hospodarky", rdfcon);
            downloadTimeline("CT24zive", rdfcon);
            downloadTimeline("CzechTV", rdfcon);
            downloadTimeline("Aktualnecz", rdfcon);
            downloadTimeline("cermak", rdfcon);
            downloadTimeline("LudekStanek", rdfcon);
            downloadTimeline("JanHrebejk", rdfcon);
            //downloadTimeline("Seznam_Zpravy", rdfcon);
            downloadTimeline("atomsedlacek", rdfcon);
            downloadTimeline("lidovky", rdfcon);*/
            
            rdfcon.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}