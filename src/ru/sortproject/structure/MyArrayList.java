package ru.sortproject.structure;

import java.lang.reflect.Array;
import java.util.Iterator;

public class MyArrayList<T> implements CustomList<T> {

    private T[] values;
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    @SuppressWarnings("unchecked")
    public MyArrayList(int capacity) {
        if (capacity <= 0)
            throw new IllegalArgumentException("Capacity <= 0");
        values = (T[]) new Object[capacity];
    }

    public MyArrayList() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public boolean add(T element) {
        if (size == values.length) {
            grow();
        }
        values[size++] = element;
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        return values[index];
    }

    @Override
    public void update(int index, T element) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        values[index] = element;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] toArray(Class<T> clazz) {
        T[] copy = (T[]) Array.newInstance(clazz, size);
        for (int i = 0; i < size; i++) {
            copy[i] = get(i);
        }
        return copy;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(values, size);
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        int newCapacity = (int) (values.length * 1.5 + 1);
        T[] newValues = (T[]) new Object[newCapacity];
        System.arraycopy(values, 0, newValues, 0, values.length);
        values = newValues;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size(); i++) {
            sb.append(get(i));
            if (i < size() - 1) sb.append(", ");
        }
        return sb.append("]").toString();
    }

    @Override
    public boolean addAll(CustomList<T> other) {
        boolean changed = false;
        for (T element : other) {
            add(element);
            changed = true;
        }
        return changed;
    }
}
