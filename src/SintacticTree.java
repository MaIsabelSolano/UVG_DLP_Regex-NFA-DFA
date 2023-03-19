package src;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;

public class SintacticTree {

    private TreeNode root;
    private HashMap<Integer, ArrayList<Integer>> followpos = new HashMap<>();
    private HashMap<Integer, Symbol> posSymbol = new HashMap<>();
    private int newpos = 0;
    private int terminalpos = 0;

    public SintacticTree(Stack<Symbol> input) {
        this.root = genTree(input);
        genFirstAndLastPos(root);
        genFollowPos(root);
    }

    private TreeNode genTree(Stack<Symbol> input){

        Stack<TreeNode> nodes = new Stack<>();

        for (int i = 0; i < input.size(); i ++) {
            if (!input.get(i).isOperator()) {
                // If it is not, it is a leaf of the tree and just
                // addet to it 
                TreeNode newNode = new TreeNode(input.get(i));

                // Check if nullable 
                if (newNode.value.c_id == 'ε') newNode.setNullable();

                nodes.push(newNode);
                
            } else {
                // If it is, we have to check wheather or not the have
                // one or two children
                if (input.get(i).c_id == '+' || input.get(i).c_id == '*' || input.get(i).c_id == '?') {
                    TreeNode left = nodes.pop();
                    TreeNode newNode = new TreeNode(input.get(i), left);

                    // Check if nullable 
                    if (input.get(i).c_id == '*' || input.get(i).c_id == '?') newNode.setNullable();
                    if (newNode.leftChild.isNullable()) newNode.setNullable();
                    nodes.push(newNode);
                }
                else {
                    TreeNode right = nodes.pop();
                    TreeNode left = nodes.pop();
                    TreeNode newNode = new TreeNode(input.get(i), left, right);

                    // check nullability
                    // concat
                    if (input.get(i).c_id == '.') {
                        if (newNode.leftChild.isNullable() &&  newNode.righChild.isNullable()) {
                            newNode.setNullable();
                        }
                    } else if (input.get(i).c_id == '|') {
                        if (newNode.leftChild.isNullable() ||  newNode.righChild.isNullable()) {
                            newNode.setNullable();
                        }
                    }

                    // or 
                    nodes.push(newNode);
                }
            }
        }

        return nodes.pop();
    }

    private void genFirstAndLastPos( TreeNode node) {

        if (node == null) {
            return;
        }

        // Gen children's first
        genFirstAndLastPos(node.leftChild);
        genFirstAndLastPos(node.righChild);

        if (node.value.isOperator()) {
            // Is an operator
            if (node.value.c_id == '.') {
                // concat

                // firstpos
                if (node.leftChild.isNullable()) {
                    node.addFirstPos(node.leftChild.getFirstpos());
                    node.addFirstPos(node.righChild.getFirstpos());
                } else node.addFirstPos(node.leftChild.getFirstpos());

                // lastpos
                if (node.righChild.isNullable()) {
                    node.addLastPos(node.leftChild.getLastpos());
                    node.addLastPos(node.righChild.getLastpos());
                } else node.addLastPos(node.righChild.getLastpos());

            } else if (node.value.c_id == '*' || node.value.c_id == '?' || node.value.c_id == '+') {
                // kleene, plus  or question mark (only one child)
                node.addFirstPos(node.leftChild.getFirstpos());
                node.addLastPos(node.leftChild.getLastpos());

            } else if (node.value.c_id == '|') {

                // firstpos
                node.addFirstPos(node.leftChild.getFirstpos());
                node.addFirstPos(node.righChild.getFirstpos());

                // lastpos
                node.addLastPos(node.leftChild.getLastpos());
                node.addLastPos(node.righChild.getLastpos());   
            }

        } else if (node.value.c_id == '#') {
            // Is the last simbol '#'
            newpos ++;
            node.firstpos.add(newpos);
            node.lastpos.add(newpos);
            node.position = newpos;

            // Initialize followos arrayList
            ArrayList<Integer> temp = new ArrayList<>();
            followpos.put(newpos, temp);

            terminalpos = newpos; 

            // Add to the dictionary
            posSymbol.put(newpos, node.value);

        } else if (node.value.c_id == 'ε'){
            // Don't generate a position for it
            ArrayList<Integer> temp = new ArrayList<>();
            followpos.put(null, temp);

        }
        
        else {
            // Is a leaf, a Symbol, therefore, it's first and last pos are itself
            newpos++;
            node.firstpos.add(newpos);
            node.lastpos.add(newpos);
            node.position = newpos;

            // Initialize followos arrayList
            ArrayList<Integer> temp = new ArrayList<>();
            followpos.put(newpos, temp);

            // Add to the dictionary
            posSymbol.put(newpos, node.value);

        }
    }

    private void genFollowPos(TreeNode node) {

        if (node == null) {
            return;
        }

        // Gen children's first
        genFollowPos(node.leftChild);
        genFollowPos(node.righChild);
        
        if (node.value.c_id == '.') {
            // concat
            for (int pos: node.leftChild.getLastpos()) {
                for (int posAdd: node.righChild.getFirstpos()) {
                    if (!followpos.get(pos).contains(posAdd)) followpos.get(pos).add(posAdd);
                }
            }

        } else if (node.value.c_id == '*' || node.value.c_id == '+') {
            for (int pos: node.getLastpos()) {
                for (int posAdd: node.getFirstpos()) {
                    // Adds all of its first post to the last pos
                    if (!followpos.get(pos).contains(posAdd)) followpos.get(pos).add(posAdd);
                }
            }
        }



    }

    public void printTree( TreeNode node) {

        if (node == null) {
            return;
        }

        printTree(node.leftChild);

        printTree(node.righChild);

        String res = "[";
        res += node.value.c_id;
        if (!node.postionIsNull()) res += ", ("+Integer.toString(node.getPosition())+") ";
        else res += ", (-) ";

        res +=  ", {";
        for (int x: node.getFirstpos()) res += Integer.toString(x) + ", ";
        res += "}";

        res +=  ", {";
        for (int x: node.getLastpos()) res += Integer.toString(x) + ", ";
        res += "}";

        if (!node.value.isOperator()) {
            if (!node.postionIsNull()) {
                res +=  ", [";
                for (int x: followpos.get(node.getPosition())) res += Integer.toString(x) + ", ";
                res += "]";
            }
            
        }

        if (node.isNullable()) res += ", N";
        else res += ", NN";

        res += "]";
        System.out.println(res);

    }

    /* Getters */
    public TreeNode getRoot() {
        return root;
    }

    public int getTerminalpos() {
        return terminalpos;
    }

    public HashMap<Integer, Symbol> getPosSymbol() {
        return posSymbol;
    }

    public ArrayList<Integer> getFollowpos(int pos) {
        return followpos.get(pos);
    }
    
}
