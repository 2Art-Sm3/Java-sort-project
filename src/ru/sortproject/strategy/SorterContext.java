package ru.sortproject.strategy;

import ru.sortproject.structure.CustomList;
import java.util.Comparator;

public class SorterContext<T> {

    private SortStrategy<T> strategy;

    public void setStrategy(SortStrategy<T> strategy) {
        this.strategy = strategy;
    }

    public void executeSort(CustomList<T> list, Comparator<T> comparator) {
        if (strategy == null) {
            System.out.println("Стратегия не выбрана!");
            return;
        }
        strategy.sort(list, comparator);
    }
}
