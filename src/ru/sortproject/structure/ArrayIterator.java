package ru.sortproject.structure;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

    private int index = 0;
    private final T[] values;
    private final int size;

    public ArrayIterator(T[] values, int size) {
        this.values = values;
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        return index < size;
    }

    @Override
    public T next() {
        return values[index++];
    }
}
