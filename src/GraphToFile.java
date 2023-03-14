/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
 * 
 * Class in charge of converting Automatas into TXT files to be read
 * by dot later. 
 */

package src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GraphToFile {

    /**
     * Simple test to, creates a TXT file with The words 
     * "Hello, world!"
     * 
     * @param outputFileName    name of the new TXT file
     * @return                  Instance of GraphToFile
     */
    public GraphToFile(String outputFileName) {

        try {

            File file = new File(outputFileName);
            FileWriter writer = new FileWriter(file);

            writer.write("Hello, world!");
            writer.write("\n\tHello, world!");

            writer.close();


        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
            e.printStackTrace();
        }

    }

    /**
     * With the given paramethers, formats all of the AFN's info
     * into a TXT for it to be read later. 
     * 
     * @param outputFileName    name of the new TXT file
     * @param afn               All of the afn with its informaiton
     * @return                  Instance of GraphToFile
     */
    public GraphToFile(String outputFileName, AFN afn) {

        try {

            File file = new File(outputFileName);
            FileWriter writer = new FileWriter(file);

            // header
            writer.write("digraph AFN\n{");
            writer.write("\n\trankdir=\"LR\";");

            // parese through AFN
            // -States
            for (State s: afn.getStates()) {
                if (s.getType() == Type.Final) writer.write("\n\t"+s.toString()+" [shape=doublecircle];");
                else if (s.getType() == Type.Inicial) writer.write("\n\t"+s.toString()+" [shape=circle, color=gray28];");
                else writer.write("\n\t"+s.toString()+" [shape=circle];");
            }

            writer.write("\n");

            // -Transitions
            for (Transition t: afn.getTransitions()) {
                writer.write(
                    "\n\t" +
                    t.getOriginState().toString() +
                    " -> " +
                    t.getFinalState().toString() +
                    " [label=" +
                    t.getSymbol().c_id +
                    "];"
                );
            }

            // close bracket
            writer.write("\n}");


            writer.close();


        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
            e.printStackTrace();
        }

    }
}