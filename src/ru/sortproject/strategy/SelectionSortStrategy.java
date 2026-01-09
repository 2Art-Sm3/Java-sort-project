package ru.sortproject.strategy;

import ru.sortproject.structure.CustomList;
import java.util.Comparator;

public class SelectionSortStrategy<T> implements SortStrategy<T> {

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

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (comparator.compare(list.get(j), list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = list.get(i);
                list.update(i, list.get(minIndex));
                list.update(minIndex, temp);
            }
        }
    }
}