package com.delvecore.utils;

import com.graphhopper.GraphHopper;
import com.graphhopper.routing.util.EncodingManager;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
	// write your code here

        CommandLineParser cliParser = new GnuParser();

        Options options = new Options();

        Option input = OptionBuilder.withArgName("input")
                .hasArgs(1)
                .isRequired()
                .withDescription("XML/PBF OSM data file")
                .create("input");

        Option output = OptionBuilder.withArgName("output")
                .hasArgs(1)
                .isRequired()
                .withDescription("Output directory for GraphHopper files")
                .create("output");

        options.addOption(input);
        options.addOption(output);

        if(args.length == 0){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("hoppim", options);
            return;
        }

        try{

            CommandLine line = cliParser.parse(options, args);

            String infile = line.getOptionValue("input");
            String outfile = line.getOptionValue("output");

            if(infile == null || outfile == null)
                System.out.print("Error with input\n");

            System.out.print("Input: " + infile + "\nOutput: " + outfile + "\n");
            System.out.print("importing file...\n");
            GraphHopper hopper = new GraphHopper();
            hopper.disableCHShortcuts();
            hopper.setInMemory(true);
            hopper.setOSMFile(infile);
            hopper.setGraphHopperLocation(outfile);
            hopper.setEncodingManager(new EncodingManager("car,foot"));
            hopper.importOrLoad();
            System.out.print("Done.\n");

        }catch( ParseException exp ){
            System.err.println("Parsing Error: " + exp.getMessage());
        }


    }
}
