package ru.sortproject.test;

import ru.sortproject.model.Car;
import util.ParallelCarCounter;
import ru.sortproject.structure.MyArrayList;

/**
 * Простой ручной тест для многопоточного счетчика
 */
public class MultithreadingTest {

    public static void main(String[] args) {
        System.out.println("ТЕСТИРОВАНИЕ МНОГОПОТОЧНОГО СЧЕТЧИКА");

        int successfulTests = 0;
        int totalTests = 0;

        try {
            // Тест 1: Базовый подсчет
            totalTests++;
            System.out.print("Тест 1: Базовый подсчет вхождений... ");

            Car target = new Car.Builder()
                    .setPower(150)
                    .setModel("Toyota")
                    .setYear(2020)
                    .build();

            MyArrayList<Car> cars = new MyArrayList<>();
            for (int i = 0; i < 10; i++) {
                if (i % 3 == 0) {
                    cars.add(target);
                } else {
                    cars.add(new Car.Builder()
                            .setPower(100 + i)
                            .setModel("Model" + i)
                            .setYear(2010 + i)
                            .build());
                }
            }

            int result = ParallelCarCounter.countOccurrences(cars, target);

            if (result == 4) { // 0, 3, 6, 9 позиции
                System.out.println("УСПЕХ - найдено " + result + " вхождений");
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - ожидалось 4, получено " + result);
            }

            // Тест 2: Подсчет в пустом списке
            totalTests++;
            System.out.print("Тест 2: Подсчет в пустом списке... ");

            MyArrayList<Car> emptyList = new MyArrayList<>();
            result = ParallelCarCounter.countOccurrences(emptyList, target);

            if (result == 0) {
                System.out.println("УСПЕХ - найдено 0 вхождений");
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - ожидалось 0, получено " + result);
            }

            // Тест 3: Все элементы совпадают
            totalTests++;
            System.out.print("Тест 3: Все элементы совпадают... ");

            MyArrayList<Car> allMatch = new MyArrayList<>();
            for (int i = 0; i < 5; i++) {
                allMatch.add(target);
            }

            result = ParallelCarCounter.countOccurrences(allMatch, target);

            if (result == 5) {
                System.out.println("УСПЕХ - найдено " + result + " вхождений");
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - ожидалось 5, получено " + result);
            }

            // Тест 4: Большой список для проверки многопоточности
            totalTests++;
            System.out.print("Тест 4: Большой список (проверка многопоточности)... ");

            int size = 10000;
            MyArrayList<Car> bigList = new MyArrayList<>();
            int expected = 0;

            for (int i = 0; i < size; i++) {
                if (i % 7 == 0) {
                    bigList.add(target);
                    expected++;
                } else {
                    bigList.add(new Car.Builder()
                            .setPower(100 + (i % 500))
                            .setModel("Car" + i)
                            .setYear(2000 + (i % 25))
                            .build());
                }
            }

            long startTime = System.currentTimeMillis();
            result = ParallelCarCounter.countOccurrences(bigList, target);
            long endTime = System.currentTimeMillis();

            if (result == expected) {
                long duration = endTime - startTime;
                System.out.println("УСПЕХ - найдено " + result + " вхождений за " + duration + "мс");
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - ожидалось " + expected +
                        ", получено " + result);
            }

            // Тест 5: Проверка equals для подсчета
            totalTests++;
            System.out.print("Тест 5: Проверка equals для подсчета... ");

            MyArrayList<Car> testList = new MyArrayList<>();
            testList.add(target);
            testList.add(new Car.Builder()
                    .setPower(150)
                    .setModel("Toyota")
                    .setYear(2020)
                    .build()); // Должен совпадать
            testList.add(new Car.Builder()
                    .setPower(150)
                    .setModel("TOYOTA")
                    .setYear(2020)
                    .build()); // Не должен совпадать (разный регистр)

            result = ParallelCarCounter.countOccurrences(testList, target);

            if (result == 2) { // Первые два должны совпадать
                System.out.println("УСПЕХ - equals работает корректно");
                successfulTests++;
            } else {
                System.out.println("НЕУДАЧА - получено " + result + ", ожидалось 2");
            }

        } catch (Exception e) {
            System.out.println("ОШИБКА В ТЕСТАХ: " + e.getMessage());
            e.printStackTrace();
        }

        // Итоги тестирования
        System.out.println("\nИТОГИ ТЕСТИРОВАНИЯ");
        System.out.println("Успешных тестов: " + successfulTests + " из " + totalTests);
        System.out.println("Процент успеха: " + (successfulTests * 100 / totalTests) + "%");
    }
}
