package ru.sortproject;

import ru.sortproject.model.Car;
import ru.sortproject.strategy.SortStrategy;

import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static Car[] cars = new Car[0];
    private static SortStrategy currentStrategy;

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("    ПРИЛОЖЕНИЕ ДЛЯ СОРТИРОВКИ АВТОМОБИЛЕЙ");
        System.out.println("=========================================");
        System.out.println("Все алгоритмы реализованы вручную:");
        System.out.println("- Паттерн Стратегия");
        System.out.println("- Паттерн Строитель");
        System.out.println("- Алгоритмы сортировки");
        System.out.println("=========================================\n");

        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = getMenuChoice(1, 8);

            switch (choice) {
                case 1:
                    cars = selectInputMethod();
                    break;
                case 2:
                    displayCars(cars);
                    break;
                case 3:
                    performSorting();
                    break;
                case 4:
                    displaySortedByAllFields();
                    break;
                case 5:
                    saveToFile();
                    break;
                case 6:
                    loadFromFile();
                    break;
                case 7:
                    displaySortingAlgorithmsInfo();
                    break;
                case 8:
                    running = false;
                    System.out.println("\nСпасибо за использование приложения!");
                    System.out.println("Все алгоритмы были реализованы вручную.");
                    break;
            }
        }

        in.close();
    }

    private static void printMainMenu() {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ ===");
        System.out.println("1. Ввести/сгенерировать данные");
        System.out.println("2. Показать данные");
        System.out.println("3. Выполнить сортировку");
        System.out.println("4. Показать сортировку по всем полям");
        System.out.println("5. Сохранить данные в файл");
        System.out.println("6. Загрузить данные из файла");
        System.out.println("7. Информация об алгоритмах");
        System.out.println("8. Выход");
        System.out.print("Выберите опцию (1-8): ");
    }

    private static int getMenuChoice(int min, int max) {
        while (true) {
            try {
                int choice = Integer.parseInt(in.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.printf("Введите число от %d до %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.print("Некорректный ввод. Введите число: ");
            }
        }
    }

    private static Car[] selectInputMethod() {
        System.out.println("\n=== ВЫБОР СПОСОБА ВВОДА ===");
        System.out.println("1. Ручной ввод");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Отмена");
        System.out.print("Выберите опцию (1-3): ");

        int choice = getMenuChoice(1, 3);

        /*switch (choice) {
            case 1:
                int size = DataInputService.inputArraySize();
                return DataInputService.inputDataManually(size);
            case 2:
                int randomSize = DataInputService.inputArraySize();
                return DataInputService.generateRandomData(randomSize);
            default:
                return cars; // Возвращаем текущие данные
        }*/
    }

    private static void displayCars(Car[] cars) {
        if (cars == null || cars.length == 0) {
            System.out.println("\nНет данных для отображения. Сначала введите данные.");
            return;
        }

        System.out.println("\n=== СПИСОК АВТОМОБИЛЕЙ ===");
        System.out.println("№  | Модель              | Мощность | Год");
        System.out.println("---|---------------------|----------|-----");

        for (int i = 0; i < cars.length; i++) {
            Car car = cars[i];
            System.out.printf("%-3d| %-20s| %-9d| %-4d%n",
                    i + 1, car.getModel(), car.getPower(), car.getYear());
        }
        System.out.println("Всего автомобилей: " + cars.length);
    }

    private static void performSorting() {
        if (cars == null || cars.length == 0) {
            System.out.println("\nНет данных для сортировки. Сначала введите данные.");
            return;
        }

        System.out.println("\n=== ВЫБОР СТРАТЕГИИ СОРТИРОВКИ ===");
        System.out.println("1. Сортировка по мощности (Пузырьковая)");
        System.out.println("2. Сортировка по модели (Выбором)");
        System.out.println("3. Сортировка по году (Вставками)");
        System.out.println("4. Отмена");
        System.out.print("Выберите опцию (1-4): ");

        int choice = getMenuChoice(1, 4);

        // Создаем копию массива для сортировки
        Car[] carsToSort = new Car[cars.length];
        for (int i = 0; i < cars.length; i++) {
            carsToSort[i] = cars[i];
        }

        /*switch (choice) {
            case 1:
                currentStrategy = new SortStrategy();
                currentStrategy.sort(carsToSort);
                break;
            case 2:
                currentStrategy = new SortStrategy();
                currentStrategy.sort(carsToSort);
                break;
            case 3:
                currentStrategy = new SortStrategy();
                currentStrategy.sort(carsToSort);
                break;
            default:
                return;
        }*/

        //System.out.println("\n" + currentStrategy.getSortStrategy());
        displayCars(carsToSort);

        System.out.print("\nХотите заменить текущие данные отсортированными? (да/нет): ");
        String response = in.nextLine().trim().toLowerCase();
        if (response.equals("да") || response.equals("yes") || response.equals("y")) {
            cars = carsToSort;
            System.out.println("Данные обновлены.");
        }
    }

    private static void displaySortedByAllFields() {
        if (cars == null || cars.length == 0) {
            System.out.println("\nНет данных для сортировки. Сначала введите данные.");
            return;
        }

        System.out.println("\n=== СОРТИРОВКА ПО ВСЕМ ПОЛЯМ ===");

        // Создаем копии для разных сортировок
        Car[] byPower = copyArray(cars);
        Car[] byModel = copyArray(cars);
        Car[] byYear = copyArray(cars);

        // Выполняем все три сортировки
       /* SortStrategy powerStrategy = new PowerSortingStrategy();
        SortStrategy modelStrategy = new ModelSortingStrategy();
        SortStrategy yearStrategy = new YearSortingStrategy();*/

        System.out.println("\n1. Сортировка по мощности:");
        //powerStrategy.sort(byPower);
        displayCars(byPower);

        System.out.println("\n2. Сортировка по модели:");
        //modelStrategy.sort(byModel);
        displayCars(byModel);

        System.out.println("\n3. Сортировка по году:");
        //yearStrategy.sort(byYear);
        displayCars(byYear);
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

        DataReader.saveToFile(cars, filename);
    }

    private static void loadFromFile() {
        System.out.print("\nВведите имя файла для загрузки (по умолчанию: cars.txt): ");
        String filename = in.nextLine().trim();
        if (filename.isEmpty()) {
            filename = "cars.txt";
        }

        Car[] loadedCars = DataReader.readFromFile(filename);
        if (loadedCars.length > 0) {
            cars = loadedCars;
            System.out.println("Загружено " + loadedCars.length + " автомобилей.");
        }
    }

    private static void displaySortingAlgorithmsInfo() {
        System.out.println("\n=== ИНФОРМАЦИЯ О РЕАЛИЗОВАННЫХ АЛГОРИТМАХ ===");
        System.out.println("\n1. ПАТТЕРНЫ:");
        System.out.println("   - Стратегия (Strategy):");
        System.out.println("     * Интерфейс SortingStrategy");
        System.out.println("     * Классы PowerSortingStrategy, ModelSortingStrategy, YearSortingStrategy");
        System.out.println("   - Строитель (Builder):");
        System.out.println("     * Внутренний класс CarBuilder в классе Car");
        System.out.println("     * Пошаговое создание объектов с валидацией");

        System.out.println("\n2. АЛГОРИТМЫ СОРТИРОВКИ (реализованы вручную):");
        System.out.println("   - Пузырьковая сортировка (PowerSortingStrategy)");
        System.out.println("   - Сортировка выбором (ModelSortingStrategy)");
        System.out.println("   - Сортировка вставками (YearSortingStrategy)");

        System.out.println("\n3. ВАЛИДАЦИЯ ДАННЫХ:");
        System.out.println("   - Модель: не пустая строка");
        System.out.println("   - Мощность: 1-2000 л.с.");
        System.out.println("   - Год: 1886-текущий год+1");

        System.out.println("\n4. ФУНКЦИОНАЛЬНОСТЬ:");
        System.out.println("   - 3 способа ввода данных");
        System.out.println("   - 3 стратегии сортировки");
        System.out.println("   - Работа с файлами");
        System.out.println("   - Валидация всех входных данных");
    }
}
