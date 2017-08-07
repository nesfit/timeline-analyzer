/**
 * TestLocal.java
 *
 * Created on 7. 8. 2017, 11:02:52 by burgetr
 */
package cz.vutbr.fit.ta.local;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author burgetr
 */
public class TestLocal
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try {
            OS os = new OS();
            System.out.println(os.toString());
            FirefoxBrowser ff = new FirefoxBrowser(os);
            if (ff.getProfiles().size() > 0)
            {
                Profile prof = ff.getProfiles().get(0);
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date fromDate = sdf.parse("01/01/2010");
                Date toDate = new Date();
                //List<HistoryItem> items = prof.getVisited(fromDate, toDate);
                List<HistoryItem> items = prof.getDownloaded(fromDate, toDate);
                for (HistoryItem item : items)
                {
                    System.out.println(item);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
