package ru.sortproject;

import ru.sortproject.model.Car;
import  ru.sortproject.strategy.SortStrategy;


import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    public static Car[] cars = new Car[0];
    private static SorterContext<Car> sorterContext = new SorterContext<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("=========================================");
            System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
            System.out.println("1. Загрузить данные");
            System.out.println("2. Показать данные");
            System.out.println("3. Сортировать данные");
            System.out.println("4. Параллельная сортировка");
            System.out.println("5. Сохранить в файл");
            System.out.println("6. Информация о реализованных алгоритмах");
            System.out.println("7. Выход");
            System.out.print("Выберите опцию (1-7): ");

            int choice = getMenuChoice(1, 7);

            switch (choice) {
                case 1:
                    loadData();
                    break;
                case 2:
                    displayCars(cars);
                    break;
                case 3:
                    sortData();
                    break;
                case 4:
                    parallelSort();
                    break;
                case 5:
                    saveToFile();
                    break;
                case 6:
                    displaySortingAlgorithmsInfo();
                    break;
                case 7:
                    System.out.println("Выход из программы.");
                    return;
            }
        }
    }

    private static void loadData() {
        System.out.println("\n=== ВЫБОР СПОСОБА ВВОДА ===");
        System.out.println("1. Ручной ввод");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Загрузить из файла");
        System.out.println("4. Отмена");
        System.out.print("Выберите опцию (1-4): ");

        int choice = getMenuChoice(1, 4);

        switch (choice) {
            case 1:
                int size = inputArraySize();
                cars = inputDataManually(size);
                break;
            case 2:
                int randomSize = inputArraySize();
                cars = generateRandomData(randomSize);
                break;
            case 3:
                System.out.print("Введите имя файла: ");
                String fileName = in.nextLine();
                Car[] loadedCars = readFromFile(fileName);
                if (loadedCars.length == 0) {
                    System.out.println("Не удалось загрузить данные из файла!");
                } else {
                    cars = loadedCars;
                    System.out.println("Загружено " + cars.length + " записей");
                }
                break;
            case 4:
                return;
        }
    }

    private static int getMenuChoice(int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(in.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.print("Введите число от " + min + " до " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }

    private static int inputArraySize() {
        System.out.print("Введите количество автомобилей: ");
        while (true) {
            try {
                int size = Integer.parseInt(in.nextLine());
                if (size > 0) {
                    return size;
                }
                System.out.print("Количество должно быть больше 0: ");
            } catch (NumberFormatException e) {
                System.out.print("Введите корректное число: ");
            }
        }
    }

    private static Car[] inputDataManually(int size) {
        Car[] result = new Car[size];
        for (int i = 0; i < size; i++) {
            System.out.println("\n=== Ввод данных автомобиля " + (i + 1) + " ===");

            System.out.print("Модель: ");
            String model = in.nextLine();

            System.out.print("Мощность (л.с.): ");
            int power = Integer.parseInt(in.nextLine());

            System.out.print("Год выпуска: ");
            int year = Integer.parseInt(in.nextLine());

            result[i] = new Car(model, power, year);
        }
        return result;
    }

    private static Car[] generateRandomData(int size) {
        Car[] result = new Car[size];
        String[] models = {"Toyota", "Honda", "Ford", "BMW", "Mercedes", "Audi", "Volkswagen", "Tesla"};

        for (int i = 0; i < size; i++) {
            String model = models[(int)(Math.random() * models.length)];
            int power = 50 + (int)(Math.random() * 4951); // 50-5000 л.с.
            int year = 1990 + (int)(Math.random() * 35); // 1990-2024

            result[i] = new Car(model, power, year);
        }
        return result;
    }

    private static Car[] readFromFile(String filename) {
        // Здесь должна быть реализация чтения из файла
        // Возвращаем пустой массив в качестве заглушки
        return new Car[0];
    }

    private static void displayCars(Car[] carsToDisplay) {
        if (carsToDisplay == null || carsToDisplay.length == 0) {
            System.out.println("Нет данных для отображения.");
            return;
        }

        System.out.println("\n=== СПИСОК АВТОМОБИЛЕЙ ===");
        for (int i = 0; i < carsToDisplay.length; i++) {
            System.out.println((i + 1) + ". " + carsToDisplay[i]);
        }
        System.out.println("==========================");
    }

    private static void sortData() {
        if (cars == null || cars.length == 0) {
            System.out.println("Нет данных для сортировки.");
            return;
        }

        System.out.println("\n=== ВЫБОР СТРАТЕГИИ СОРТИРОВКИ ===");
        System.out.println("1. Сортировка по мощности Пузырьковая");
        System.out.println("2. Отмена");
        System.out.print("Выберите опцию (1-2): ");

        int choice = getMenuChoice(1, 2);

        // Создаем копию массива для сортировки
        Car[] carsToSort = copyArray(cars);

        switch (choice) {
            case 1:
                sorterContext.setStrategy(new BubbleSortStrategy<>());
                break;
            case 2:
                return;
        }
        System.out.println("\nРезультат сортировки:");
        displayCars(carsToSort);

        System.out.print("\nХотите заменить текущие данные отсортированными? (да/нет): ");
        String answer = in.nextLine().trim().toLowerCase();
        if (answer.equals("да") || answer.equals("yes")) {
            cars = carsToSort;
            System.out.println("Данные обновлены.");
        }
    }

    private static void parallelSort() {
        if (cars == null || cars.length == 0) {
            System.out.println("Нет данных для сортировки.");
            return;
        }

        // Создаем копии для параллельной сортировки
        Car[] byPower = copyArray(cars);
        Car[] byModel = copyArray(cars);
        Car[] byYear = copyArray(cars);

        // Выполняем сортировку
        System.out.println("\n1. Сортировка:");
        new BubbleSortStrategy().sort(byPower);
        displayCars(byPower);
    }

    private static Car[] copyArray(Car[] original) {
        Car[] copy = new Car[original.length];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i];
        }
        return copy;
    }

    private static void saveToFile() {
        if (cars == null || cars.length == 0) {
            System.out.println("\nНет данных для сохранения.");
            return;
        }

        System.out.print("\nВведите имя файла для сохранения (по умолчанию: cars.txt): ");
        String filename = in.nextLine().trim();
        if (filename.isEmpty()) {
            filename = "cars.txt";
        }

        // Здесь должна быть реализация сохранения в файл
        System.out.println("Сохранение в файл " + filename + " (заглушка)");
    }

    private static void displaySortingAlgorithmsInfo() {
        System.out.println("\n=== ИНФОРМАЦИЯ О РЕАЛИЗОВАННЫХ АЛГОРИТМАХ ===");
        System.out.println("\n1. ПАТТЕРНЫ:");
        System.out.println("   - Стратегия (Strategy):");
        System.out.println("     * Интерфейс SortStrategy");
        System.out.println("     * Классы BubbleSortStrategy, SelectionSortStrategy, InsertionSortStrategy");
        System.out.println("   - Строитель (Builder):");
        System.out.println("     * Внутренний класс CarBuilder в классе Car");
        System.out.println("     * Пошаговое создание объектов с валидацией");

        System.out.println("\n2. АЛГОРИТМЫ СОРТИРОВКИ (реализованы вручную):");
        System.out.println("   - Пузырьковая сортировка (BubbleSortStrategy)");

        System.out.println("\n3. ВАЛИДАЦИЯ ДАННЫХ:");
        System.out.println("   - Модель: не пустая строка");
        System.out.println("   - Мощность: 1-2000 л.с.");
        System.out.println("   - Год: 1886-текущий год+1");

        System.out.println("\n4. ФУНКЦИОНАЛЬНОСТЬ:");
        System.out.println("   - 3 способа ввода данных");
        System.out.println("   - стратегия сортировки");
        System.out.println("   - Работа с файлами");
        System.out.println("   - Валидация всех входных данных");
    }
}

