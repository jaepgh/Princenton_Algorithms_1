
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

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
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a;
    private int n;

    public RandomizedQueue() // construct an empty randomized queue
    {
        a = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() // is the randomized queue empty?
    {
        return n == 0;
    }

    public int size() // return the number of items on the randomized queue
    {
        return n;
    }

    public void enqueue(Item item) // add the item
    {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == a.length) {
            resize(2 * a.length);    // double size of array if necessary
        }
        a[n++] = item;
    }

    public Item dequeue() // remove and return a random item
    {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniform(0, n);
        Item item = a[random];

        a[random] = a[--n];
        a[n] = null;

        if (n > 0 && n == a.length / 4) {
            resize(a.length / 2);
        }

        return item;
    }

    public Item sample() // return a random item (but do not remove it)
    {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniform(0, n);
        return a[random];
    }

    private void resize(int capacity) {
        assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    public Iterator<Item> iterator() // return an independent iterator over items in random order
    {
        return new RamQueueIterator();
    }

    private class RamQueueIterator implements Iterator<Item> {

        private int i;
        private int order[];

        public RamQueueIterator() {
            i = n - 1;
            order = new int[n];
            
            for (int j = 0; j < n; j++) {
                order[j] = j;
            }
            
            StdRandom.shuffle(order);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return a[order[i--]];
        }
    }
}
