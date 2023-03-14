/*
 * @author: Ma. Isabel Solano
 * @version 1.1, 14/03/23
 * 
 * AFN class as a Automata should be defined.
 * 
 */

package src;

import java.util.ArrayList;
import java.util.HashMap;

public class AFD {

    private ArrayList<State> States;
    private HashMap<Integer, Symbol> Symbols;
    private State initialState;
    
    /**
     * 
     * Simple test constructor
     * 
     * @param Symbols
     */
    public AFD(HashMap<Integer, Symbol> Symbols){
        this.Symbols = Symbols;
    }

    /**
     * Subset construction from NFA to DNA
     * 
     * @param Symbols
     * @param afn
     */
    public AFD(HashMap<Integer, Symbol> Symbols, AFN afn) {

    }

}
