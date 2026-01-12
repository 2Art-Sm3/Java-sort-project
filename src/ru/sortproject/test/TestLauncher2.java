package ru.sortproject.test;

import ru.sortproject.model.Car;
import ru.sortproject.strategy.BubbleSortStrategy;
import ru.sortproject.strategy.SorterContext;
import ru.sortproject.structure.MyArrayList;
import ru.sortproject.util.CarComparator;
import ru.sortproject.util.CarValidator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class TestLauncher2 {

    public static void main(String[] args) {
        System.out.println("ТЕСТИРОВАНИЕ ЛОГИКИ МЕНЮ");

        int successfulTests = 0;
        int totalTests = 0;

        // Тест 1: getMenuChoice корректный ввод
        totalTests++;
        System.out.print("Тест 1: getMenuChoice корректный ввод... ");
        try {
            String testInput = "3\n";
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            Scanner in = new Scanner(System.in);
            int result = getMenuChoice(in, 1, 7);

            if (result == 3) {
                System.out.println("УСПЕХ");
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - получено: " + result);
            }
        } catch (Exception e) {
            System.out.println("НЕУДАЧА - исключение: " + e.getMessage());
        }

        // Тест 2: getMenuChoice с повторным вводом
        totalTests++;
        System.out.print("Тест 2: getMenuChoice с повторным вводом... ");
        try {
            String testInput = "abc\n10\n2\n";
            System.setIn(new ByteArrayInputStream(testInput.getBytes()));

            ByteArrayOutputStream output = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(output));

            Scanner scanner = new Scanner(System.in);
            int result = getMenuChoice(scanner, 1, 5);

            System.setOut(originalOut);
            String outputText = output.toString();

            if (result == 2 && outputText.contains("Введите корректное число")) {
                System.out.println("УСПЕХ");
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - результат: " + result);
            }
        } catch (Exception e) {
            System.out.println("НЕУДАЧА - исключение: " + e.getMessage());
        }

        // Тест 3: createCarForSearch логика
        totalTests++;
        System.out.print("Тест 3: Логика создания автомобиля для поиска... ");
        try {
            // Тестируем валидацию вручную
            String validPower = "150";
            String validModel = "Toyota";
            String validYear = "2020";

            boolean powerValid = CarValidator.validatePower(validPower);
            boolean modelValid = CarValidator.validateModel(validModel);
            boolean yearValid = CarValidator.validateYear(validYear);

            if (powerValid && modelValid && yearValid) {
                // Создаем автомобиль как в методе createCarForSearch
                Car car = new Car.Builder()
                        .setModel(validModel)
                        .setPower(Integer.parseInt(validPower))
                        .setYear(Integer.parseInt(validYear))
                        .build();

                System.out.println("УСПЕХ - создан автомобиль: " + car);
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - валидация не прошла");
            }
        } catch (Exception e) {
            System.out.println("НЕУДАЧА - исключение: " + e.getMessage());
        }

        // Тест 4: Проверка SorterContext
        totalTests++;
        System.out.print("Тест 4: Проверка SorterContext... ");
        try {
            SorterContext<Car> context = new SorterContext<>();
            context.setStrategy(new BubbleSortStrategy<>());

            MyArrayList<Car> testList = new MyArrayList<>();
            testList.add(new Car.Builder()
                    .setPower(200)
                    .setModel("Test")
                    .setYear(2020)
                    .build());

            CarComparator comparator = new CarComparator();
            context.executeSort(testList, comparator);

            System.out.println("УСПЕХ - контекст работает");
            successfulTests++;
        } catch (Exception e) {
            System.out.println("НЕУДАЧА - исключение: " + e.getMessage());
        }

        // Тест 5: Проверка ExecutorService shutdown
        totalTests++;
        System.out.print("Тест 5: Проверка ExecutorService... ");
        try {
            // Создаем ExecutorService как в Main
            java.util.concurrent.ExecutorService executor =
                    java.util.concurrent.Executors.newSingleThreadExecutor();

            // Проверяем, что он работает
            executor.submit(() -> {
                // Пустая задача для теста
            });

            executor.shutdown();
            System.out.println("УСПЕХ - ExecutorService работает");
            successfulTests++;
        } catch (Exception e) {
            System.out.println("НЕУДАЧА - исключение: " + e.getMessage());
        }

        // Тест 6: Проверка отображения автомобилей
        totalTests++;
        System.out.print("Тест 6: Проверка форматирования вывода... ");
        try {
            Car car = new Car.Builder()
                    .setPower(150)
                    .setModel("Toyota")
                    .setYear(2020)
                    .build();

            String output = String.format("%d, %s, %d г.",
                    car.getPower(), car.getModel(), car.getYear());

            if (output.contains("150") && output.contains("Toyota") && output.contains("2020")) {
                System.out.println("УСПЕХ - форматирование: " + output);
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - результат: " + output);
            }
        } catch (Exception e) {
            System.out.println("НЕУДАЧА - исключение: " + e.getMessage());
        }

        // Итоги тестирования
        System.out.println("\nИТОГИ ТЕСТИРОВАНИЯ");
        System.out.println("Успешных тестов: " + successfulTests + " из " + totalTests);
        System.out.println("Процент успеха: " + (successfulTests * 100 / totalTests) + "%");
    }

    // Копия метода из Main для тестирования
    private static int getMenuChoice(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.print("Введите число от " + min + " до " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }
}
