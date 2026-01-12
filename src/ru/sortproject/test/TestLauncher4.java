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

        String directoryPath = "src/ru/sortproject/txtfiles"; // название папки внутри проекта
        File dir = new File(directoryPath);
        File outputFile = new File(dir, "sorted_cars.txt");
        String fullPath = outputFile.getAbsolutePath();

        CustomList<Car> cars = DataLoader.loadRandom(5); // метод loadRandom
        SaveSortedToFile saver = new SaveSortedToFile();
        SorterContext<Car> sorter = new SorterContext<>();
        BubbleSortStrategy<Car> sortCarsBub = new BubbleSortStrategy<>();
        CarComparator comparator = new CarComparator();

        sorter.setStrategy(sortCarsBub);
        sorter.executeSort(cars,comparator);
        saver.saveSortedToFile(cars,fullPath);     // Запись отсортированной коллекции в файл в режиме append

        cars = DataLoader.loadManual();             // Ручной ввод коллекции
        sorter.executeSort(cars,comparator);
        saver.saveSortedToFile(cars,fullPath);

        cars = DataLoader.loadFromFile(fullPath);   //Загрузка коллекции из файла в формате toString
        sorter.executeSort(cars,comparator);
        saver.saveSortedToFile(cars,fullPath);

        System.out.println("Тест завершён!");

    }
}