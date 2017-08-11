/**
 * AnalysisTest.java
 *
 * Created on 10. 8. 2017, 11:14:10 by burgetr
 */
package cz.vutbr.fit.ta.links;

import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.core.RDFConnectorSesame;

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

        RDFConnector repo = new RDFConnectorSesame(REPO);
        URLLinkAnalyzer la = new URLLinkAnalyzer(repo);
        la.findImageURLs();
        la.findSharedURLs();
        
        la.saveModel();
        repo.closeConnection();
    }

}
