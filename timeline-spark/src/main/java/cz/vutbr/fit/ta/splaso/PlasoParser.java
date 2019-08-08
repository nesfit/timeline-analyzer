/**
 * PlasoParser.java
 *
 * Created on 8. 8. 2019, 12:19:50 by burgetr
 */
package cz.vutbr.fit.ta.splaso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author burgetr
 */
public class PlasoParser
{

    public PlasoParser()
    {
        
    }
    
    public List<PlasoEntry> parseInputStream(InputStream is) throws IOException
    {
        final Pattern p = Pattern.compile("^.*'([a-zA-Z_]+)':\\s+(.+)[\\,\\}]$");
        List<PlasoEntry> ret = new ArrayList<>();
    
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        
        String line;
        PlasoEntry curEntry = null;
        Map<String, String> curMap = null;
        while ((line = in.readLine()) != null)
        {
            line = line.trim();
            switch (line) {
                case "Event:":
                    if (curEntry != null)
                        ret.add(curEntry);
                    curEntry = new PlasoEntry();
                    curMap = curEntry.getEvent();
                    break;
                
                case "Event data:":
                    if (curEntry != null)
                        curMap = curEntry.getEventData();
                    break;
                
                default:
                    Matcher m = p.matcher(line);
                    if (m.find())
                    {
                        String key = m.group(1);
                        String val = m.group(2);
                        if (val != null && !val.equals("None"))
                        {
                            if (val.startsWith("[u'"))
                                val = val.substring(3);
                            else if (val.startsWith("u'"))
                                val = val.substring(2);
                            else if (val.startsWith("'"))
                                val = val.substring(1);
                            
                            if (val.endsWith("']"))
                                val = val.substring(0, val.length() - 2);
                            else if (val.endsWith("'"))
                                val = val.substring(0, val.length() - 1);
                            
                            //TODO unescape?
                            //System.out.println(key+"="+val);
                            if (curMap != null)
                                curMap.put(key, val);
                        }
                    }
                    break;
            }
        }
        if (curEntry != null)
            ret.add(curEntry);
        
        return ret;
    }
    
}
