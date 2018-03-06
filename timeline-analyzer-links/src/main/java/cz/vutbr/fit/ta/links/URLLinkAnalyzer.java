/**
 * URLLinkAnalyzer.java
 *
 * Created on 10. 8. 2017, 11:08:41 by burgetr
 */
package cz.vutbr.fit.ta.links;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.ontology.vocabulary.TA;

/**
 * Finds different URL connections among the entries and their contents.
 * 
 * @author burgetr
 */
public class URLLinkAnalyzer
{
    private static Logger log = LoggerFactory.getLogger(URLLinkAnalyzer.class);
    
    private RDFConnector repo;
    private Model model;

    
    public URLLinkAnalyzer(RDFConnector repo)
    {
        this.repo = repo;
        model = new LinkedHashModel();
    }

    public RDFConnector getRepo()
    {
        return repo;
    }

    public Model getModel()
    {
        return model;
    }
    
    public void saveModel()
    {
        repo.add(model);
    }

    //================================================================================
    
    /**
     * Finds all occurences of the image source URLs in other posts. Represents the connections
     * as the {@code ta:urlMention} links in the repo.
     */
    public Model findImageURLs()
    {
        Model ret = new LinkedHashModel();
        
        //find all image URLs
        String q1 = "SELECT ?img ?url WHERE {?img ta:sourceUrl ?url . ?img rdf:type ta:Image}";
        TupleQueryResult r1 = repo.executeQuery(q1);
        while (r1.hasNext())
        {
            BindingSet data = r1.next();
            String urlVal = data.getValue("url").stringValue();
            log.debug("Image URL: <{}>", urlVal);
            
            String q2 = "SELECT ?entry WHERE {?entry ta:contains ?content . ?content ta:sourceUrl \"" + urlVal + "\" . ?content rdf:type ta:URLContent}";
            TupleQueryResult r2 = repo.executeQuery(q2);
            while (r2.hasNext())
            {
                BindingSet row = r2.next();
                Value val = row.getValue("entry");
                if (val instanceof IRI)
                {
                    log.debug("    Adding ta:urlConnection to {}", val.stringValue());
                    ret.add((IRI) data.getValue("img"), TA.urlMention, (IRI) val);
                }
            }
            r2.close();
        }
        r1.close();
        
        log.debug("findImageURLs: found {} links", ret.size());
        model.addAll(ret);
        return ret;
    }
    
    /**
     * Finds all occurences of the published URLs in other posts. Represents the connections
     * as the {@code ta:sameUrl} links in the repo.
     */
    public Model findSharedURLs()
    {
        Model ret = new LinkedHashModel();
        
        //find all image URLs
        String q1 = "SELECT ?cont ?url WHERE {?cont ta:sourceUrl ?url . ?cont rdf:type ta:URLContent}";
        TupleQueryResult r1 = repo.executeQuery(q1);
        while (r1.hasNext())
        {
            BindingSet data = r1.next();
            String urlVal = data.getValue("url").stringValue();
            IRI srcIri = (IRI) data.getValue("cont");
            log.debug("Search URL: <{}>", urlVal);
            
            String q2 = "SELECT ?entry ?content WHERE {?entry ta:contains ?content . ?content ta:sourceUrl \"" + urlVal + "\" . ?content rdf:type ta:URLContent}";
            TupleQueryResult r2 = repo.executeQuery(q2);
            while (r2.hasNext())
            {
                BindingSet row = r2.next();
                IRI destIri = (IRI) row.getValue("content");
                Value entry = row.getValue("entry");
                if (entry instanceof IRI && !srcIri.equals(destIri))
                {
                    log.debug("    Adding ta:sameUrl to {}", entry.stringValue());
                    ret.add(srcIri, TA.sameURL, (IRI) entry);
                }
            }
            r2.close();
        }
        r1.close();
        
        log.debug("findSharedURLs: found {} links", ret.size());
        model.addAll(ret);
        return ret;
    }
    
}