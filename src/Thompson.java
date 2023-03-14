/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
 * 
 * Class in charge of building an NFA by Thompson's subset construction
 */

package src;

import java.util.ArrayList;
import java.util.HashMap;

public class Thompson {

    HashMap<Integer, Symbol> alphabet;
    int numState = 0;
    Symbol epsilon = new Symbol('ε');
    
    /**
     * Constructor
     * @param dict  Dictionary of symbols
     */
    public Thompson(HashMap<Integer, Symbol> dict) {
        this.alphabet = dict;
    }

    /**
     * 
     * Given the root of a tree, this method recursively
     * goes through all the nodes bottom up and left to right and
     * builds the according AFN of each node. 
     * 
     * @param node  Current node
     * @return      Current's node NFA
     */
    public AFN SubsetConstuction(TreeNode node) {
        // first node will be the root of the tree


        if (node.leftChild == null && node.righChild == null) {
            // Tree leafs

            // Get states ids
            int originState = 0;
            originState += numState;
            numState ++;
            int destinState = 0;
            destinState += numState;
            numState ++;

            // Create new states
            State oS = new State(originState, 1);
            State dS = new State(destinState, 3);

            // Create simple transition
            Transition t = new Transition(oS, node.value, dS);

            ArrayList<State> states = new ArrayList<>();
            states.add(oS);
            states.add(dS);

            ArrayList<Transition> transitions = new ArrayList<>();
            transitions.add(t);

            AFN currentAfn = new AFN(states, oS, alphabet, dS, transitions);
            return currentAfn;
        }

        else {
            if (node.righChild == null) {
                
                // Generate children's AFN before their own
                AFN Child = SubsetConstuction(node.leftChild);

                if (node.value.c_id == '*') {
                    // kleen clousure

                    // Get new states ids
                    int originState = 0;
                    originState += numState;
                    numState ++;
                    int destinState = 0;
                    destinState += numState;
                    numState ++;

                    // Create new states
                    State oS = new State(originState, 1);
                    State dS = new State(destinState, 3);

                    // Get values from children
                    // States
                    ArrayList<State> statesChild = Child.getStates();

                    ArrayList<State> states = new ArrayList<>();
                    states.add(oS);
                    states.addAll(statesChild);
                    states.add(dS);

                    // initial and final states position in arrays
                    int initialChildStatePos = 0;
                    int finalChildStatePos = -1;

                    for (int i = 0; i < statesChild.size(); i++ ){
                        if (statesChild.get(i).type == Type.Inicial) {
                            initialChildStatePos = i;
                            statesChild.get(i).setToTrans();
                        }
                        if (statesChild.get(i).type == Type.Final) {
                            finalChildStatePos = i;
                            statesChild.get(i).setToTrans();
                        }
                    }

                    // get new transitions
                    ArrayList<Transition> transitions = new ArrayList<>();
                    transitions.addAll(Child.getTransitions());
                    

                    // create new transitions
                    Transition skip = new Transition(oS, epsilon, dS);
                    Transition first = new Transition(oS, epsilon, statesChild.get(initialChildStatePos));
                    Transition last = new Transition(statesChild.get(finalChildStatePos), epsilon, dS);
                    Transition back = new Transition(
                        statesChild.get(finalChildStatePos)
                        , epsilon,
                        statesChild.get(initialChildStatePos));

                    transitions.add(skip);
                    transitions.add(first);
                    transitions.add(last);
                    transitions.add(back);

                    AFN currentAfn = new AFN(states, oS, alphabet, dS, transitions);
                    return currentAfn;

                } else if (node.value.c_id == '+') {
                    // positive clousure

                    // Get values from children
                    // States
                    ArrayList<State> statesChild = Child.getStates();

                    ArrayList<State> states = new ArrayList<>();
                    states.addAll(statesChild);

                    // initial and final states position in arrays
                    int initialChildStatePos = 0;
                    int finalChildStatePos = -1;

                    for (int i = 0; i < statesChild.size(); i++ ){
                        if (statesChild.get(i).type == Type.Inicial) {
                            initialChildStatePos = i;
                        }
                        if (statesChild.get(i).type == Type.Final) {
                            finalChildStatePos = i;
                        }
                    }

                    // get new transitions
                    ArrayList<Transition> transitions = new ArrayList<>();
                    transitions.addAll(Child.getTransitions());
                    
                    Transition back = new Transition(
                        statesChild.get(finalChildStatePos)
                        , epsilon,
                        statesChild.get(initialChildStatePos));

                    transitions.add(back);

                    AFN currentAfn = new AFN(
                        states, 
                        Child.getInitialState(), 
                        alphabet,
                        Child.getFinalState(),
                        transitions
                    );
                    return currentAfn;

                } else {
                    // question mark ('?')
                    
                    // Get new states ids
                    int originState = 0;
                    originState += numState;
                    numState ++;
                    int destinState = 0;
                    destinState += numState;
                    numState ++;

                    // Create new states
                    State oS = new State(originState, 1);
                    State dS = new State(destinState, 3);

                    // Get values from children
                    // States
                    ArrayList<State> statesChild = Child.getStates();

                    ArrayList<State> states = new ArrayList<>();
                    states.add(oS);
                    states.addAll(statesChild);
                    states.add(dS);

                    // initial and final states position in arrays
                    int initialChildStatePos = 0;
                    int finalChildStatePos = -1;

                    for (int i = 0; i < statesChild.size(); i++ ){
                        if (statesChild.get(i).type == Type.Inicial) {
                            initialChildStatePos = i;
                            statesChild.get(i).setToTrans();
                        }
                        if (statesChild.get(i).type == Type.Final) {
                            finalChildStatePos = i;
                            statesChild.get(i).setToTrans();
                        }
                    }

                    // get new transitions
                    ArrayList<Transition> transitions = new ArrayList<>();
                    transitions.addAll(Child.getTransitions());
                    

                    // create new transitions
                    Transition skip = new Transition(oS, epsilon, dS);
                    Transition first = new Transition(oS, epsilon, statesChild.get(initialChildStatePos));
                    Transition last = new Transition(statesChild.get(finalChildStatePos), epsilon, dS);

                    transitions.add(skip);
                    transitions.add(first);
                    transitions.add(last);

                    AFN currentAfn = new AFN(states, oS, alphabet, dS, transitions);
                    return currentAfn;
                }
        
            } else {
                AFN currentAfn;
                // Generate children's AFN before their own
                AFN Left = SubsetConstuction(node.leftChild);
                
                AFN Right = SubsetConstuction(node.righChild);

                // Generate own AFN from current node
                if (node.value.c_id == '|') {
                    // or

                    // Get new states ids
                    int originState = 0;
                    originState += numState;
                    numState ++;
                    int destinState = 0;
                    destinState += numState;
                    numState ++;

                    // Create new states
                    State oS = new State(originState, 1);
                    State dS = new State(destinState, 3);
                    
                    // Get values from children
                    // States
                    ArrayList<State> statesLeft = Left.getStates();
                    ArrayList<State> statesRight = Right.getStates();

                    ArrayList<State> states = new ArrayList<>();
                    states.add(oS);
                    states.addAll(statesLeft);
                    states.addAll(statesRight);
                    states.add(dS);
                    
                    // initial and final states position in arrays
                    int initialLeftStatePos = 0;
                    int initialRightStatePos = 0;
                    int finalLeftStatePos = -1;
                    int finalRightStatePos = -1;

                    for (int i = 0; i < statesLeft.size(); i++ ){
                        if (statesLeft.get(i).type == Type.Inicial) {
                            initialLeftStatePos = i;
                            statesLeft.get(i).setToTrans();
                        }
                        if (statesLeft.get(i).type == Type.Final) {
                            finalLeftStatePos = i;
                            statesLeft.get(i).setToTrans();
                        }
                    }

                    for (int i = 0; i < statesRight.size(); i++ ){
                        if (statesRight.get(i).type == Type.Inicial) {
                            initialRightStatePos = i;
                            statesRight.get(i).setToTrans();
                        }
                        if (statesRight.get(i).type == Type.Final) {
                            finalRightStatePos = i;
                            statesRight.get(i).setToTrans();
                        }
                    }

                    // get new transitions
                    ArrayList<Transition> transitions = new ArrayList<>();
                    transitions.addAll(Left.getTransitions());
                    transitions.addAll(Right.getTransitions());

                    // create new transitions
                    
                    Transition beginningOrOp1 = new Transition(oS, epsilon, statesLeft.get(initialLeftStatePos));
                    Transition beginningOrOp2 = new Transition(oS, epsilon, statesRight.get(initialRightStatePos));

                    Transition endOrOp1 = new Transition(statesLeft.get(finalLeftStatePos), epsilon, dS);
                    Transition endOrOp2 = new Transition(statesRight.get(finalRightStatePos), epsilon, dS);

                    transitions.add(beginningOrOp1);
                    transitions.add(beginningOrOp2);
                    transitions.add(endOrOp1);
                    transitions.add(endOrOp2);

                    currentAfn = new AFN(states, oS, alphabet, dS, transitions);


                } else {
                    // concatenation

                    // get first and last state
                    State firState = Left.getInitialState();
                    State lastState = Right.getFinalState();

                    // Get values from children
                    // States
                    ArrayList<State> statesLeft = Left.getStates();
                    ArrayList<State> statesRight = Right.getStates();

                    ArrayList<State> states = new ArrayList<>();
                    states.addAll(statesLeft);
                    states.addAll(statesRight);

                    // initial and final states position in arrays
                    int initialRightStatePos = 0;
                    int finalLeftStatePos = -1;

                    for (int i = 0; i < statesLeft.size(); i++ ){
                        if (statesLeft.get(i).type == Type.Final) {
                            finalLeftStatePos = i;
                            statesLeft.get(i).setToTrans();
                        }
                    }

                    for (int i = 0; i < statesRight.size(); i++ ){
                        if (statesRight.get(i).type == Type.Inicial) {
                            initialRightStatePos = i;
                            statesRight.get(i).setToTrans();
                        }
                    }

                    // get new transitions
                    ArrayList<Transition> transitions = new ArrayList<>();
                    transitions.addAll(Left.getTransitions());
                    transitions.addAll(Right.getTransitions());

                    // create new transition 

                    Transition dot = new Transition(
                        statesLeft.get(finalLeftStatePos), 
                        epsilon, 
                        statesRight.get(initialRightStatePos));

                    transitions.add(dot);

                    currentAfn = new AFN(states, firState, alphabet, lastState, transitions);
                }

                
                return currentAfn;
            }
            
        }
        
        // The last AFN to be returned will be the root's, therefore, the expression's AFN

    }

}
