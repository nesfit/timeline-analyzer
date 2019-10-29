/**
 * DecodeProfiles.java
 *
 * Created on 29. 10. 2019, 10:21:27 by burgetr
 */
package cz.vutbr.fit.ta.local;

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
 * A command-line interface for decoding profiles.
 * 
 * @author burgetr
 */
public class DecodeProfiles extends DownloadTool
{
    private static Logger log = LoggerFactory.getLogger(DecodeProfiles.class);
    
    private Resource context;
    private RDFConnector rdfcon;
    
    
    public DecodeProfiles()
    {
        super();
        rdfcon = new RDFConnectorRDF4J(getRepositoryUrl());
        context = getContext();
    }

    public void downloadTimeline(String homedir) throws IOException
    {
        OS os = new OS();
        log.info(os.toString());
        FirefoxBrowser ff = new FirefoxBrowser(os);
        log.info("Found {} profiles", ff.getProfiles().size());
        for (Profile prof : ff.getProfiles())
        {
            LocalProfileSource src = new LocalProfileSource(ff, prof, getStartDate(), getEndDate());
            Timeline timeline = src.getTimeline();
            
            TargetModel target = new TargetModel(new LinkedHashModel());
            target.add(timeline);
            log.info("Model created, {} triples", target.getModel().size());
            
            rdfcon.add(target.getModel(), context);
        }
    }

    protected static Resource getContext()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();        
        String stamp = df.format(today);
        return ResourceFactory.createResourceIRI("local", "context", stamp);
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            System.out.println("Finds browser profiles in users' home directories and stores the events to a RDF storage");
            System.out.println("Usage: DecodeProfiles <homedir> [<homedir> ...]");
            System.out.println("       where homedir is a path to a user home directory (e.g. /home/john)");
            System.out.println();
            System.out.println("The target repository URL must be specified using -Drepo.url=<url> or in config.properties (see the docs).");
        }
        else
        {
            DecodeProfiles client = new DecodeProfiles();
            for (String homedir : args)
            {
                try
                {
                    client.downloadTimeline(homedir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
