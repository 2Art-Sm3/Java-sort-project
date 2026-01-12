package ru.sortproject.strategy;

import ru.sortproject.structure.CustomList;

import java.util.Comparator;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

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

        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                T current = list.get(j);
                T next = list.get(j + 1);

                if (comparator.compare(current, next) > 0) {
                    list.update(j, next);
                    list.update(j + 1, current);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }
}