/**
 * AnalysisTest.java
 *
 * Created on 10. 8. 2017, 11:14:10 by burgetr
 */
package cz.vutbr.fit.ta.links;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.eclipse.rdf4j.model.Resource;

import cz.vutbr.fit.ta.core.RDFConnectorRemote;
import cz.vutbr.fit.ta.core.RDFConnectorRDF4J;
import cz.vutbr.fit.ta.core.ResourceFactory;

/**
 * 
 * @author burgetr
 */
public class AnalysisTest
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        final String REPO = "http://localhost:8080/rdf4j-server/repositories/test";

        try {
            RDFConnectorRemote repo = new RDFConnectorRDF4J(REPO);
            URLLinkAnalyzer la = new URLLinkAnalyzer(repo);
            la.normalizeURLs();
            //la.findImageURLs();
            la.findSharedURLs();
            
            la.saveModel(getContext());
            repo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    protected static Resource getContext()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();        
        String stamp = df.format(today);
        return ResourceFactory.createResourceIRI("analyzer", "context", stamp);
    }
    

}
