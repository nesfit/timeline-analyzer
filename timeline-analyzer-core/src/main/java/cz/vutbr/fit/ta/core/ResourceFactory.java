/**
 * ResourceFactory.java
 *
 * Created on 28. 7. 2017, 13:55:36 by burgetr
 */
package cz.vutbr.fit.ta.core;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

/**
 * 
 * @author burgetr
 */
public class ResourceFactory
{
    private final static ValueFactory vf = SimpleValueFactory.getInstance();
    private final static String RESOURCE_PREFIX = "http://nesfit.github.io/resource/ta#";
    
    public static IRI createResourceIRI(String module, String type, String id)
    {
        return vf.createIRI(RESOURCE_PREFIX, module + "-" + type + "-" + id);
    }

    public static IRI createUrlIRI(String urlString)
    {
        try
        {
            final String encUri = URLEncoder.encode(urlString, "UTF-8");
            return vf.createIRI(RESOURCE_PREFIX, "url-" + encUri);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static IRI createFileIRI(String taskId, String fullPath)
    {
        try
        {
            final String encUri = URLEncoder.encode(fullPath, "UTF-8");
            return vf.createIRI(RESOURCE_PREFIX, "file-" + taskId + "-" + encUri);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
