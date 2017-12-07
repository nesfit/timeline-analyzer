/**
 * Test.java
 *
 * Created on 31. 7. 2017, 14:05:14 by burgetr
 */
package cz.vutbr.fit.ta.twitter;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;

import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.core.RDFConnectorSesame;
import cz.vutbr.fit.ta.ontology.Timeline;

/**
 * 
 * @author burgetr
 */
public class TestTwitter
{
    public static final String REPO = "http://localhost:8080/rdf4j-server/repositories/test";
    
    public static void downloadTimeline(String username, RDFConnector rdfcon)
    {
        TwitterSource twitter = new TwitterSource(username);
        Timeline timeline = twitter.getTimeline();
        
        System.out.println("Got timeline of " + twitter.getUsername() + " of " + timeline.getEntries().size() + " entries");
        
        Model model = new LinkedHashModel();
        timeline.addToModel(model);
        System.out.println(model);
        
        rdfcon.add(model);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        RDFConnector rdfcon = new RDFConnectorSesame(REPO);
        
        downloadTimeline("iROZHLAScz", rdfcon);
        downloadTimeline("RESPEKT_CZ", rdfcon);
        downloadTimeline("DVTVcz", rdfcon);
        downloadTimeline("veselovskyma", rdfcon);
        downloadTimeline("ekonom_cz", rdfcon);
        downloadTimeline("hospodarky", rdfcon);
        downloadTimeline("CT24zive", rdfcon);
        downloadTimeline("CzechTV", rdfcon);
        downloadTimeline("Aktualnecz", rdfcon);
        downloadTimeline("cermak", rdfcon);
        downloadTimeline("LudekStanek", rdfcon);
        
        rdfcon.closeConnection();
    }

}
