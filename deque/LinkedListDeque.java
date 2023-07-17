package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class LinkDequeType {
        private T first;
        private LinkDequeType head;
        private LinkDequeType tail;
        private LinkDequeType(T i, LinkDequeType prev, LinkDequeType next) {
            first = i;
            head = prev;
            tail = next;
        }
    }
    private int size;
    private LinkDequeType sentinel;

    private class LinkIter implements Iterator<T> {
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
        return new LinkIter();
    }
    @Override
    public void addFirst(T item) {
        if (isEmpty()) {
            size += 1;
            sentinel = new LinkDequeType(null, null, null);
            sentinel.tail = new LinkDequeType(item, sentinel, sentinel);
            sentinel.head = sentinel.tail;
        } else {
            size += 1;
            LinkDequeType noder = sentinel.tail;
            sentinel.tail = new LinkDequeType(item, sentinel, noder);
            sentinel.tail.tail.head = sentinel.tail;
        }
    }
    @Override
    public void addLast(T item) {
        if (isEmpty()) {
            size += 1;
            sentinel = new LinkDequeType(null, null, null);
            sentinel.tail = new LinkDequeType(item, sentinel, sentinel);
            sentinel.head = sentinel.tail;
        } else {
            size += 1;
            LinkDequeType noder = sentinel.head;
            sentinel.head = new LinkDequeType(item, noder, sentinel);
            sentinel.head.head.tail = sentinel.head;
        }
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public void printDeque() {
        LinkDequeType checker = sentinel;
        if (sentinel.tail == null) {
            System.out.println(sentinel.first);
        }
        while (checker.tail != sentinel) {
            checker = checker.tail;
            System.out.print(checker.first + " ");
        }
        System.out.println();
    }
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        T value = sentinel.tail.first;
        sentinel.tail = sentinel.tail.tail;
        sentinel.tail.head = sentinel;
        return value;
    }
    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        size -= 1;
        T value = sentinel.head.first;
        sentinel.head = sentinel.head.head;
        sentinel.head.tail = sentinel;
        return value;
    }
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        LinkDequeType checker = sentinel;
        for (int i = index; i >= 0; i--) {
            checker = checker.tail;
        }
        return checker.first;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (this == o) {
            return true;
        } else if (o instanceof Deque item) {
            if (size != item.size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (!get(i).equals(item.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public LinkedListDeque() {
        size = 0;
        sentinel = new LinkDequeType(null, null, null);
        sentinel.head = sentinel;
        sentinel.tail = sentinel;
    }
    private T recursiveHelper(int indexer, LinkDequeType node) {
        if (indexer == 0) {
            return node.first;
        } else {
            return recursiveHelper(indexer - 1, node.tail);
        }
    }
    public T getRecursive(int index) {
        if (index >= size() || index < 0) {
            return null;
        }
        LinkDequeType checker = sentinel.tail;
        return recursiveHelper(index, checker);
    }
}
