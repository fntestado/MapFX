package datastructures.tree;

/**
 * Helper class for storing a data from the tree. This class
 * implements generic type parameter that extends Comparable
 * interface. This implementation is used to compare each value from
 * tree.
 * @param <T>
 * @author De Lara, Leigh Tondee
 * @author Supang, Bryan Rane
 * @author Testado, Franz Nico
 * @since 2019-10-28
 */
public class Node<T extends Comparable<T>> {
    T data; // data to be inserted
    Node<T> left;
    Node<T> right;

    /**
     * A default constructor for creating an object
     */
    Node() {
    }

    /**
     * A constructor for creating a Node object that accept a value
     * with initial data of null for left and right value
     * @param data
     */
    Node(T data) {
        this.data = data;
        right = null;
        left = null;
    }

    /**
     * Supports {@code Tree} from adding nodes by calling itself recursively
     *
     * @param current
     * @param data
     * @return
     */
    public Node<T> addNode(Node<T> current, T data) {
       if (current == null) {
            Tree.size++;
            return new Node<T>(data);
        }

        if (data.compareTo(current.data) < 0) {
            // insert the data in the left side if the new data is
            // less than to the current's (root) data
            current.left = addNode(current.left, data);
        } else if (data.compareTo(current.data) > 0) {
            // insert the data in the right side if the new data is
            // greater than to the current's (root) data
            current.right = addNode(current.right, data);
        } else {
            // if it is equal then it means the data already exists
            return current;
        }
        return current;
    }


    /**
     * Finds the node to be deleted into the tree
     *
     * @param current
     * @param data
     * @return
     */
    public Node deleteNode(Node<T> current, T data) throws NullPointerException {
        if (current == null) {
            return null;
        }
        if (data == current.data) {
            // if the node is a leaf node
            if (current.left == null && current.right == null) {
                return null;
            }
            // if the node has only  one child
            if (current.right == null) {
                return current.left;
            }
            if (current.left == null) {
                return current.right;
            }
            T smallestValue = findSmallestValue(current.right);
            current.data = smallestValue;
            current.right = deleteNode(current.right, smallestValue);
            return current;
        }
        if (data.compareTo(current.data) < 0) {
            current.left = deleteNode(current.left, data);
            return current;
        }
        current.right = deleteNode(current.right, data);
        return current;
    }

    // Helper method that is used for comparing the smallest data from each node
    private T findSmallestValue(Node<T> root) {
        return root.left == null ? root.data : findSmallestValue(root.left);
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }
}
