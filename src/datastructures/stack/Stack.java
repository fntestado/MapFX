package datastructures.stack;

import java.util.EmptyStackException;

/**
 * The {@code Stack} class represents a las-in-first-out (LIFO) stack of generic items.
 * It supports the usual push and pop operations, along with methods for peeking at
 * the top item, testing if the stack is empty, getting the number of items in the
 * stack, and iterating over the items in LIFO order.
 *
 * @author De Lara, Leigh Tondee
 * @author Supang, Bryan Rane
 * @author Testado, Franz Nico
 * @version 1.0
 * @since 2019-11-04
 *
 * @param <T> the generic type of an item in this stack
 */
public class Stack<T> {
    private int size;   // size of the stack
    private Node<T> top;   // top of stack

    /**
     * Helper class for implementing LinkedList
     * type of datastructures.Stack
     */
    private class Node<T> {
        private T data; // data of the node class
        private Node<T> next = null;    // reference for next data

        // constructor for creating a new data
        public Node(T data) {
            this.data = data;
        }

        // constructor for creating a new data and value for next
        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
        // setter method for next
        public void setNext(Node<T> next) {
            this.next = next;
        }

        // getter method for next
        public Node<T> getNext() {
            return next;
        }

        public T getData() {
            return data;
        }
    }

    /**
     * Constructor for initializing an empty stack
     */
    public Stack() {
        top = null;
        size = 0;
    }

    /**
     * This method removes the object at the top of this stack.
     * @return
     * @throws EmptyStackException
     */
    public T pop() throws EmptyStackException {
        if (isEmpty())
            throw new EmptyStackException();
        Node<T> temp = top;
        top = temp.getNext();
        size--;
        return temp.getData();
    }

    /**
     * This method accept item from the parameter that will
     * use to push an item onto the top of this stack
     *
     * @param item
     */
    public void push(T item) {
        Node<T> newNode = new Node<>(item, null);
        if (top == null)
            top = newNode;
        else {
            newNode.setNext(top);
            top = newNode;
        }
        size++;
    }

    /**
     * This method is use for checking the size of stack
     * by checking the top level of the stack.
     *
     * @return
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * Returns (but does not remove) the node's data most recently added to this stack
     * @return the node's data most recently added to this stack
     * @throws NullPointerException if this stack is empty
     */
    public T peek() {
        if (isEmpty())
            throw new NullPointerException();

        return top.data;
    }

    /**
     * This method returns the size of this stack
     * @return
     */
    private int size() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0)
            return "Empty";
        else {
            Node<T> temp = top;
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            while (temp != null) {
                if (temp.getNext() == null)
                    sb.append(temp.getData() + "]");
                else {
                    sb.append(temp.getData() + ", ");
                    temp = temp.getNext();
                }
            }
            return sb.toString();
        }
    }
}
