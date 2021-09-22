package datastructures.linkedlist;

/**
 * The {@code LinkedList} class represents a doubly linked list class in which a node contains
 * a pointer to the previous as well as the next node in the sequence.
 *
 * @author Leigh Tondee De Lara
 * @author Bryan Supang
 * @author Franz Nico Testado
 */

public class LinkedList<T extends Comparable> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * default constructor that initialize a value
     */
    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Adding the node into the linked list at beginning.
     *
     * @param data
     */
    public void addFirst(T data) {
        Node<T> temp = new Node<>(null, data, head);

        if (isEmpty()) {
            head = temp;
            tail = temp;
            size++;
            return;
        }

        if (head != null)
            head.setPrevious(temp);
        head = temp;
        if (tail == null)
            tail = temp;
        size++;
    }

    /**
     * Adding the node into the linked list to the end.
     *
     * @param data
     */
    public void add(T data) {
        Node<T> temp = new Node<>(tail, data, null);

        if (isEmpty()) {
            head = temp;
            tail = temp;
            size++;
            return;
        }

        if (tail != null)
            tail.setNext(temp);
        tail = temp;
        size++;
    }

    /**
     * Adding the node into the linked list before the specified node.
     *
     * @param index
     * @param data
     */
    public void addBefore(int index, T data) {
        Node<T> newData = new Node<>(data);
        Node<T> temp = head;

        if (isEmpty())
            throw new NullPointerException();

        if (index == 0) {
            head = new Node<>(null, data, head);
            size++;
            return;
        }

        for (int i = 0; temp != null && i != index - 1; i++)
            temp = temp.getNext();


        temp.setNext(new Node<>(temp, data, temp.getNext()));
//            temp.setNext(new Node<>(temp, data, temp.getNext()));
        temp.setPrevious(new Node<>(temp.getPrevious(), temp.getData(), temp.getNext()));

        size++;
    }

    /**
     * Adding the node into the linked list after the specified node.
     *
     * @param index
     * @param data
     */
    public void addAfter(int index, T data) {
        Node<T> newData = new Node<>(data);
        Node<T> temp = head;

        if (isEmpty())
            throw new NullPointerException();

        if (index == 0) {
            head.setNext(new Node<>(head, data, head.getNext()));
            size++;
            return;
        }

        for (int i = 0; temp != null && i != index; i++)
            temp = temp.getNext();

        temp.setNext(new Node<>(temp, data, temp.getNext()));

        size++;
    }

    /**
     * Removing the node from specified index.
     *
     * @param
     * @throws NullPointerException
     */
    public void remove(T data) throws NullPointerException {
        if (isEmpty())
            throw new NullPointerException();

        if (head.getData().equals(data)) {
            head = head.getNext();
            size--;
            return;
        }

        Node<T> temp = head;

        while (temp != null) {
            if (temp.getData() == data) {

                break;
            }
            temp = temp.getNext();
        }

        if (temp == null)
            throw new NullPointerException();
        if (temp.getNext() != null)
            temp.getNext().setPrevious(temp.getPrevious());
        temp.getPrevious().setNext(temp.getNext());
        size--;
    }

    public Node<T> getTail() {
        return tail;
    }

    /**
     * Removing the node at the first index
     */
    public void removeFirst() {
        Node<T> temp = head;
        if (isEmpty())
            throw new NullPointerException();
        head = head.getNext();
        head = null;
        size--;
    }

    public void removeLast() {
        Node<T> temp = tail;
        if (isEmpty())
            throw new NullPointerException();
        tail = tail.getPrevious();
        tail = null;
        size--;
    }


    /**
     * this method is used for updating an existing value from the list with two parameters
     * accepting parameter the index to update and value of new data
     *
     * @param index
     * @param data
     */
    public void update(int index, T data) {
        Node<T> temp = head;

        if (isEmpty())
            throw new NullPointerException();

        for (int i = 0; temp != null && i == index - 1; i++)
            temp = temp.getNext(); //temp node remain

        temp.setData(data);
    }

    public T get(int index) {
        Node<T> temp = head;

        for (int i = 0; temp != null && i != index; i++)
            temp = temp.getNext();

        if (temp == null)
            return null;
        else
            return temp.getData();
    }

    public void sortList() {
        Node<T> current = head, index = null;
        T temp;

        if (head == null) {
            return;
        } else {
            while (current != null) {
                index = current.getNext();
                while (index != null) {
                    if (current.getData().compareTo(index.getData()) == 1) {
                        temp = current.getData();
                        current.setData(index.getData());
                        index.setData(temp);
                    }
                    index = index.getNext();
                }
                current = current.getNext();
            }
        }
    }

    public boolean contains(T data) {
        Node<T> temp = head;
        if (isEmpty())
            throw new NullPointerException();
        else {
            while (temp != null) {
                if (temp.getData().equals(data)) {
                    return true;
                }
                temp = temp.getNext();
            }
            return false;
        }
    }

    /**
     * getter method for the size
     *
     * @return the size of the Linked List
     */
    public int size() {
        return size;
    }

    /**
     * This method is used to check the if size is empty
     *
     * @return
     */
    public Boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        Node<T> temp = head;
        StringBuilder sb = new StringBuilder();
//        sb.append("[");

        while (temp != null) {
            sb.append(temp.getData() + "");
            temp = temp.getNext();
        }
        return "" + sb;
    }
}
