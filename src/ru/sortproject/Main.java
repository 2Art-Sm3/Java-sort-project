package ru.sortproject;

import ru.sortproject.model.Car;
import ru.sortproject.strategy.*;
import ru.sortproject.structure.CustomList;
import ru.sortproject.structure.MyArrayList;
import ru.sortproject.util.CarComparator;
import ru.sortproject.util.DataLoader;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static CustomList<Car> cars = new MyArrayList<>();
    private static final SorterContext<Car> sorterContext = new SorterContext<Car>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n ГЛАВНОЕ МЕНЮ ");
            System.out.println("1. Загрузить данные");
            System.out.println("2. Показать данные");
            System.out.println("3. Сортировать данные");
            System.out.println("4. Информация о реализованных алгоритмах");
            System.out.println("5. Очистить данные");
            System.out.println("6. Выход");
            System.out.print("Выберите опцию (1-6): ");

            int choice = getMenuChoice(1, 6);

            switch (choice) {
                case 1:
                    loadData();
                    break;
                case 2:
                    displayCars();
                    break;
                case 3:
                    sortData();
                    break;
                case 4:
                    displaySortingAlgorithmsInfo();
                    break;
                case 5:
                    clearData();
                    break;
                case 6:
                    System.out.println("Выход из программы.");
                    return;
            }
        }
    }

    private static void clearData() {
        if (cars.size() > 0) {
            System.out.print("Вы уверены, что хотите очистить все данные? (да/нет): ");
            String answer = in.nextLine().trim().toLowerCase();
            if (answer.equals("да") || answer.equals("yes")) {
                cars = new MyArrayList<>();
                System.out.println("Данные очищены.");
            } else {
                System.out.println("Очистка отменена.");
            }
        } else {
            System.out.println("Очистка отменена.");
        }
    }

    private static void loadData() {
        System.out.println("\nВЫБОР СПОСОБА ВВОДА");
        System.out.println("1. Ручной ввод");
        System.out.println("2. Случайная генерация");
        System.out.println("3. Загрузить из файла");
        System.out.println("4. Отмена");
        System.out.print("Выберите опцию (1-4): ");

        int choice = getMenuChoice(1, 4);

        switch (choice) {
            case 1:
                loadManual();
                break;
            case 2:
                loadRandom();
                break;
            case 3:
                loadFromFile();
                break;
            case 4:
                return;
        }
    }

    private static void loadManual(){
        CustomList<Car> loadedCars = DataLoader.loadManual();
        if (loadedCars.size() > 0) {
            if (cars.size() > 0) {
                System.out.print("\nДобавить к существующим данным? (да/нет): ");
                String answer = in.nextLine().trim().toLowerCase();
                if (answer.equals("да") || answer.equals("yes") || answer.equals("y") || answer.equals("д")) {
                    cars.addAll(loadedCars);
                    System.out.println("Добавлено " + loadedCars.size() + " автомобилей.");
                    System.out.println("Всего автомобилей: " + cars.size());
                } else {
                    cars = loadedCars;
                    System.out.println("Загружено " + cars.size() + " автомобилей.");
                }
            } else {
                cars = loadedCars;
                System.out.println("Загружено " + cars.size() + " автомобилей.");
            }
        } else {
            System.out.println("Не было добавлено ни одного автомобиля.");
        }
    }

    private static void loadFromFile() {
        System.out.print("Введите имя файла (по умолчанию: cars.txt): ");
        String filename = in.nextLine().trim();
        if (filename.isEmpty()) {
            filename = "cars.txt";
        }
    }

    private static void loadRandom() {
        System.out.print("Сколько автомобилей сгенерировать? (1-1000): ");
        int count = getMenuChoice(1, 1000);

        CustomList<Car> loadedCars = DataLoader.loadRandom(count);
        if (loadedCars.size() > 0) {
            if (cars.size() > 0) {
                System.out.print("\nДобавить к существующим данным? (да/нет): ");
                String answer = in.nextLine().trim().toLowerCase();
                if (answer.equals("да") || answer.equals("yes") || answer.equals("y") || answer.equals("д")) {
                    cars.addAll(loadedCars);
                    System.out.println("Добавлено " + loadedCars.size() + " автомобилей.");
                    System.out.println("Всего автомобилей: " + cars.size());
                } else {
                    cars = loadedCars;
                    System.out.println("Сгенерировано " + cars.size() + " автомобилей.");
                }
            } else {
                cars = loadedCars;
                System.out.println("Сгенерировано " + cars.size() + " автомобилей.");
            }
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

    private static void displayCars() {
        if (cars.size() == 0) {
            System.out.println("Нет данных для отображения.");
            return;
        }

        System.out.println("\n СПИСОК АВТОМОБИЛЕЙ");
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            System.out.println((i + 1) + ", " + car.getPower() + ", " + car.getModel() + ", " + car.getYear() + " г.");
        }
        System.out.println("\n");
    }



    private static void sortData() {
        if (cars.size() == 0) {
            System.out.println("Нет данных для сортировки.");
            return;
        }

        System.out.println("\nВЫБОР СТРАТЕГИИ СОРТИРОВКИ");
        System.out.println("1. Сортировка Пузырьковая");
        System.out.println("2. Сортировка Вставкой");
        System.out.println("3. Сортировка Выборкой");
        System.out.println("4. Четно-нечетная сортировка");
        System.out.println("5. Отмена");
        System.out.print("Выберите опцию (1-5): ");

        int choice = getMenuChoice(1, 5);

        // Создаем копию массива для сортировки
        CustomList<Car> carsCopy = copyList(cars);

        switch (choice) {
            case 1:
                sorterContext.setStrategy(new BubbleSortStrategy<>());
                break;
            case  2:
                sorterContext.setStrategy(new SelectionSortStrategy<>());
                break;
            case 3:
                sorterContext.setStrategy(new InsertionSortStrategy<>());
                break;
            case 4:
                sorterContext.setStrategy(new EvenOddSortStrategy());
                break;
            case 5:
                return;
        }
        sorterContext.executeSort(carsCopy, new CarComparator());

        System.out.println("\nРезультат сортировки:");
        for (int i = 0; i < carsCopy.size(); i++) {
            Car car = carsCopy.get(i);
            System.out.println((i + 1) + ". " + car.getPower() + ", " + car.getModel() + ", " + car.getYear() + " г.");
        }
        System.out.println();

        System.out.print("\nХотите заменить текущие данные отсортированными? (да/нет): ");
        String answer = in.nextLine().trim().toLowerCase();
        if (answer.equals("да") || answer.equals("yes")) {
            cars = carsCopy;
            System.out.println("Данные обновлены.");
        }else {
            System.out.println("Данные не изменены.");
        }
    }

    private static CustomList<Car> copyList(CustomList<Car> cars) {
        if (cars == null) {
            return new MyArrayList<>();
        }
        CustomList<Car> copy = new MyArrayList<>();
        for (int i = 0; i < cars.size(); i++) {
            Car originalCar = cars.get(i);
            // Создаем глубокую копию через Builder
            Car copiedCar = new Car.Builder()
                    .setModel(originalCar.getModel())
                    .setPower(originalCar.getPower())
                    .setYear(originalCar.getYear())
                    .build();

            copy.add(copiedCar);
        }
        return copy;
    }


    private static void displaySortingAlgorithmsInfo() {
        System.out.println("\nИНФОРМАЦИЯ О РЕАЛИЗОВАННЫХ АЛГОРИТМАХ");
        System.out.println("\n1. ПАТТЕРНЫ:");
        System.out.println("   - Стратегия (Strategy):");
        System.out.println("     * Интерфейс SortStrategy");
        System.out.println("     * Классы BubbleSortStrategy, SelectionSortStrategy, InsertionSortStrategy, EvenOddSortStrategy");
        System.out.println("   - Строитель (Builder):");
        System.out.println("     * Внутренний класс Builder в классе Car");
        System.out.println("     * Пошаговое создание объектов с валидацией");

        System.out.println("\n2. АЛГОРИТМЫ СОРТИРОВКИ (реализованы вручную):");
        System.out.println("   - Пузырьковая сортировка (BubbleSortStrategy)");
        System.out.println("   - Сортировка Вставкой  (InsertionSortStrategy)");
        System.out.println("   - Сортировка Выборкой  (SelectionSortStrategy)");
        System.out.println("   - Четно-нечетная сортировка (EvenOddSortStrategy)");


        System.out.println("\n3. ВАЛИДАЦИЯ ДАННЫХ:");
        System.out.println("   - Модель: не пустая строка");
        System.out.println("   - Мощность: 1-2000 л.с.");
        System.out.println("   - Год: 1960-2025");

        System.out.println("\n4. ФУНКЦИОНАЛЬНОСТЬ:");
        System.out.println("   - 3 способа ввода данных");
        System.out.println("   - стратегия сортировки");
        System.out.println("   - Работа с файлами");
        System.out.println("   - Валидация всех входных данных");
    }
}