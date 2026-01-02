package ru.sortproject.structure;

public interface CustomList<T> {

    void add(T element);
    T get(int index);
    int size();
    T[] toArray(Class<T> clazz);
}
