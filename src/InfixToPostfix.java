/*
 * @Author Maria Isabel Solano
 * @version 0.1, 24/02/23
 * 
 * Infix To Postfix class that recieves a String with a regular 
 * expression and a HashMap with the accepted alphabet, and 
 * returns a Stack with the poxtfixed regular expression. 
 */

package src;

import java.util.*;

public class InfixToPostfix {

    HashMap<Integer, Symbol> dict = new HashMap<>();

    /**
     * Constructor
     * 
     * @param dict - Dictionary with all the symbol from the alphabet
     */
    public InfixToPostfix() {

        Symbol epsilon = new Symbol('ε');
        dict.put(epsilon.id, epsilon);
        
    }

    
    /*
     * Convert is the method that is called to do all the work. 
     * 
     * @params String           The input string that contains the regex
     * @return Stack<Symbol>    A stack with the regex on a postfix order
     */
    public Stack<Symbol> convert(String infix) {

        Stack<Symbol> stack = new Stack<>();
        Stack<Symbol> postfix = new Stack<>();

        ArrayList<Symbol> input = transformToSymbols(infix);

        // Adds concat '.' if the user didn't specify
        input = concatAdd(input);

        String test = "";
        for (Symbol s: input) {
            test += String.valueOf(s.c_id);
        }

        // DELETE LATER
        System.out.println(test);

        // Checks if the letters belong to the alphabet
        for ( int i = 0; i < input.size(); i++) {
            Symbol c = input.get(i);

            if ( Character.isDigit(c.c_id) || Character.isLetter(c.c_id)) {
                postfix.push(c);
            } else if (c.c_id == '(') {
                stack.push(c);
            } else if (c.c_id == ')') {
                while (!stack.empty() && stack.peek().c_id != '(') {
                    postfix.push(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek().c_id != '(') {
                    throw new IllegalArgumentException("Invalid expression");
                } else {
                    stack.pop();
                }
            } else {
                while (!stack.empty() && precedence(c.c_id) <= precedence(stack.peek().c_id)) {
                    postfix.push(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            postfix.push(stack.pop());
        }

        return postfix;
    }

    /*
     * Checks that all of the characters that are in the regex belong
     * to the Alphabet of the language.  
     * 
     * @params String       The input string that contains the regex
     * @ArrayList<Symbol>   A dynamic array that contains all of the symbols that 
     *                      belong to the regular expression. 
     */
    private ArrayList<Symbol> transformToSymbols(String ogInput) {

        ArrayList<Symbol> input = new ArrayList<>();

        // for (int i = 0; i < ogInput.length(); i ++) {

        //     int j = ogInput.charAt(i);
            
        //     if ( ogInput.charAt(i) == '?' || ogInput.charAt(i) == '*' ||
        //          ogInput.charAt(i) == '+' || ogInput.charAt(i) == '.' ||
        //          ogInput.charAt(i) == '|' || ogInput.charAt(i) == '(' ||
        //          ogInput.charAt(i) == ')') {
                
        //         Symbol temp = new Symbol(ogInput.charAt(i));
        //         input.add(temp);
        //     } else if (dict.containsKey(j)) {
        //         input.add(dict.get(j));
        //     } else {
        //         //The input contains unrecognized Symbols
        //         System.out.println("No existe");
        //     }
        // }

        for (int i = 0; i < ogInput.length(); i++ ){

            Symbol temp = new Symbol(ogInput.charAt(i));

            // Checks if it already exists on the alphabet
            if (dict.containsKey(temp.id)) {
                // It is already part of the dictionary, it adds it to de array
                input.add(dict.get(temp.id));
            } else {
                // Not already part of the dictionary
                if (isOperator(temp)) {
                    // Adds to the array
                    input.add(temp);
                }
                else {
                    // Adds to the array
                    input.add(temp);

                    // Adds to the dictionary
                    dict.put(temp.id, temp);
                }
            }

        }

        return input;
    }

    /*
     * If the user doesn't include a concat, the program adds it so that
     * way is easier to work with the reagular expression. 
     * 
     * @param ArrayList<Symbol> input   The regular expression already converted
     *                                  into a dynamic array
     * @return ArrayList<Symbols> temp  A similar array to imput but with
     *                                  all the concats
     */
    private ArrayList<Symbol> concatAdd(ArrayList<Symbol> input) {

        Symbol concat = new Symbol('.');

        ArrayList<Symbol> temp = new ArrayList<>();

        // copying the array
        for (int i = 0; i < input.size() ; i++) {
            temp.add(input.get(i));
        }

        // adding the '.'s
        int bias = 1;  
        for (int i = 0; i < input.size() - 1 ; i ++) {
            // if it is an operator
            if ( isOperator(input.get(i)))  {
                if ( input.get(i).c_id == '*' || input.get(i).c_id == '+' ||
                     input.get(i).c_id == ')' || input.get(i).c_id == '?') {
                    if ( !isOperator(input.get(i + 1)) || 
                         input.get(i + 1).c_id == '('
                        ) {

                        temp.add(i + bias, concat);
                        bias ++;
    
                    }
                }

            // if it is a normal symbol
            } else if ( !isOperator(input.get(i))) {
                if ( !isOperator(input.get(i + 1))) {

                    temp.add(i + bias, concat);
                    bias ++;

                } else if (input.get(i + 1).c_id == '(') {
                    temp.add(i + bias, concat);
                    bias ++;
                }
            }
        }

        return temp;
    }

    /*
     * Method to determine if the current symbols is an 
     * operator or not
     * 
     * @warning This method may be deprecated in the near future
     * 
     * @param Symbols s     The current symbol
     * @return boolean res  True of false wheather the symbols is an 
     *                      operator or not
     */
    private boolean isOperator(Symbol s) {
        boolean res = false;

        if (s.c_id == '?' || s.c_id == '.' || 
            s.c_id == '+' || s.c_id == '*' ||
            s.c_id == '(' || s.c_id == ')' ||
            s.c_id == '|' ) {
                
                res = true;
            }

        return res;
    }

    /*
     * Method to determine the precedence of an operator
     * 
     * @param char operator The current operator
     * @retunr int r        The level of precedence of that operator
     */
    private int precedence(char operator) {
        int r = -1;
        if (operator == '?') {
            r = 3;
        } else if (operator == '|') {
            r = 2;
        } else if (operator == '*') {
            r = 3;
        } else if (operator == '+') {
            r = 3;
        } else if (operator == '.') {
            r = 1;
        }

        return r;
    }

    public HashMap<Integer, Symbol> getDic() {
        return this.dict;
    }
}
