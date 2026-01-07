package ru.sortproject.strategy;

import java.util.Comparator;

public class BubbleSortStrategy<T> implements SortStrategy<T> {

    @Override
    public void sort(T[] array, Comparator<T> comparator) {
        if (array == null) {
            throw new IllegalArgumentException("Array is null");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator is null");
        }

        if (array.length < 2) {
            return;
        }

        int n = array.length;
        boolean swapped;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(array[j], array[j + 1]) > 0) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    private void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

}