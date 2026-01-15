package ru.sortproject;

import ru.sortproject.model.Car;
import ru.sortproject.strategy.*;
import ru.sortproject.structure.CustomList;
import ru.sortproject.structure.MyArrayList;
import ru.sortproject.util.*;

import java.io.File;
import java.util.Scanner;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static CustomList<Car> cars = new MyArrayList<>();
    private static final SorterContext<Car> sorterContext = new SorterContext<>();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n ГЛАВНОЕ МЕНЮ ");
            System.out.println("1. Загрузить данные");
            System.out.println("2. Показать данные");
            System.out.println("3. Сортировать данные");
            System.out.println("4. Многопоточный подсчет автомобилей");
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
                    countCarsParallel();
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

    private static void countCarsParallel() {
        if (cars.size() == 0) {
            System.out.println("Нет данных для подсчета.");
            return;
        }
        System.out.println("\nМНОГОПОТОЧНЫЙ ПОДСЧЕТ АВТОМОБИЛЕЙ");
        System.out.println("=".repeat(40));
        System.out.println("\nВыберите автомобиль для подсчета:");
        System.out.println("1. Задать автомобиль для поиска вручную");
        System.out.println("2. Отмена");
        System.out.print("Выберите опцию (1-2): ");

        int choice = getMenuChoice(1, 2);
        Car targetCar = null;

        switch (choice) {
            case 1:
                targetCar = createCarForSearch();
                if (targetCar == null) {
                    System.out.println("Ошибка создания автомобиля.");
                    return;
                }
                break;
            case 2:
                return;
        }
        if (targetCar == null) {
            System.out.println("Не выбран автомобиль для подсчета.");
            return;
        }

        int count = ParallelCarCounter.countOccurrences(cars, targetCar);
        System.out.println("\nРЕЗУЛЬТАТ ПОДСЧЕТА:");
        System.out.println("=".repeat(40));
        System.out.println("Искомый автомобиль: " + targetCar);
        System.out.println("Размер коллекции: " + cars.size());
        System.out.println("Найдено вхождений: " + count);

        if (count > 0) {
            CustomList<Car> foundCars = new MyArrayList<>();
            for (Car c : cars) {
                if (c.equals(targetCar)) foundCars.add(c);
            }
            System.out.print("Показать найденные авто на экране? (да/нет): ");
            String showAnswer = in.nextLine().trim().toLowerCase();

            if (showAnswer.equals("да") || showAnswer.equals("yes")) {
                System.out.printf("%-4s | %-12s | %-10s | %-20s | %-10s\n", "№", "Индекс", "Мощность", "Модель", "Год");
                System.out.println("-".repeat(65));

                int displayCounter = 1;

                for (int i = 0; i < cars.size(); i++) {
                    Car current = cars.get(i);
                    if (current.equals(targetCar)) {
                        System.out.printf("%-4d | %-12d | %-7d л.с. | %-20s | %d г.\n",
                                displayCounter++, i, current.getPower(), current.getModel(), current.getYear());
                    }
                }
                System.out.println("-".repeat(65));
            }
            System.out.print("\nСохранить найденные результаты в файл? (да/нет): ");
            String saveAnswer = in.nextLine().trim().toLowerCase();
            if (saveAnswer.equals("да") || saveAnswer.equals("yes")) {
                System.out.print("Введите имя файла (Enter для 'found_cars.txt'): ");
                String filename = in.nextLine().trim();
                if (filename.isEmpty()) filename = "found_cars.txt";
                SaveSortedToFile.saveSortedToFile(foundCars, filename);
            }
        }
    }
    //Поиск Авто
    private static Car createCarForSearch() {
        System.out.println("\nСОЗДАНИЕ АВТОМОБИЛЯ ДЛЯ ПОИСКА");
        System.out.println("-".repeat(30));

        try {
            String powerStr, model, yearStr;

            do {
                System.out.print("Мощность: ");
                powerStr = in.nextLine();
                if (!CarValidator.validatePower(powerStr)) {
                    System.out.println("Некорректная мощность");
                }
            } while (!CarValidator.validatePower(powerStr));
            int power = Integer.parseInt(powerStr);

            do {
                System.out.print("Модель: ");
                model = in.nextLine();
                if (!CarValidator.validateModel(model)) {
                    System.out.println("Модель не может быть пустой.");
                }
            } while (!CarValidator.validateModel(model));

            do {
                System.out.print("Год выпуска: ");
                yearStr = in.nextLine();
                if (!CarValidator.validateYear(yearStr)) {
                    System.out.println("Некорректный год. Допустимо: 1960-2025");
                }
            } while (!CarValidator.validateYear(yearStr));
            int year = Integer.parseInt(yearStr);

            Car car = new Car.Builder()
                    .setModel(model)
                    .setPower(power)
                    .setYear(year)
                    .build();

            System.out.println("Создан автомобиль для поиска: " + car);
            return car;
        } catch (Exception e) {
            System.out.println("Ошибка создания автомобиля: " + e.getMessage());
            return null;
        }
    }

    private static void clearData() {
        if (cars.size() == 0) {
            System.out.println("Коллекция уже пуста.");
            return;
        }
        System.out.print("Вы уверены, что хотите очистить все данные (" + cars.size() + " шт.)? (да/нет): ");
        String answer = in.nextLine().trim().toLowerCase();

        if (answer.equals("да") || answer.equals("yes")) {
            cars.clear();
            System.out.println("Данные успешно очищены.");
        } else {
            System.out.println("Очистка отменена пользователем.");
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
        boolean addToExisting = false;
        if (cars.size() > 0) {
            System.out.print("\nТекущая коллекция содержит " + cars.size() + " автомобилей.");
            System.out.print(" Добавить к существующим? (да/нет): ");
            String answer = in.nextLine().trim().toLowerCase();
            addToExisting = answer.equals("да") || answer.equals("yes");
        }

        CustomList<Car> loadedCars = null;

        switch (choice) {
            case 1:
                loadedCars = DataLoader.loadManual();
                break;
            case 2:
                System.out.print("Сколько автомобилей сгенерировать? (1-10000): ");
                int count = getMenuChoice(1, 10000);
                loadedCars = DataLoader.loadRandom(count);
                break;
            case 3:
                System.out.print("Введите имя файла (по умолчанию cars.txt): ");
                String filename = in.nextLine().trim();
                if (filename.isEmpty()) filename = "cars.txt";

                String fullPath = "data/" + filename;

                File file = new File(fullPath);
                if(!file.exists()) {
                    System.out.println("Файл не найден по адресу: " + file.getAbsolutePath());
                    return;
                }
                loadedCars = DataLoader.loadFromFile(fullPath);
                break;
            case 4:
                return;
        }
        if (loadedCars != null && loadedCars.size() > 0) {
            if (addToExisting) {
                cars.addAll(loadedCars);
                System.out.println("\nДобавлено " + loadedCars.size() + " автомобилей.");
                System.out.println("  Всего автомобилей: " + cars.size());
            } else {
                cars = loadedCars;
                System.out.println("\nЗагружено " + cars.size() + " автомобилей.");
            }
        } else {
            System.out.println("\nНе удалось загрузить данные.");
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
        System.out.printf("%-4s | %-10s | %-20s | %-10s\n", "№", "Мощность", "Модель", "Год");
        System.out.println("-".repeat(50));
        int counter = 1;
        for (Car car : cars) {
            System.out.printf("%-4d | %-7d л.с. | %-20s | %d г.\n",
                    counter++,
                    car.getPower(),
                    car.getModel(),
                    car.getYear());
        }
        System.out.println("-".repeat(50) + "\n");
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
        CustomList<Car> carsCopy = CarListUtil.copyList(cars);

        switch (choice) {
            case 1:
                sorterContext.setStrategy(new BubbleSortStrategy<>());
                break;
            case  2:
                sorterContext.setStrategy(new InsertionSortStrategy<>());
                break;
            case 3:
                sorterContext.setStrategy(new SelectionSortStrategy<>());
                break;
            case 4:
                sorterContext.setStrategy(new EvenOddSortStrategy());
                break;
            case 5:
                return;
        }
        sorterContext.executeSort(carsCopy, new CarComparator());

        System.out.println("\n РЕЗУЛЬТАТ СОРТИРОВКИ:");
        System.out.printf("%-4s | %-10s | %-20s | %-10s\n", "№", "Мощность", "Модель", "Год");
        System.out.println("-".repeat(50));

        int counter = 1;
        for (Car car : carsCopy) {
            System.out.printf("%-4d | %-7d л.с. | %-20s | %d г.\n",
                    counter++, car.getPower(), car.getModel(), car.getYear());
        }
        System.out.println("-".repeat(50));

        System.out.print("\nСохранить отсортированные данные в файл? (да/нет): ");
        String saveAnswer = in.nextLine().trim().toLowerCase();

        if (saveAnswer.equals("да") || saveAnswer.equals("yes")) {
            System.out.print("Введите имя файла (Enter для 'sorted_cars.txt'): ");
            String filename = in.nextLine().trim();
            SaveSortedToFile.saveSortedToFile(carsCopy, filename);
        }
    }
}