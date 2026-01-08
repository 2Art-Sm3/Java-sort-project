package ru.sortproject.util;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;
import ru.sortproject.structure.MyArrayList;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class DataLoader {
    private static final Scanner scanner = new Scanner(System.in);

    public static CustomList<Car> loadFromFile(String filename) {

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            return br.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length != 3) return null;
                        String power = parts[0].trim();
                        String model = parts[1].trim();
                        String year = parts[2].trim();

                        if (CarValidator.validatePower(power) &&
                                CarValidator.validateModel(model) &&
                                CarValidator.validateYear(year)) {
                            return new Car.Builder()
                                    .setPower(Integer.parseInt(power))
                                    .setModel(model)
                                    .setYear(Integer.parseInt(year))
                                    .build();
                        }
                        return null;
                    })
                    .collect(MyArrayListCollector.carCollector());

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return new MyArrayList<>();
        }
    }

    public static CustomList<Car> loadManual() {
        CustomList<Car> cars = new MyArrayList<>();
        System.out.print("Введите длину массива: ");
        int length = 0;
        while (true) {
            System.out.print("Введите длину массива: ");
            String input = scanner.nextLine();
            try {
                length = Integer.parseInt(input);
                if (length > 0) break;
                else System.out.println("Длина должна быть положительным числом");
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Попробуйте еще раз.");
            }
        }

        IntStream.range(0, length).forEach(i -> {
            System.out.println("Введите данные для автомобиля #" + (i + 1));
            String powerStr, model, yearStr;

            do {
                System.out.print("Мощность: ");
                powerStr = scanner.nextLine();
                if (!CarValidator.validatePower(powerStr)) {
                    System.out.println("Некорректное значение мощности. Попробуйте еще раз.");
                }
            } while (!CarValidator.validatePower(powerStr));
            int power = Integer.parseInt(powerStr);

            do {
                System.out.print("Модель: ");
                model = scanner.nextLine();
            } while (!CarValidator.validateModel(model));

            do {
                System.out.print("Год производства: ");
                yearStr = scanner.nextLine();
                if (!CarValidator.validateYear(yearStr)) {
                    System.out.println("Некорректный формат. Попробуйте еще раз.");
                }
            } while (!CarValidator.validateYear(yearStr));
            int year = Integer.parseInt(yearStr);

            cars.add(new Car.Builder()
                    .setPower(power)
                    .setModel(model)
                    .setYear(year)
                    .build());
        });
        return cars;
    }

    public static CustomList<Car> loadRandom(int count) {
        CustomList<Car> cars = new MyArrayList<>();
        Random rand = new Random();
        for (int i=0; i<count; i++) {
            int power = rand.nextInt(1000);
            String model = "Модель " + rand.nextInt(10000);
            int year = rand.nextInt(2015);
            cars.add(new Car.Builder()
                    .setPower(power)
                    .setModel(model)
                    .setYear(year)
                    .build());
        }
        return cars;
    }
}
