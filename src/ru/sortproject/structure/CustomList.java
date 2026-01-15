package ru.sortproject.structure;


public interface CustomList<T> extends Iterable<T> {

    boolean add(T element);
    T get(int index);
    void update(int index, T element);
    int size();
    boolean addAll(CustomList<T> other);
    void clear();
}
