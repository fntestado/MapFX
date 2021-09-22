package datastructures.linkedlist;


public class Node<T> {
    private T data;
    private Node<T> next;
    private Node<T> previous;

    /**
     * Node class constructor accepting generic data
     * @param data
     */
    public Node(T data) {
        this(null, data, null);
    }

    /**
     * Node class constructor with parameter setting
     * the previous node, generic data, and next node
     * @param previous
     * @param data
     * @param next
     */
    public Node(Node<T> previous, T data, Node<T> next) {
        this.previous = previous;
        this.data = data;
        this.next = next;
    }

    /**
     * generic data variable getter method
     * @return
     */
    public T getData() {
        return data;
    }

    /**
     * generic data variable setter method
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * next variable getter method
     * @return
     */
    public Node<T> getNext() {
        return next;
    }

    /**
     * next variable setter method
     * @param next
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }

    /**
     * previous variable getter method
     * @return
     */
    public Node<T> getPrevious() {
        return previous;
    }

    /**
     * previous variable setter method
     * @param previous
     */
    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

}
