package deque;
import java.util.Iterator;
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {

    private int size;
    private static final int FOUR = 4;
    private static final int ENDER = 5;
    private static final int START_SIZE = 8;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    private class ArrayIter implements Iterator<T> {
        private int indexer = 0;

        @Override
        public boolean hasNext() {
            if (indexer >= size()) {
                return false;
            }
            return true;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                return null;
            }
            T value = get(indexer);
            indexer += 1;
            return value;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayIter();
    }

    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    private int plusOne(int index) {
        if (index == items.length - 1) {
            return 0;
        }
        return index + 1;
    }

    @Override
    public void addFirst(T item) {
        if (items.length == size) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    private void upsizer(int capacity) {
        int indexer = capacity / FOUR;
        int first = minusOne(indexer);
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < items.length; i++) {
            a[indexer] = get(i);
            indexer += 1;
        }
        items = a;
        nextLast = indexer;
        nextFirst = first;
    }

    private void downsizer(int capacity) {
        int indexer = capacity / FOUR;
        int first = minusOne(indexer);
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < items.length; i++) {
            if (get(i) != null) {
                a[indexer] = get(i);
                indexer += 1;
            }
        }
        nextLast = indexer;
        nextFirst = first;
        items = a;
    }

    /**
     * Resizes the underlying array to the target capacity
     */
    private void resize(int capacity) {
        if (size > items.length / FOUR) {
            upsizer(capacity);
        } else {
            downsizer(capacity);
        }
    }

    @Override
    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i < items.length; i++) {
            System.out.println(get(i) + " ");
        }
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        if (size < items.length / FOUR && size >= (START_SIZE * 2)) {
            resize(items.length / 2);
        }
        T x = get(0);
        nextFirst = plusOne(nextFirst);
        items[nextFirst] = null;
        size -= 1;
        return x;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (size < items.length / FOUR && size >= (START_SIZE * 2)) {
            resize(items.length / 2);
        }
        T x = get(size - 1);
        nextLast = minusOne(nextLast);
        items[nextLast] = null;
        size -= 1;
        return x;
    }

    @Override
    public T get(int index) {
        int indexer = (nextFirst + index + 1) % items.length;
        T value = items[indexer];
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (o instanceof Deque checker) {
            if (size() != checker.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!checker.get(i).equals(get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[START_SIZE];
        nextFirst = FOUR;
        nextLast = ENDER;
    }
}
