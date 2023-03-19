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
     * Basic constructor
     */
    public GraphToFile() {

    }

    /**
     * With the given paramethers, formats all of the AFN's info
     * into a TXT for it to be read later. 
     * 
     * @param outputFileName    name of the new TXT file
     * @param afn               All of the afn with its informaiton
     * @return                  Instance of GraphToFile
     */
    public void generateFile(String outputFileName, AFN afn) {

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
            writer.write("\n\tinitial [label = \"\", shape=none, height = .0, width = .0]");

            writer.write("\n");
            
            // Initial state 
            writer.write("\n\tinitial -> " + afn.getInitialState());

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

    public void generateFile(String outputFileName, AFD afd) {

        try {

            File file = new File(outputFileName);
            FileWriter writer = new FileWriter(file);

            // header
            writer.write("digraph AFN\n{");
            writer.write("\n\trankdir=\"LR\";");

            // parese through AFN
            // -States
            for (State s: afd.getStates()) {
                if (s.getType() == Type.Final) writer.write("\n\t"+s.toString()+" [shape=doublecircle];");
                else if (s.getType() == Type.Inicial) writer.write("\n\t"+s.toString()+" [shape=circle, color=gray28];");
                else writer.write("\n\t"+s.toString()+" [shape=circle];");
            }
            writer.write("\n\tinitial [label = \"\", shape=none, height = .0, width = .0]");

            writer.write("\n");
            
            // Initial state 
            writer.write("\n\tinitial -> " + afd.getInitialState());

            writer.write("\n");

            // -Transitions
            for (Transition t: afd.getTransitions()) {
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