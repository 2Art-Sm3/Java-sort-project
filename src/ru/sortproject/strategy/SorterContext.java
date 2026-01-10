package ru.sortproject.strategy;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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

    public void saveSortedToFile(CustomList<Car> list, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            for (Car car : list) {
                writer.write(car.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
