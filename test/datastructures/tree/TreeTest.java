package datastructures.tree;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TreeTest {

    @Test
    public void add() {
        Tree<Integer> tree = new Tree();

        tree.add(10);
        tree.add(102);
        tree.add(20);
        tree.add(1);
        tree.add(9);

        System.out.println("FINAL: " + tree.get(new Integer(9)));
    }

    @Test
    public void size() {
        Tree tree = new Tree();

        tree.add(10);
        tree.add(102);
        tree.add(20);
        tree.add(1);
        tree.add(9);

        Assert.assertTrue("not equal", 5 == tree.size());
    }

    @Test
    public void contains() {
        Tree<Integer> tree = new Tree();

        tree.add(10);
        tree.add(102);
        tree.add(20);
        tree.add(1);
        tree.add(9);

        Assert.assertEquals(tree.contains(9), 9);
    }

    @Test
    public void breadFirstSearch() {
        Tree tree = new Tree();

        tree.add(10);
        tree.add(102);
        tree.add(20);
        tree.add(1);
        tree.add(9);

//        tree.breadthFirstSearch();
    }
}