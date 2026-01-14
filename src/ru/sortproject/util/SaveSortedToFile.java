package ru.sortproject.util;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveSortedToFile {
    public static void saveSortedToFile(CustomList<Car> list, String filename) {

        File file = new File(filename);
        String outputFilename;

        if (!file.exists()) {
            outputFilename = "data/sorted_cars.txt";
            file = new File(outputFilename);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            for (Car car : list) {
                writer.write(car.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
