/**
 * DownloadTwitter.java
 *
 * Created on 22. 10. 2019, 11:51:50 by burgetr
 */
package cz.vutbr.fit.ta.twitter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.ta.core.DownloadTool;
import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.core.RDFConnectorRDF4J;
import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.ontology.Timeline;
import io.github.radkovo.rdf4j.builder.TargetModel;

/**
 * 
 * @author burgetr
 */
public class DownloadTwitter extends DownloadTool
{
    private static Logger log = LoggerFactory.getLogger(DownloadTwitter.class);
    
    private Resource context;
    private RDFConnector rdfcon;
    
    
    public DownloadTwitter()
    {
        super();
        rdfcon = new RDFConnectorRDF4J(getRepositoryUrl());
        context = getContext();
    }
    
    public void downloadTimeline(String username) throws IOException
    {
        TwitterSource twitter = new TwitterSource(username);
        twitter.setLimit(getLimit());
        Timeline timeline = twitter.getTimeline();
        
        log.debug("Got timeline of {} of {} events", twitter.getUsername(), timeline.getEvents().size());
        
        TargetModel target = new TargetModel(new LinkedHashModel());
        target.add(timeline);
        //System.out.println(target.getModel());
        log.info("Model created, {} triples", target.getModel().size());
        
        rdfcon.add(target.getModel(), context);
    }
    
    protected Resource getContext()
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
        if (args.length == 0)
        {
            System.out.println("Downloads a user profile information to a RDF storage");
            System.out.println("Usage: DownloadTwitter <username> [<username> ...]");
            System.out.println("       where username is a Twitter user name (without the leading @)");
            System.out.println();
            System.out.println("The target repository URL must be specified using -Drepo.url=<url> or in config.properties (see the docs).");
        }
        else
        {
            DownloadTwitter client = new DownloadTwitter();
            for (String user : args)
            {
                try
                {
                    client.downloadTimeline(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
