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

        Vista vis = new Vista();
        
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

            // From postfix transform to Tree
            Tree regexT = new Tree(stack);
            //System.out.println("\nTree");
            //regexT.printTree(regexT.getRoot());

            // AFN
            Thompson thompson = new Thompson(alphabet);
            AFN afn = thompson.SubsetConstuction(regexT.getRoot());

            System.out.println(afn);

            // AFN graph
            String graphTxtFileName = "output/AFN.txt";
            String graphJpgFileName = "output/AFN.jpg";
            GraphToFile gtf = new GraphToFile(graphTxtFileName, afn);
            TerminalCommand tc = new TerminalCommand();
            tc.GraphAFN(graphTxtFileName, graphJpgFileName);
        } 



    }

}