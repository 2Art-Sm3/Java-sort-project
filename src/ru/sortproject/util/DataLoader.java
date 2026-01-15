package ru.sortproject.util;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;
import ru.sortproject.structure.MyArrayList;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class DataLoader {

    private DataLoader() {}

    private static final Scanner scanner = new Scanner(System.in);

    public static CustomList<Car> loadFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            return br.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(line -> {
                        if (line.startsWith("Car{") && line.endsWith("}")) {
                            String content = line.substring(line.indexOf('{') + 1, line.lastIndexOf('}')).trim();
                            String[] parts = content.split(",\\s*");
                            if (parts.length != 3) return null;

                            String powerStr = null;
                            String model = null;
                            String yearStr = null;

                            for (String part : parts) {
                                String[] keyValue = part.split("=");
                                if (keyValue.length != 2) return null;
                                String key = keyValue[0].trim();
                                String value = keyValue[1].trim();

                                switch (key) {
                                    case "power":
                                        powerStr = value;
                                        break;
                                    case "model":
                                        if (value.startsWith("'") && value.endsWith("'") && value.length() >= 2) {
                                            model = value.substring(1, value.length() - 1);
                                        } else {
                                            return null;
                                        }
                                        break;
                                    case "year":
                                        yearStr = value;
                                        break;
                                    default:
                                        return null;
                                }
                            }

                            if (powerStr != null && model != null && yearStr != null) {
                                if (CarValidator.validatePower(powerStr) &&
                                        CarValidator.validateModel(model) &&
                                        CarValidator.validateYear(yearStr)) {
                                    return new Car.Builder()
                                            .setPower(Integer.parseInt(powerStr))
                                            .setModel(model)
                                            .setYear(Integer.parseInt(yearStr))
                                            .build();
                                }
                            }
                            return null;
                        } else {
                            String[] parts = line.split(",\\s*");
                            if (parts.length != 3) return null;

                            String powerStr = parts[0];
                            String model = parts[1];
                            String yearStr = parts[2];

                            if (powerStr != null && model != null && yearStr != null) {
                                if (CarValidator.validatePower(powerStr) &&
                                        CarValidator.validateModel(model) &&
                                        CarValidator.validateYear(yearStr)) {
                                    return new Car.Builder()
                                            .setPower(Integer.parseInt(powerStr))
                                            .setModel(model)
                                            .setYear(Integer.parseInt(yearStr))
                                            .build();
                                }
                            }
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(MyArrayListCollector.carCollector());
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return new MyArrayList<>();
        }
    }

    public static CustomList<Car> loadManual() {
        int length = readLength();

        return IntStream.range(0, length)
                .mapToObj(i -> readCarFromConsole(i))
                .collect(MyArrayListCollector.carCollector());
    }

    public static CustomList<Car> loadRandom(int count) {
        Random rand = new Random();
        return IntStream.range(0, count)
                .mapToObj(i -> new Car.Builder()
                        .setPower(rand.nextInt(1000))
                        .setModel("Модель " + rand.nextInt(10000))
                        .setYear(rand.nextInt(65) + 1960)
                        .build())
                .collect(MyArrayListCollector.carCollector());
    }

    private static Car readCarFromConsole(int index) {
        System.out.println("Введите данные для автомобиля #" + (index + 1));
        String powerStr, model, yearStr;

        do {
            System.out.print("Мощность: ");
            powerStr = scanner.nextLine();
            if (!CarValidator.validatePower(powerStr)) {
                System.out.println("Некорректное значение мощности. Попробуйте еще раз.");
            }
        } while (!CarValidator.validatePower(powerStr));

        do {
            System.out.print("Модель: ");
            model = scanner.nextLine();
            if (!CarValidator.validateModel(model)) {
                System.out.println("Некорректное название. Попробуйте еще раз.");
            }
        } while (!CarValidator.validateModel(model));

        do {
            System.out.print("Год производства: ");
            yearStr = scanner.nextLine();
            if (!CarValidator.validateYear(yearStr)) {
                System.out.println("Некорректный формат года. Попробуйте еще раз.");
            }
        } while (!CarValidator.validateYear(yearStr));

        return new Car.Builder()
                .setPower(Integer.parseInt(powerStr))
                .setModel(model)
                .setYear(Integer.parseInt(yearStr))
                .build();
    }

    private static int readLength() {
        while (true) {
            System.out.print("Введите количество автомобилей: ");
            String input = scanner.nextLine();
            try {
                int length = Integer.parseInt(input);
                if (length > 0) return length;
                System.out.println("Длина должна быть положительным числом.");
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Попробуйте еще раз.");
            }
        }
    }
}
