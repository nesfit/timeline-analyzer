/**
 * TestFB.java
 *
 * Created on 2. 3. 2018, 10:43:26 by burgetr
 */
package cz.vutbr.fit.ta.fb;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Post;
import com.restfb.types.Post.Attachments;

import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.core.RDFConnectorSesame;
import cz.vutbr.fit.ta.ontology.Timeline;

import com.restfb.types.StoryAttachment;
import com.restfb.types.User;

/**
 * 
 * @author burgetr
 */
public class TestFB
{
    public static final String REPO = "http://localhost:8080/rdf4j-server/repositories/test";
    
    public static void downloadTimeline(String username, RDFConnector rdfcon)
    {
        FBSource fb = new FBSource(username);
        fb.setLimit(1000);
        Timeline timeline = fb.getTimeline();
        
        System.out.println("Got timeline of " + fb.getProfileId() + " of " + timeline.getEntries().size() + " entries");
        
        Model model = new LinkedHashModel();
        timeline.addToModel(model);
        System.out.println(model);
        
        rdfcon.add(model);
    }
    
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        RDFConnector rdfcon = new RDFConnectorSesame(REPO);

        downloadTimeline("iROZHLAS.cz", rdfcon);
        downloadTimeline("tydenikrespekt", rdfcon);
        downloadTimeline("DVTV.cz", rdfcon);
        downloadTimeline("ekonom.cz", rdfcon);
        downloadTimeline("ihned.cz", rdfcon);
        downloadTimeline("CT24.cz", rdfcon);
        downloadTimeline("ceskatelevize", rdfcon);
        downloadTimeline("Aktualne.cz", rdfcon);
        downloadTimeline("SeznamZpravy", rdfcon);
        downloadTimeline("sedlacekt", rdfcon);
        downloadTimeline("lidovky.cz", rdfcon);
        
        rdfcon.closeConnection();
        
        
        //testWithApp();
        
        /*ScopeBuilder scopeBuilder = new ScopeBuilder();
        scopeBuilder.addPermission(FacebookPermissions.USER_ABOUT_ME);
        FacebookClient client = new DefaultFacebookClient(Version.VERSION_2_12);
        String loginDialogUrlString = client.getLoginDialogUrl(appId, "http://www.fit.vutbr.cz", scopeBuilder);
        System.out.println(loginDialogUrlString);*/

        
    }

    //========================================================================================================
    
    protected static void testPublic()
    {
        FacebookClient fb = new DefaultFacebookClient(Version.VERSION_2_12);

        Connection<Post> myFeed = fb.fetchConnection("123858471641/feed", Post.class);
        for (List<Post> myFeedPage : myFeed)
        {
            for (Post post : myFeedPage)
            {
                System.out.println("Post: " + post);
            }
        }
    }
    
    protected static void testWithApp()
    {
        /*Properties prop = loadProperties();
        String appId = prop.getProperty("fb.appId");
        String appSecret = prop.getProperty("fb.appSecret");
        
        FacebookClient fb = new DefaultFacebookClient(Version.VERSION_2_12);
        AccessToken token = fb.obtainAppAccessToken(appId, appSecret);
        
        fb = new DefaultFacebookClient(token.getAccessToken(), Version.VERSION_2_12);*/
        FacebookClient fb = new TAFacebookClient();

        //String dest = "123858471641/feed"; //iRozhlas.cz
        String dest = "Aktualne.cz/feed";
        
        Connection<Post> myFeed = fb.fetchConnection(dest, Post.class, Parameter.with("fields", "created_time,message,caption,description,full_picture,link,name,object_id,place,type,status_type,attachments"));
        for (List<Post> myFeedPage : myFeed)
        {
            int cnt = 0;
            for (Post post : myFeedPage)
            {
                System.out.println("Post: " + post);
                
                System.out.println("Caption: " + post.getCaption());
                System.out.println("Text: " + post.getCaption());
                System.out.println("Type: " + post.getType());
                Attachments atts = post.getAttachments();
                for (StoryAttachment att : atts.getData())
                {
                    System.out.println("ATT " + att.getId());
                    System.out.println("  Type: " + att.getType());
                    StoryAttachment.Attachments satts = att.getSubAttachments();
                    if (satts != null)
                    {
                        for (StoryAttachment satt : satts.getData())
                        {
                            System.out.println("  SATT " + satt.getId());
                            System.out.println("    Type: " + satt.getType());
                            System.out.println("    Descr: " + satt.getDescription());
                            StoryAttachment.Media m = satt.getMedia();
                            System.out.println("    Media type: " + m.getType());
                            if (m.getImage() != null)
                            {
                                StoryAttachment.Image img = m.getImage();
                                System.out.println("    Image src: " + img.getSrc());
                            }
                        }
                    }
                }
                
                cnt++;
                if (cnt > 5)
                    break;
            }
            
            break; //just take the first page
        }

    }
    
    protected static void testWithToken()
    {
        String token;
        token = "EAACEdEose0cBAArKuVIzaiv0v83BG3r6o7A1tySnoyKYjAqzA8JUZA9Da2XZBvoNfOVSQWe0fbfgZBpoS8PWCMx9IX5rSzDgVkbQVnRrSZC9ZCPyTzOfqKTRyExqFUZCvnnC1cNDBZANuRf4ObyktfJ5GHk6aIu19iuhRoSVsDzM87ZA56W6z67XSux4D4iDAPgZD";
        
        FacebookClient fb = new DefaultFacebookClient(token, Version.VERSION_2_12);
        
        User user = fb.fetchObject("me", User.class);
        System.out.println(user);
    }
    
    protected static Properties loadProperties()
    {
        Properties prop = new Properties();
        InputStream input = null;

        try {

            String filename = "fb.properties";
            input = TestFB.class.getClassLoader().getResourceAsStream(filename);
            if (input == null)
                return null;

            prop.load(input);
            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
}
