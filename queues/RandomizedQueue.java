/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int n;


    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++)
            temp[i] = arr[i];
        arr = temp;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == arr.length) resize(2 * arr.length);
        arr[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int randIndex = StdRandom.uniform(n);
        Item temp = arr[randIndex];
        arr[randIndex] = arr[n - 1];
        arr[n - 1] = temp;

        Item item = arr[--n];
        arr[n] = null;
        if (n > 0 && n == arr.length / 4) resize(arr.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        // [0,n)
        int randIndex = StdRandom.uniform(n);
        return arr[randIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private int i;
        private Item[] tempArr;

        // fixed the two nested iterators bug;
        public RandomArrayIterator() {
            i = n;
            tempArr = (Item[]) new Object[n];
            for (int j = 0; j < n; j++) {
                tempArr[j] = arr[j];
            }
        }

        public boolean hasNext() {
            return i > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        // TODO: unsure is the equal prob;
        public Item next() {
            if (i == 0) throw new NoSuchElementException();
            int randIndex = StdRandom.uniform(i);
            Item temp = tempArr[randIndex];
            tempArr[randIndex] = tempArr[i - 1];
            tempArr[i - 1] = temp;
            return tempArr[--i];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>();
        randomizedQueue.enqueue(1);
        randomizedQueue.enqueue(2);
        randomizedQueue.enqueue(3);
        for (int i : randomizedQueue) {
            System.out.print(i + " ");
        }
    }

}
