/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
 * 
 * View class to interact with the user. 
 */

package src;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Vista {
    
    Scanner scan = new Scanner( new InputStreamReader(System.in, Charset.defaultCharset()));

    public Vista() {

    }

    /** 
     * Simple welcome and instructions for users to read
     * 
     */
    public void Welcome() {
        System.out.println("\n___________________________________________");;
        System.out.println("___________________________________________");;
        System.out.println("Welcome");
        System.out.println("Please follow the following instrucctions... \n");
    }

    /**
     * Gets the users input and verifies if its correct.
     * @return  A string with the user's regular expression
     */
    public String getRegex() {

        System.out.println("Regular expression: ");
        String input = scan.nextLine();

        // remove spaces and change to epsilon
        input = input.replace(' ', 'ε');
        
        if (checkCorrectness(input)) {
            
            return input;
            // Returns the user's regular expression
        }
        else {
            return null;
            // It was an invalid regular expression
        }

    }

    public String getR() {

        System.out.println("\nExpression to simulate: ");
        String input = scan.nextLine();
        
        return input;

    }

    /**
     * Checks if the user's regular expression is correct by 5 standards
     * 1. Starts witha valid character and no operator
     * 2. Has a valid sequence of operators, and there are no operators close to one another
     * 3. Has no empty parenthesis at the end
     * 4. Has the same amount of openening parenthesis as closing parenthesis
     * 
     * @param input User's input
     * @return      True if it's okay, False if it's not
     */
    private boolean checkCorrectness(String input){
        boolean correct = true;

        if (input.charAt(0) == '+' || input.charAt(0) == '*' || 
            input.charAt(0) == '|' || input.charAt(0) == '.' ||
            input.charAt(0) == '?' || input.charAt(0) == ')') {
                // begins with invalid operator (1)
                printErrorMssg(0, 1);
                return false;
            }

        int openParenthesis = 0;
        int closeParenthesis = 0;
        for (int i = 0; i < input.length() - 1; i++) {

            if (input.charAt(i) == '|' || input.charAt(i) == '.') {
                if (input.charAt(i + 1) == '|' || input.charAt(i + 1) == '+' ||
                    input.charAt(i + 1) == '*' || input.charAt(i + 1) == '?' ||
                    input.charAt(i + 1) == ')' || input.charAt(i + 1) == '.') {
                        // Invalid sequence of operators (2)
                        printErrorMssg(i, 2);
                        return false;
                }
            }

            // *, +, ? accept anything after them


            if (input.charAt(i) == '(' && input.charAt(i + 1) == ')') {
                // empty parenthesis (3)
                printErrorMssg(i, 3);
                return false;
            }

            
            if (input.charAt(i) == '(') openParenthesis ++;
            if (input.charAt(i) == ')') closeParenthesis ++;

        }

        if (input.charAt(input.length() - 1) == ')') closeParenthesis ++;

        if (input.charAt(input.length() - 1) == '(') {
            // empty last parenthesis (4)
            printErrorMssg(input.length() - 1, 4);
            return false;
        }

        if (openParenthesis != closeParenthesis) {
            // Inbalanced number of parenthesis (5)
            printErrorMssg(input.length(), 5);
            return false;
        }

        return correct;
    }

    /**
     * Prints an arror message and the position of the error
     * 
     * @param pos   Position of the error in the expression
     * @param err   Type of error
     */
    private void printErrorMssg(int pos, int err) {
        if (err == 1) {
            System.out.println("^");
            System.out.println("ERROR AT POSITION 0: Cannot start a regular expresion with a operator\n");

        } else if (err == 2) {
            for (int i = 0; i < pos; i ++ ) {
                System.out.print("~");
            }
            System.out.print("^\n");
            System.out.println("ERROR AT POSITION " + Integer.toString(pos) + ": Invalid sequence of operators.");
            System.out.println("Operators such as '|' and '.' must be followed up by another valud sub expression\n");
        
        } else if (err == 3) {
            for (int i = 0; i < pos; i ++ ) {
                System.out.print("~");;
            }
            System.out.print("^\n");
            System.out.println("ERROR AT POSITION " + Integer.toString(pos) + ": Invalid empty parenthesis\n");
        } else if (err == 4) {
            for (int i = 0; i < pos; i ++ ) {
                System.out.print("~");
            }
            System.out.print("^\n");
            System.out.println("ERROR AT POSITION " + Integer.toString(pos) + ": Invalid empty parenthesis at the end of the input");
            System.out.println("Parenthesis can be left open, but the MUST have a valid subexpression after the opening bracket\n");
        } else if (err == 5) {
            for (int i = 0; i < pos; i ++ ) {
                System.out.print("~");
            }
            System.out.print("^\n");
            System.out.println("ERROR AT POSITION " + Integer.toString(pos) + ": Inbalanced number of opening and closing parenthesis\n");
        }
    }
}
