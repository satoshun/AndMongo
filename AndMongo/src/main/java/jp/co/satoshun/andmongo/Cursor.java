package jp.co.satoshun.andmongo;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Cursor {
    private List<?> value;
    private String type;

    public Cursor(List<?> value) {
        this(value, null);
    }

    public Cursor(List<?> value, String type) {
        this.value = value;
        this.type = type;
    }

    public <T> List<T> get() {
        return (List<T>) value;
    }

    public <T> T getOne() {
        return (T) value.get(0);
    }

    public <T> CursorIterator<T> iterator() {
        return new CursorIterator<T>((List<T>) value);
    }

    public class CursorIterator<T> implements Iterable<T>, Iterator<T> {
        Iterator<T> it;

        public CursorIterator(List<T> list) {
            it = list.iterator();
        }

        @Override
        public boolean hasNext() {
            return it.hasNext();
        }

        @Override
        public T next() {
            return it.next();
        }

        @Override
        public void remove() {
        }

        @Override
        public Iterator<T> iterator() {
            return this;
        }
    }
}
