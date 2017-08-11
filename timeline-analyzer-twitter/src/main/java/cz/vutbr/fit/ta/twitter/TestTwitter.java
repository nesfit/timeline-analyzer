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
public class Test
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        final String REPO = "http://localhost:8080/rdf4j-server/repositories/test";
        
        TwitterSource twitter = new TwitterSource("okundra");
        Timeline timeline = twitter.getTimeline();
        
        System.out.println("Got timeline of " + twitter.getUsername() + " of " + timeline.getEntries().size() + " entries");
        
        Model model = new LinkedHashModel();
        timeline.addToModel(model);
        System.out.println(model);
        
        RDFConnector rdfcon = new RDFConnectorSesame(REPO);
        rdfcon.add(model);
        rdfcon.closeConnection();
        

    }

}
