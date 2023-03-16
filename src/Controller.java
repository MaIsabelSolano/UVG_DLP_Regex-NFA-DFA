/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
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

            System.out.println("\nDictionary:");
            for (int i: alphabet.keySet()) {
                System.out.print(alphabet.get(i).c_id);
            }

            System.out.println("\nStack: ");
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

            System.out.println(afn);

            // AFN graph
            graphTxtFileName = "output/AFN.txt";
            graphJpgFileName = "output/AFN.jpg";
            gtf.generateFile(graphTxtFileName, afn);
            tc.GraphAFN(graphTxtFileName, graphJpgFileName);

            // --- AFD ----

            // AFN to AFD
            AFD afd_trans = new AFD(alphabet, afn);
            System.out.println(afd_trans);

            // AFD_trans graph
            graphTxtFileName = "output/AFD_trans.txt";
            graphJpgFileName = "output/AFD_trans.jpg";
            gtf.generateFile(graphTxtFileName, afd_trans);
            tc.GraphAFN(graphTxtFileName, graphJpgFileName);

            // AFD_trans simulation
            String simulate1 = vis.getR();
            if (afd_trans.Simulate(simulate1)) System.out.println("Belongs!");
            else System.out.println("Doesn't belong!");

            // AFD Direct construction
            Stack<Symbol> augmentedStack = new Stack<>();
            for (Symbol s: stack) augmentedStack.add(s); // copy stack
            Symbol concat = new Symbol('.');
            Symbol enSymbol = new Symbol('#');
            augmentedStack.add(enSymbol);
            augmentedStack.add(concat);
            

            System.out.println("\nStack: ");
            for (Symbol s: augmentedStack) {
                System.out.print(s.c_id);
            }
            System.out.println("");

            SintacticTree sintacticTree = new SintacticTree(augmentedStack);
            sintacticTree.printTree(sintacticTree.getRoot());
            AFD afd_direct = new AFD(alphabet, sintacticTree);
            System.out.println(afd_direct);

            // AFD_direct graph
            graphTxtFileName = "output/AFD_direct.txt";
            graphJpgFileName = "output/AFD_direct.jpg";
            gtf.generateFile(graphTxtFileName, afd_direct);
            tc.GraphAFN(graphTxtFileName, graphJpgFileName);


        } 



    }

}