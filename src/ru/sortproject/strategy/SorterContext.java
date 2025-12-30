package ru.sortproject.strategy;

import java.util.Comparator;

public class SorterContext<T> {
    private SortStrategy<T> strategy;

    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void executeSort(T[] array, Comparator<T> comparator) {
        if (strategy == null) {
            System.out.println("Стратегия не выбрана!");
            return;
        }
        strategy.sort(array, comparator);
    }
}
