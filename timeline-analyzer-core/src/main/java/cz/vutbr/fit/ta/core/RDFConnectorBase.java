/**
 * RDFConnectorBase.java
 *
 * Created on 26. 7. 2019, 10:56:23 by burgetr
 */
package cz.vutbr.fit.ta.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Common parts of the RDF connector implementations.
 * 
 * @author burgetr
 */
public class RDFConnectorBase
{
    private Map<String, String> prefixes;
    
    
    public RDFConnectorBase()
    {
        initPrefixes();
    }
    
    public Map<String, String> getPrefixes()
    {
        return prefixes;
    }

    private void initPrefixes()
    {
        prefixes = new HashMap<>();
        prefixes.put("ta", "http://nesfit.github.io/ontology/ta.owl#");
        prefixes.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        prefixes.put("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        prefixes.put("tares", "http://nesfit.github.io/resource/ta#");
    }
    
    public String getPrefixString()
    {
        String ret = "";
        for (Map.Entry<String, String> entry : prefixes.entrySet())
        {
            ret += "PREFIX " + entry.getKey() + ": <" + entry.getValue() + "> ";
        }
        return ret;
    }

}
