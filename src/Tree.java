/*
 * @author: Ma. Isabel Solano
 * @version 1, 26/02/23
 * 
 * Tree Structure for by TreNodes. 
 * 
 */

package src;

import java.util.Stack;

public class Tree {

    private TreeNode root;
    
    /**
     * 
     * Simple constructor that calls the mathod to generate the tree
     * 
     * @param input User's regular expression as a list of symbols in a postfix order
     */
    public Tree(Stack<Symbol> input) {
        this.root = genTree(input);
    }

    /**
     * 
     * Generates Tree node by node, buttom up, and left to right.
     * 
     * @param input User's regular expression as a list of symbols in a postfix order
     * @return      Curent new node for it to be stored in other nodes. 
     */
    private TreeNode genTree(Stack<Symbol> input){

        Stack<TreeNode> nodes = new Stack<>();

        for (int i = 0; i < input.size(); i ++) {
            if (!input.get(i).isOperator()) {
                // If it is not, it is a leaf of the tree and just
                // addet to it 
                TreeNode newNode = new TreeNode(input.get(i));
                nodes.push(newNode);
            } else {
                // If it is, we have to check wheather or not the have
                // one or two children
                if (input.get(i).c_id == '+' || input.get(i).c_id == '*' || input.get(i).c_id == '?') {
                    TreeNode left = nodes.pop();
                    TreeNode newNode = new TreeNode(input.get(i), left);
                    nodes.push(newNode);
                }
                else {
                    TreeNode right = nodes.pop();
                    TreeNode left = nodes.pop();
                    TreeNode newNode = new TreeNode(input.get(i), left, right);
                    nodes.push(newNode);
                }
            }
        }

        return nodes.pop();
    }

    /**
     * 
     * Prints all of the tree nodes, bottom up, left to right 
     * 
     * @param node  Tree's root and current node
     */
    public void printTree( TreeNode node) {

        if (node == null) {
            return;
        }

        printTree(node.leftChild);

        printTree(node.righChild);

        System.out.println(node.value.c_id);

        
    }

    /* Getters */
    public TreeNode getRoot() {
        return root;
    }

}