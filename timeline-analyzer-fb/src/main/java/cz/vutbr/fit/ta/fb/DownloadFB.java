/**
 * DownloadFB.java
 *
 * Created on 22. 10. 2019, 12:49:42 by burgetr
 */
package cz.vutbr.fit.ta.fb;

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
public class DownloadFB extends DownloadTool
{
    private static Logger log = LoggerFactory.getLogger(DownloadFB.class);
    
    private Resource context;
    private RDFConnector rdfcon;
    
    
    public DownloadFB()
    {
        super();
        rdfcon = new RDFConnectorRDF4J(getRepositoryUrl());
        context = getContext();
    }

    public void downloadTimeline(String username) throws IOException
    {
        FBSource fb = new FBSource(username);
        fb.setLimit(getLimit());
        Timeline timeline = fb.getTimeline();
        
        log.debug("Got timeline of {} of {} events", fb.getProfileId(), timeline.getEvents().size());
        
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
            System.out.println("Usage: DownloadFB <username> [<username> ...]");
            System.out.println("       where username is a Facebook user name");
            System.out.println();
            System.out.println("The target repository URL must be specified using -Drepo.url=<url> or in config.properties (see the docs).");
        }
        else
        {
            DownloadFB client = new DownloadFB();
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
