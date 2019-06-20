/**
 * Main.java
 *
 * Created on 26. 7. 2017, 14:46:28 by burgetr
 */
package cz.vutbr.fit.ta.tools;

import java.io.IOException;
import java.nio.file.Paths;

import org.eclipse.rdf4j.rio.RDFFormat;

import com.github.radkovo.rdf4j.builder.ClassBuilder;
import com.github.radkovo.rdf4j.vocab.GenerationException;
import com.github.radkovo.rdf4j.vocab.VocabBuilder;

/**
 * 
 * @author burgetr
 */
public class OntologyGenerator
{

    private static void generateFromOWL(String filename, RDFFormat format,
            String vocabName, String vocabDir, String vocabPackage,
            String classDir, String classPackage)
            throws IOException, GenerationException
    {
        //build vocabulary
        VocabBuilder vb = new VocabBuilder(filename, format);
        vb.setPackageName(vocabPackage);
        vb.generate(Paths.get(vocabDir, vocabName + ".java"));
        
        //build classes
        ClassBuilder cb = new ClassBuilder(filename, format);
        cb.setPackageName(classPackage);
        cb.setVocabPackageName(vocabPackage);
        cb.setVocabName(vocabName);
        cb.generate(classDir);
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            generateFromOWL("/home/burgetr/git/timeline-analyzer/timeline-analyzer-core/ontology/ta.owl",
                    null, //auto 
                    "TA", 
                    "/home/burgetr/git/timeline-analyzer/timeline-analyzer-core/src/main/java/cz/vutbr/fit/ta/ontology/vocabulary", 
                    "cz.vutbr.fit.ta.ontology.vocabulary",
                    "/home/burgetr/git/timeline-analyzer/timeline-analyzer-core/src/main/java/cz/vutbr/fit/ta/ontology", 
                    "cz.vutbr.fit.ta.ontology");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
