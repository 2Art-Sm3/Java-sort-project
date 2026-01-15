package ru.sortproject.test;

import ru.sortproject.model.Car;
import ru.sortproject.strategy.BubbleSortStrategy;
import ru.sortproject.structure.CustomList;
import ru.sortproject.strategy.SorterContext;
import ru.sortproject.util.CarComparator;
import ru.sortproject.util.DataLoader;
import ru.sortproject.util.SaveSortedToFile;

import java.io.File;

public class TestLauncher4 {
    public static void main(String[] args) {

        String fileName = "sorted_cars.txt";
        String loadPath = "data/" + fileName;

        SorterContext<Car> sorter = new SorterContext<>();
        sorter.setStrategy(new BubbleSortStrategy<>());
        CarComparator comparator = new CarComparator();

        System.out.println("--- Этап 1: Random Load ---");
        CustomList<Car> carsRand = DataLoader.loadRandom(5);
        sorter.executeSort(carsRand, comparator);
        SaveSortedToFile.saveSortedToFile(carsRand, fileName);

        System.out.println("\n--- Этап 2: Manual Load ---");
        CustomList<Car> carsManual = DataLoader.loadManual();
        sorter.executeSort(carsManual, comparator);
        SaveSortedToFile.saveSortedToFile(carsManual, fileName);

        System.out.println("\n--- Этап 3: Load From File ---");
        CustomList<Car> carsFromFile = DataLoader.loadFromFile("data/cars.txt");
        sorter.executeSort(carsFromFile, comparator);
        SaveSortedToFile.saveSortedToFile(carsFromFile, fileName);

        System.out.println("\n✅ Тест успешно завершён! Проверьте папку data/" + fileName);

    }
}