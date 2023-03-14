/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
 * 
 * Simple Node from the Tree. Stores the value as a Symbol and it's children
 */

package src;

public class TreeNode {
    Symbol value;
    TreeNode leftChild;
    TreeNode righChild;

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
}
