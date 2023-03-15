/*
 * @author: Ma. Isabel Solano
 * @version 1.1, 14/03/23
 * 
 * AFN class as a Automata should be defined.
 * 
 */

package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Stack;

public class AFD {

    private ArrayList<State> States;
    private HashMap<Integer, Symbol> Symbols;
    private State initialState;
    private ArrayList<Transition> trans;
    
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

        // Delete epsilon from Symbol dictionary
        Symbol epsilon = new Symbol('ε');
        Symbols.remove(epsilon.id);

        // Array list that 
        ArrayList<ArrayList<State>> C_states = new ArrayList<>();
        Stack<ArrayList<State>> unverified_States = new Stack<>();

        ArrayList<State> currentStates = new ArrayList<>(); 
        currentStates.add(afn.getInitialState()); // Adds the inital state to de current states
        currentStates = eClosure(afn, currentStates); // Get first state
        unverified_States.add(currentStates); 
        C_states.add(currentStates);

        boolean verifier = true; // Indicates if new states have been added to C_states
        // The algorithm stops when no new states are being added, inicating that 
        while (verifier) {

            // Verify if there are still unverified states    
            if (!unverified_States.isEmpty()) {
                // if yes, pop one from unverified_States
                currentStates = unverified_States.pop();
            } else {
                // if not, stop
                verifier = false;
                // The algorithm is done
            }

            // DELETE LATER
            String tempString = "";
            for (State s: currentStates) tempString += s.toString();
            System.out.println("Current state: " + tempString);

            // perform for each symbol in the alphabet
            for (int currentSymbol: Symbols.keySet()) {

                ArrayList<State> moveRes = move(afn, currentStates, Symbols.get(currentSymbol));

                // DELETE LATER
                tempString = "";
                for (State s: currentStates) tempString += s.toString();
                System.out.println("Current state (after "+ Symbols.get(currentSymbol).c_id +"): " + tempString);


                // e-clousure
                moveRes = eClosure(afn, moveRes);

                // DELETE LATER
                tempString = "";
                for (State s: currentStates) tempString += s.toString();
                System.out.println("Current state (after epsilon): " + tempString);


                // rearange
                moveRes.sort(Comparator.naturalOrder());

                // verify if we already have this state
                if (!C_states.contains(moveRes) && !moveRes.isEmpty()) {
                    C_states.add(moveRes);
                    unverified_States.add(moveRes);
                }

            }

        }

        // Print DELETE LATER
        for (int i = 0; i < C_states.size(); i++) {
            String temp = "{";
            for (State s : C_states.get(i)) temp += s.toString();
            temp += "}";
            System.out.println(temp);
        }

        // Transform C_states to new simple states and determines which states are
        // final states

    }

    ArrayList<State> eClosure(AFN afn, ArrayList<State> current) {

        ArrayList<State> E_states = new ArrayList<>();
        for (State s: current) E_states.add(s); // copy first symbols

        for (int i = 0; i < current.size(); i++) {

            // System.out.println("checking " + current.get(i).toString());
            // Check the epsilon transitions for each the current set of states

            State temp = current.get(i);
            ArrayList<Transition> transitions = afn.getTransitions();

            for (int j = 0; j < transitions.size() ; j++ ) {
                if (transitions.get(j).getOriginState().id == temp.id) {
                    // System.out.println(transitions.get(j));
                    // check for all transitions that start begin with that state
                    if (transitions.get(j).symbol.c_id == 'ε') {
                        // System.out.println("Adds " + transitions.get(j).getFinalState().toString());
                        // if it has an epsilon transition, it ads the final state to Estates

                        // but first it checks if it was already in the list so it won't repeat states
                        if (!E_states.contains(transitions.get(j).getFinalState())) E_states.add(transitions.get(j).getFinalState());
                    }
                }
            }

        }


        return E_states;
    }

    ArrayList<State> move(AFN afn, ArrayList<State> current, Symbol s) {

        ArrayList<State> S_states = new ArrayList<>();

        for (int i = 0; i < current.size(); i++) {
            // Check the given symbol transitions for each the current set of states

            State temp = current.get(i);
            ArrayList<Transition> transitions = afn.getTransitions();

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

    ArrayList<State> combine(ArrayList<State> e_c, ArrayList<State> m) {

        ArrayList<State> states = new ArrayList<>();

        for (State s: e_c) states.add(s);

        for (State s: m) {
            if (!states.contains(s)) states.add(s);
        }

        // sort array
        states.sort(Comparator.naturalOrder()); // This way they will alwasy have the same order

        return states;
    }

    

}
