package ru.sortproject.strategy;

import ru.sortproject.structure.CustomList;
import java.util.Comparator;

public class InsertionSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(CustomList<T> list, Comparator<T> comparator) {
        if (list == null) {
            throw new IllegalArgumentException("List is null");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }

        int n = list.size();

        if (n < 2) {
            return;
        }

        for (int i = 1; i < n; i++) {
            T key = list.get(i);
            int j = i - 1;

            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.update(j + 1, list.get(j));
                j = j - 1;
            }
            list.update(j + 1, key);
        }
    }
}