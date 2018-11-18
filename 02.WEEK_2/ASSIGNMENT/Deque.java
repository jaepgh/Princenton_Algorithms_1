
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jaep2
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

    private int n; //Control the size of the stack
    private Node first;
    private Node last;

    private class Node {

        private Item item;
        private Node next;
        private Node head;
    }

    public Deque() // construct an empty deque
    {
        this.n = 0;
        this.first = null;
        this.last = null;
    }

    public boolean isEmpty() // is the deque empty?
    {
        return first == null;
    }

    public int size() // return the number of items on the deque
    {
        return n;
    }

    public void addFirst(Item item) // add the item to the front
    {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.head = null;
            first.next = null;

            last = first;
        } else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            first.head = null;
            oldFirst.head = first;
        }

        n++;
    }

    public void addLast(Item item) // add the item to the end
    {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            last = new Node();
            last.item = item;
            last.head = null;
            last.next = null;

            first = last;
        } else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.head = oldLast;

            oldLast.next = last;
        }

        n++;
    }

    public Item removeFirst() // remove and return the item from the front
    {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        Item oldFirst = this.first.item;
        if (n > 1) {
            first.next.head = null;
            first = first.next;
        } else {
            first = null;
            last = null;
        }

        n--;

        return oldFirst;
    }

    public Item removeLast() // remove and return the item from the end
    {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Item oldLast = last.item;

        if (n > 1) {
            last = last.head;
            last.next = null;
        } else {
            first = null;
            last = null;
        }

        n--;

        return oldLast;
    }

    @Override
    public Iterator<Item> iterator() // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

}
