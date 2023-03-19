/*
 * @author: Ma. Isabel Solano
 * @version 2, 19/03/23
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
 
    public boolean Simulate(String r) {

        ArrayList<State> currentState = new ArrayList<>();
        currentState.add(this.initialState);
        currentState = eClosure(currentState);

        for (int c = 0; c < r.length(); c++) {
            
            Symbol temp = new Symbol(r.charAt(c));
            currentState = move(currentState, temp);
            currentState = eClosure(currentState);
        }

        if(currentState.contains(FinalState)) return true;
        return false;
    }

    ArrayList<State> move(ArrayList<State> current, Symbol s) {

        ArrayList<State> S_states = new ArrayList<>();

        for (int i = 0; i < current.size(); i++) {
            // Check the given symbol transitions for each the current set of states

            State temp = current.get(i);

            for (int j = 0; j < transitions.size() ; j++ ) {
                if (transitions.get(j).getOriginState().id == temp.id) {
                    // check for all transitions that start begin with that state
                    if (transitions.get(j).symbol.c_id == s.c_id) {
                        // if it has an epsilon transition, it ads the final state to Estates

                        // but first it checks if it was already in the list so it won't repeat states
                        if (!S_states.contains(transitions.get(j).getFinalState())) S_states.add(transitions.get(j).getFinalState());
                    }
                }
            }

        }

        return S_states;
    }

    ArrayList<State> eClosure(ArrayList<State> current) {

        ArrayList<State> E_states = new ArrayList<>();
        for (State s: current) E_states.add(s); // copy first symbols

        boolean keepGoing = true;
        while (keepGoing) {
            for (int i = 0; i < current.size(); i++) {

                // Check the epsilon transitions for each the current set of states
                State temp = current.get(i);
    
                for (int j = 0; j < transitions.size() ; j++ ) {
                    if (transitions.get(j).getOriginState().id == temp.id) {
                        // check for all transitions that start begin with that state
                        if (transitions.get(j).symbol.c_id == 'ε') {
                            // if it has an epsilon transition, it ads the final state to Estates
    
                            // but first it checks if it was already in the list so it won't repeat states
                            if (!E_states.contains(transitions.get(j).getFinalState())) E_states.add(transitions.get(j).getFinalState());
                        }
                    }
                }
    
            }

            // Determine weather we should keep going or not
            if (E_states.equals(current)) {
                // There are no other epsilon transitions to be reached
                keepGoing = false;
            } else {
                // Rewrite current
                current.clear();
                for (State s: E_states) current.add(s);
            }

        }


        return E_states;
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
        String ret = "\nSymbols: " + Symbols.values().toString();

        ret += "\nStates: " + States.toString();

        ret += "\nInitial State : " + this.initialState.toString();

        ret += "\nFinal State : " + this.FinalState.toString();

        ret += "\nTransitions: " + transitions.toString();
        // for (int i = 0; i < transitions.size(); i ++)  {
        //     String temp = "[";
        //     int originId = transitions.get(i).getOriginState().getId();
        //     int endID = transitions.get(i).getFinalState().getId();
        //     char symbol = transitions.get(i).symbol.c_id;
        //     temp += Integer.toString(originId) + " --" + symbol + "-> "+ Integer.toString(endID);
        //     temp += "]";
        //     ret += temp;
        // }

        return ret;
    }

}
