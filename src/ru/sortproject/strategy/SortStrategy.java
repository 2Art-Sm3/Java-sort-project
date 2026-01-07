package ru.sortproject.strategy;

import ru.sortproject.structure.CustomList;
import ru.sortproject.structure.MyArrayList;

import java.util.Comparator;

public interface SortStrategy<T> {
    void sort(CustomList<T> list, Comparator<T> comparator);
}
