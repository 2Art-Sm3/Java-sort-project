package ru.sortproject.util;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class SaveSortedToFile {

    private SaveSortedToFile() {}

    public static void saveSortedToFile(CustomList<Car> list, String fileName) {

        String name = (fileName == null || fileName.isEmpty()) ? "sorted_cars.txt" : fileName;
        String finalPath = "data/" + name;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(finalPath, true))) {
            writer.newLine();
            writer.write("--- Запись от " + LocalDateTime.now() + " ---");
            writer.newLine();

            for (Car car : list) {
                writer.write(car.toString());
                writer.newLine();
            }
            writer.write("------------------------------------------");
            writer.newLine();
            System.out.println("Данные добавлены в файл: " + finalPath);
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }
    }
}
