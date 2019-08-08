/**
 * Cli.java
 *
 * Created on 8. 8. 2019, 12:51:03 by burgetr
 */
package cz.vutbr.fit.ta.spark;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;

import com.github.radkovo.rdf4j.builder.TargetModel;

import cz.vutbr.fit.ta.core.RDFConnector;
import cz.vutbr.fit.ta.core.ResourceFactory;
import cz.vutbr.fit.ta.halyard.RDFConnectorHalyard;
import cz.vutbr.fit.ta.ontology.Timeline;
import cz.vutbr.fit.ta.splaso.PlasoEntry;
import cz.vutbr.fit.ta.splaso.PlasoParser;
import cz.vutbr.fit.ta.splaso.SparkPlasoSource;

/**
 * 
 * @author burgetr
 */
public class Cli
{

    public static void main(String[] args)
    {
        try
        {
            CommandLineParser parser = new PosixParser();
            CommandLine cli = parser.parse(getCliOpts(), args);
            
            if (cli.hasOption('h')) {
                printHelp();
                return;
            }
            
            InputStream is = new FileInputStream("/home/burgetr/tmp/plaso/burgetr/output.txt");
            PlasoParser p = new PlasoParser();
            List<PlasoEntry> entries = p.parseInputStream(is);
            is.close();
            
            System.out.println("Read " + entries.size() + " entries");
            SparkPlasoSource src = new SparkPlasoSource("plasotest", entries);
            Timeline timeline = src.getTimeline();
            
            TargetModel target = new TargetModel(new LinkedHashModel());
            target.add(timeline);
            //System.out.println(model);
            System.out.println("Model created, " + target.getModel().size() + " triples");
            
            System.out.println("Saving start at " + (new Date()));
            
            String tableName = cli.getOptionValue('t');
            System.out.println("Using table " + tableName);
            
            RDFConnector rdfcon;
            if (cli.hasOption('c'))
            {
                String configPath = cli.getOptionValue('c');
                rdfcon = new RDFConnectorHalyard(configPath, tableName);
            } 
            else if (cli.hasOption('q')) 
            {
                String quora = cli.getOptionValue('q');
                int port = Integer.valueOf(cli.getOptionValue('p'));
                rdfcon = new RDFConnectorHalyard(quora, port, tableName);
            }
            else
            {
                rdfcon = new RDFConnectorHalyard(tableName);
            }
            rdfcon.add(target.getModel(), getContext());
            rdfcon.close();
            System.out.println("Saving finished at " + (new Date()));
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MissingOptionException e) {
            printHelp("Missing option: " + e.getMessage());
        } catch (ParseException e) {
            printHelp(e.getMessage());
        }

    }
    
    protected static Resource getContext()
    {
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        Date today = Calendar.getInstance().getTime();        
        String stamp = df.format(today);
        return ResourceFactory.createResourceIRI("plaso", "context", stamp);
    }
    
    private static void printHelp()
    {
        printHelp(null);
    }

    private static void printHelp(String error)
    {
        HelpFormatter hf = new HelpFormatter();
        PrintWriter w = new PrintWriter(System.out);
        if (error != null)
        {
            hf.printWrapped(w, 80, error);
            w.println();
        }
        hf.printWrapped(w, 80, 12,
                "usage: Main [options...] [<input-file>]");
        hf.printWrapped(w, 80, 42,
                "  <input-file>                            the input file to read from, stdin if omitted");
        hf.printOptions(w, 80, getCliOpts(), 2, 2);
        w.flush();
        w.close();
    }
    
    @SuppressWarnings("static-access")
    private static Options getCliOpts() {
        Options o = new Options();

        o.addOption(OptionBuilder
                .withLongOpt("table-name")
                .withDescription("HBase table name to be used (required)")
                .hasArgs(1)
                .withArgName("table name")
                .isRequired(true)
                .create('t'));

        o.addOption(OptionBuilder
                .withLongOpt("quora")
                .withDescription("zookeeper quora (e.g. server1,server2)")
                .hasArgs(1)
                .withArgName("quora list")
                .isRequired(false)
                .create('q'));

        o.addOption(OptionBuilder
                .withLongOpt("port")
                .withDescription("zookeeper port")
                .hasArgs(1)
                .withArgName("port")
                .isRequired(false)
                .create('p'));

        o.addOption(OptionBuilder
                .withLongOpt("config")
                .withDescription("the hbase-site.xml config file to be used (replaces -p and -q)")
                .hasArgs(1)
                .withArgName("config")
                .isRequired(false)
                .create('c'));

        o.addOption(OptionBuilder
                .withLongOpt("help")
                .withDescription("print this help")
                .isRequired(false)
                .hasArg(false)
                .create('h'));

        return o;
    }


}
