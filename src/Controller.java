/*
 * @author: Ma. Isabel Solano
 * @version 2, 19/03/23
 * 
 * Main class to interact with the program. 
 */

package src;

import java.util.HashMap;
import java.util.Stack;

public class Controller {
    public static void main(String[] args) {

        // classes
        Vista vis = new Vista();
        TerminalCommand tc = new TerminalCommand();
        GraphToFile gtf = new GraphToFile();
        
        // important attributes
        String graphTxtFileName;
        String graphJpgFileName;


        // Welcome the user
        vis.Welcome();

        // Get regex from user
        String regex = vis.getRegex();

        if (regex != null) {
            // The user gave a valid regular expression as input so the
            // program can continue normally

            // Transform to postfix
            InfixToPostfix itp = new InfixToPostfix();

            Stack<Symbol> stack = itp.convert(regex);

            HashMap<Integer, Symbol> alphabet = itp.getDic();

            System.out.println("\nPostfix: ");
            for (Symbol s: stack) {
                System.out.print(s.c_id);
            }
            System.out.println("");

            // From postfix transform to Tree
            Tree regexT = new Tree(stack);
            //System.out.println("\nTree");
            //regexT.printTree(regexT.getRoot());

            // AFN
            Thompson thompson = new Thompson(alphabet);
            AFN afn = thompson.SubsetConstuction(regexT.getRoot());
            System.out.println(" \n*** NFA ***");
            System.out.println(afn);

            // AFN graph
            graphTxtFileName = "output/AFN.txt";
            graphJpgFileName = "output/AFN.jpg";
            gtf.generateFile(graphTxtFileName, afn);
            tc.GraphAFN(graphTxtFileName, graphJpgFileName);

            // --- AFD ----

            // AFN to AFD
            AFD afd_trans = new AFD(alphabet, afn);
            System.out.println("\n*** NFA to DFA ***");
            System.out.println(afd_trans);

            // AFD_trans graph
            graphTxtFileName = "output/AFD_trans.txt";
            graphJpgFileName = "output/AFD_trans.jpg";
            gtf.generateFile(graphTxtFileName, afd_trans);
            tc.GraphAFN(graphTxtFileName, graphJpgFileName);

            // AFD Direct construction
            Stack<Symbol> augmentedStack = new Stack<>();
            for (Symbol s: stack) augmentedStack.add(s); // copy stack
            Symbol concat = new Symbol('.');
            Symbol enSymbol = new Symbol('#');
            augmentedStack.add(enSymbol);
            augmentedStack.add(concat);
            
            System.out.println("\n*** DFA Direct construction ***");
            System.out.println("\nAugmented postfix: ");
            for (Symbol s: augmentedStack) {
                System.out.print(s.c_id);
            }
            System.out.println("");

            System.out.println("\nSyntactic Tree");
            SintacticTree sintacticTree = new SintacticTree(augmentedStack);
            sintacticTree.printTree(sintacticTree.getRoot());
            AFD afd_direct = new AFD(alphabet, sintacticTree);
            System.out.println(afd_direct);

            // AFD_direct graph
            graphTxtFileName = "output/AFD_direct.txt";
            graphJpgFileName = "output/AFD_direct.jpg";
            gtf.generateFile(graphTxtFileName, afd_direct);
            tc.GraphAFN(graphTxtFileName, graphJpgFileName);

            // AFD minimization
            AFD_minimizer afdMin = new AFD_minimizer();
            AFD afd_minimized = afdMin.minimize(afd_trans);
            System.out.println("\n*** Minimized DFA (NFA to DFA) ***");
            System.out.println(afd_minimized);

            // AFD_trans minimized graph
            graphTxtFileName = "output/AFD_min.txt";
            graphJpgFileName = "output/AFD_min.jpg";
            gtf.generateFile(graphTxtFileName, afd_minimized);
            tc.GraphAFN(graphTxtFileName, graphJpgFileName);

            // --- Simulation
            String simulate1 = vis.getR();

            // AFN 
            if (afn.Simulate(simulate1)) System.out.println("AFD: Belongs!");
            else System.out.println("AFN: Doesn't belong!");

            // AFN to AFD
            if (afd_trans.Simulate(simulate1)) System.out.println("AFD (subset): Belongs!");
            else System.out.println("AFD (subset): Doesn't belong!");

            // Direct AFD 
            if (afd_direct.Simulate(simulate1)) System.out.println("AFD (Direct): Belongs!");
            else System.out.println("AFD (Direct): Doesn't belong!");

            // Minimidez AFD 
            if (afd_direct.Simulate(simulate1)) System.out.println("AFD (Min): Belongs!");
            else System.out.println("AFD (Min): Doesn't belong!");


        } 



    }

}