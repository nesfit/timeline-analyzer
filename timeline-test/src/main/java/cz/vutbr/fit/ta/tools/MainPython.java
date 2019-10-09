/**
 * Main.java
 *
 * Created on 26. 7. 2017, 14:46:28 by burgetr
 */
package cz.vutbr.fit.ta.tools;

import java.nio.file.Paths;

import org.eclipse.rdf4j.rio.RDFFormat;

import com.github.radkovo.rdf4j.python.VocabBuilderPython;
import com.github.radkovo.rdf4j.vocab.VocabBuilder;

/**
 * 
 * @author burgetr
 */
public class MainPython
{

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            String filename = "/home/burgetr/git/timeline-analyzer/timeline-analyzer-core/ontology/ta.owl";
            RDFFormat format = null; //auto
            String vocabName = "TA";
            String vocabDir = "/home/burgetr/tmp";
            
            //build vocabulary
            VocabBuilder vb = new VocabBuilderPython(filename, format);
            vb.generate(Paths.get(vocabDir, vocabName + ".py"));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
