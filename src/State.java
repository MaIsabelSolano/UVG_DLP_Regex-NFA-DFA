/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
 * 
 * States of an authomata
 * 
 */

package src;

public class State {
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

    /* Getters */
    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }
    
}

enum Type {

    Inicial, Trans, Final
}