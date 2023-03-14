/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
 * 
 * AFN class as a Automata should be defined.
 * 
 */

package src;

import java.util.ArrayList;
import java.util.HashMap;

public class AFN extends Automata{

    private ArrayList<State> States;
    private State FinalState;
    private HashMap<Integer, Symbol> Symbols;
    private State initialState;
    private ArrayList<Transition> transitions;

    /**
     * 
     * Simple test constructor
     * 
     * @param Symbols   Dictionary of symbols of the languages alphabet
     */
    public AFN(HashMap<Integer, Symbol> Symbols) {
        this.Symbols = Symbols;
    }
    
    /**
     * 
     * Constructor with all of the AFN information
     * 
     * @param States        Set of all the automata states
     * @param initialState  Initial State
     * @param Symbols       Dictionary of symbols of the languages alphabet
     * @param finalState    End State
     * @param transitions   Set of all the transitions that build the automata
     */
    public AFN(
        ArrayList<State> States, 
        State initialState, 
        HashMap<Integer, Symbol> Symbols,
        State finalState,
        ArrayList<Transition> transitions ) {

        this.States = States;
        this.FinalState = finalState;
        this.Symbols = Symbols;
        this.initialState = initialState;
        this.transitions = transitions;
    }

    /* Getters */
    public ArrayList<State> getStates() {
        return this.States;
    }

    public State getFinalState() {
        return FinalState;
    }

    public HashMap<Integer, Symbol> getSymbols() {
        return Symbols;
    }

    public State getInitialState() {
        return initialState;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        String ret = "AFN\nSymbols: ";

        for (Integer i: Symbols.keySet()) {
            ret += Symbols.get(i).c_id + ", ";
        }

        ret += "\nStates: {";
        for (State s: States) {
            ret += Integer.toString(s.id) + ", ";
        }

        ret += "}\nTransitions: ";
        for (int i = 0; i < transitions.size(); i ++)  {
            String temp = "[";
            int originId = transitions.get(i).getOriginState().getId();
            int endID = transitions.get(i).getFinalState().getId();
            char symbol = transitions.get(i).symbol.c_id;
            temp += Integer.toString(originId) + " --" + symbol + "-> "+ Integer.toString(endID);
            temp += "]";
            ret += temp;
        }

        return ret;
    }

}
