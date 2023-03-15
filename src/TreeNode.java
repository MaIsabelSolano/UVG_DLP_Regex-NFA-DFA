/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
 * 
 * Simple Node from the Tree. Stores the value as a Symbol and it's children
 */

package src;

import java.util.ArrayList;

public class TreeNode {
    Symbol value;
    TreeNode leftChild;
    TreeNode righChild;
    
    int position = -1;
    boolean nullable = false;
    
    ArrayList<Integer> firstpos = new ArrayList<>();
    ArrayList<Integer> lastpos = new ArrayList<>();

    /**
     * 
     * Tree's Leaf node constructor
     * 
     * @param value Symbol that the leaf represents
     */
    public TreeNode(Symbol value){
        this.value = value;
    }

    /**
     * 
     * Unary operator node constructor
     * 
     * @param value     Value that the node represents
     * @param leftChild Only child node
     */
    public TreeNode(Symbol value, TreeNode leftChild) {
        this.value = value;
        this.leftChild = leftChild;
    }

    /**
     * 
     * Binary operator node constructor
     * 
     * @param value         Value that the node represents
     * @param leftChild     Node that represents it's left child
     * @param rightChild    Node that represents it's right child
     */
    public TreeNode(Symbol value, TreeNode leftChild, TreeNode rightChild) {
        this.value = value;
        this.leftChild = leftChild;
        this.righChild = rightChild;
    }

    public void addFirstPos(ArrayList<Integer> toAdd) {
        for (int i: toAdd){
            if (!firstpos.contains(i)) firstpos.add(i);
        }
    }

    public void addLastPos(ArrayList<Integer> toAdd) {
        for (int i: toAdd){
            if (!lastpos.contains(i)) lastpos.add(i);
        }
    }

    public boolean postionIsNull(){
        if (position < 0) return true;
        else return false;
    }

    /* Getters */
    public ArrayList<Integer> getFirstpos() {
        return firstpos;
    }

    public ArrayList<Integer> getLastpos() {
        return lastpos;
    }

    public boolean isNullable() {
        return nullable;
    }

    public int getPosition() {
        return position;
    }

    /* Setters */
    public void setNullable() {
        this.nullable = true;
    }

}
