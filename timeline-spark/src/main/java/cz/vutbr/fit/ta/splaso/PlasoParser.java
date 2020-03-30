/**
 * PlasoParser.java
 *
 * Created on 29. 3. 2020, 20:06:02 by burgetr
 */
package cz.vutbr.fit.ta.splaso;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A common plaso output parser superclass.
 * 
 * @author burgetr
 */
public abstract class PlasoParser
{

    /**
     * Parses an input stream and creates a list of entries (events).
     * @param is The input stream to parse
     * @return a list of extracted entries
     * @throws IOException
     */
    public abstract List<PlasoEntry> parseInputStream(InputStream is) throws IOException;

}
