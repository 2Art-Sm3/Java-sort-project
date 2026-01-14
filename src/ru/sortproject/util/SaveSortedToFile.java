package ru.sortproject.util;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SaveSortedToFile {

    private SaveSortedToFile() {}

    public static void saveSortedToFile(CustomList<Car> list, String fileName) {

        String finalName = (fileName == null || fileName.isEmpty()) ? "sorted_cars.txt" : fileName;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalName, true))) {
            for (Car car : list) {
                writer.write(car.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
