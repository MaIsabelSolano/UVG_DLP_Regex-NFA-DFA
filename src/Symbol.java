/*
 * @author: Ma. Isabel Solano
 * @version 2, 19/03/23
 * 
 * Symbol class that stores the char and int value of each symbol
 * 
 */

package src;
public class Symbol {
    
    public int id; //ASCII
    public char c_id;

    /**
     * Symbol class constructor
     * 
     * @param a Symbol 
     */
    public Symbol(char a){

        this.c_id = a;
        this.id = a;
    }

    /**
     * Easy way to know if the current symbol si an operator
     * or part of the alphabet
     * @return  If the symbol is an operator or not. 
    */
    public boolean isOperator(){
        boolean operator = false;
        if (c_id == '|' || c_id == '?' ||
            c_id == '+' || c_id == '*' ||
            c_id == '(' || c_id == ')' ||
            c_id == '.') {
                operator = true;
        }

        return operator;
    }

    @Override
    public String toString() {
        return "" + c_id;
    }
}
