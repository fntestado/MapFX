package datastructures.tree;

import datastructures.linkedlist.LinkedList;
import datastructures.stack.Stack;

/**
 * The {@code Tree} class represents almost each function in a recursive
 * manner. The {@code Tree} class implemented using nodes as linked list
 * way of storing data. This implementation uses an (unbalanced) binary
 * search tree. Each values of this tree is implemented in a generic data
 * type.
 *
 * @param <T>
 * @author De Lara, Leigh Tondee
 * @author Supang, Bryan Rane
 * @author Testado, Franz Nico
 * @version 1.0
 * @since 2019-10-28
 */

public class Tree<T extends Comparable<T>> {
    private Node<T> root;   // root value of this tree
    public static int size;

    /**
     * Constructor with a root value
     * of null and a zero value of tree size
     */
    public Tree() {
        root = null;
        size = 0;
    }

    /**
     * Adds the specified data from the parameter into this tree
     *
     * @param data
     */
    public void add(T data) {
        root = new Node<T>().addNode(root, data);
    }

    /**
     * Deletes the specified data from the parameter into this tree
     *
     * @param data
     */
    public void delete(T data) {
        root = new Node<T>().deleteNode(root, data);
    }

    LinkedList<T> result;

    /**
     * Visits all the nodes starting from left to right to perform in order traversal
     */
    public LinkedList<T> traverseInOrder() {
        Stack<Node<T>> nodes = new Stack<>();
        result = new LinkedList<>();
        Node<T> temp = root;

        while (!nodes.isEmpty() || temp != null) {
            if (temp != null) {
                nodes.push(temp);
                temp = temp.left;
            } else {
                Node<T> node = nodes.pop();
                result.add(node.data);
                temp = node.right;
            }
        }
        return result;
    }

    /**
     * Visits the left subtree, the right subtree, and the root node at the end
     */
    public LinkedList<T> traversePostOrder() {
        Stack<Node<T>> nodes = new Stack<>();
        LinkedList<T> result = new LinkedList<>();
        nodes.push(root);

        while (!nodes.isEmpty()) {
            Node<T> temp = nodes.peek();

            if (temp.isLeaf()) {
                Node<T> node = nodes.pop();
                result.add(node.data);
            } else {
                if (temp.right != null) {
                    nodes.push(temp.right);
                    temp.right = null;
                }

                if (temp.left != null) {
                    nodes.push(temp.left);
                    temp.left = null;
                }
            }
        }
        return result;
    }

    /**
     * Check if the tree contains a specific value
     *
     * @param data
     * @return true if tree contains the data, false if tree not contains the data
     */
    public boolean contains(T data) {
        Node<T> temp = root;

        while (temp != null) {
            if (temp.data.compareTo(data) == 0) {
                return true;
            }
            if (data.compareTo(temp.data) < 0) {
                temp = temp.left;
            } else {
                temp = temp.right;
            }
        }
        return false;
    }

    public T get(T data) {
        return contains(data) ? getNode(root, data) : null;
    }

    public LinkedList<T> toLinkedList() {
        return traverseInOrder();
    }

    private T getNode(Node<T> node, T data) {
        if (node == null) {
            return null;
        }
        if (data.compareTo(node.data) == 0) {
            return node.data;
        } else if (data.compareTo(node.data) < 0) {
            return this.getNode(node.left, data);
        } else {
            return this.getNode(node.right, data);
        }
    }

    /**
     * Returns the total number of node
     *
     * @return size
     */
    public int size() {
        return size;
    }

    public T getRoot() {
        return root.data;
    }

    /**
     * Visits all nodes of a level before going to the next level.
     */
    public void breadFirstSearch() {
        int height = calculateHeight(root);
        for (int i = 0; i < height; i++) {

        }
    }

    private void levelOrder(Node<T> node, int level) {
        if (node == null) {
            return;
        }
        if (level == 0) {

        }
    }

    private int calculateHeight(Node<T> node) {
        if (root == null) {
            return 0;
        } else {
            int lsh = calculateHeight(node.left);
            int rsh = calculateHeight(root.right);
            return Math.max(lsh, rsh) + 1;
        }
    }

}











