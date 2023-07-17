package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparer;

    public MaxArrayDeque(Comparator<T> c) {
        comparer = c;
    }
    public T max() {
        if (isEmpty()) {
            return null;
        } else {
            return max(comparer);
        }
    }
    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        } else {
            T holder = get(0);
            for (int i = 0; i < size(); i++) {
                if (c.compare(get(i), holder) > 0) {
                    holder = get(i);
                }
            }
            return holder;
        }
    }
}
