/*
 * @author: Ma. Isabel Solano
 * @version 2, 19/03/23
 * 
 * Stores a transition information
 */

package src;
public class Transition {
    public State originState;
    public State finalState;
    public Symbol symbol;

    /**
     * 
     * Constructor 
     * 
     * @param originState   Beginning state of the transition
     * @param symbol        Symbol with wich the transitions is being made
     * @param finalState    Final state of the transition
     */
    public Transition( State originState, Symbol symbol, State finalState) {
        this.originState = originState;
        this.symbol = symbol;
        this.finalState = finalState;
    }

    public boolean equals(Transition t) {
        boolean res = false;
        if (t.originState.id == this.originState.id) {
            if (t.symbol.c_id == this.symbol.c_id) {
                if (t.finalState.id == this.finalState.id) {
                    res = true;
                }
            }
        }
        return res;
    }

    /* Getters */
    public State getFinalState() {
        return finalState;
    }

    public State getOriginState() {
        return originState;
    }
    
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return "["+originState.toString() + " --" + symbol.c_id + "-> " + finalState.toString() +" ]";
    }

}
