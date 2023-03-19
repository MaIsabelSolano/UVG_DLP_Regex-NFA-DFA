/*
 * @author: Ma. Isabel Solano
 * @version 1.1, 15/3/23
 * 
 * States of an authomata
 * 
 */

package src;

public class State implements Comparable<State> {
    public int id;
    public Type type;

    /**
     * 
     * Constructor
     * 
     * @param id            Identifier of the State, numeric
     * @param typeOption    Type of State 1 for Initial, 2 for Transitional, 3 for Final
     */
    public State(int id, int typeOption) {
        this.id = id;
        // with the given number, it determines
        // the tipoe of State
        if (typeOption == 1) {
            // Inicial
            this.type = Type.Inicial;

        } else if (typeOption == 2) {
            // Transition
            this.type = Type.Trans;
            
        } else if (typeOption == 3) {
            // Final
            this.type = Type.Final;
        }
    }

    /**
     * Transforms States into Transitional types.
     * This is useful for the subset Thompson consturction
     */
    public void setToTrans() {
        this.type = Type.Trans;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return Integer.toString(id);
    }

    @Override
    public int compareTo(State s) {
        int res = 0;

        if (this.id > s.id) res = 1;
        else if (this.id < s.id) res = -1;

        return res;
    }

    /* Getters */
    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public boolean isFinal() {
        if (type == Type.Final) return true;
        else return false; 
    }
    
}

enum Type {

    Inicial, Trans, Final
}