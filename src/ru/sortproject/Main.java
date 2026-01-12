package ru.sortproject;

import ru.sortproject.model.Car;
import ru.sortproject.strategy.*;
import ru.sortproject.structure.CustomList;
import ru.sortproject.structure.MyArrayList;
import ru.sortproject.test.*;
import ru.sortproject.util.CarComparator;
import ru.sortproject.util.CarValidator;
import ru.sortproject.util.DataLoader;
import ru.sortproject.util.ParallelCarCounter;
import ru.sortproject.util.SaveSortedToFile;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final Scanner in = new Scanner(System.in);
    private static CustomList<Car> cars = new MyArrayList<>();
    private static final SorterContext<Car> sorterContext = new SorterContext<Car>();
    private static final ExecutorService backgroundExecutor = Executors.newSingleThreadExecutor();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n ГЛАВНОЕ МЕНЮ ");
            System.out.println("1. Загрузить данные");
            System.out.println("2. Показать данные");
            System.out.println("3. Сортировать данные");
            System.out.println("4. Многопоточный подсчет автомобилей");
            System.out.println("5. Информация о реализованных алгоритмах");
            System.out.println("6. Очистить данные");
            System.out.println("7. Запустить тесты");
            System.out.println("8. Выход");
            System.out.print("Выберите опцию (1-8): ");

            int choice = getMenuChoice(1, 8);

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
                    displaySortingAlgorithmsInfo();
                    break;
                case 6:
                    clearData();
                    break;
                case 7:
                    runAllTests();
                    break;
                case 8:
                    System.out.println("Выход из программы.");
                    shutdownExecutor(); // Закрываем executor
                    return;
            }
        }
    }

    private static void runAllTests() {
        System.out.println("\nЗАПУСК ВСЕХ ТЕСТОВ ");

        try {
            TestLauncher1.main(new String[]{});
            System.out.println("\n" + "=".repeat(50));
            TestLauncher2.main(new String[]{});
            System.out.println("\n" + "=".repeat(50));
            TestLauncher3.main(new String[]{});
            System.out.println("\n" + "=".repeat(50));
            TestLauncher4.main(new String[]{});
            System.out.println("\n" + "=".repeat(50));
            MultithreadingTest.main(new String[]{});

            System.out.println("\nВСЕ ТЕСТЫ ЗАВЕРШЕНЫ");
            System.out.println("Нажмите Enter для продолжения...");
            in.nextLine();

        } catch (Exception e) {
            System.out.println("Ошибка при запуске тестов: " + e.getMessage());
        }
    }

    private static void shutdownExecutor() {
        backgroundExecutor.shutdown();
    }

    private static void countCarsParallel() {
        if (cars.size() == 0) {
            System.out.println("Нет данных для подсчета.");
            return;
        }

        System.out.println("\n🔍 МНОГОПОТОЧНЫЙ ПОДСЧЕТ АВТОМОБИЛЕЙ");
        System.out.println("=".repeat(40));

        // Показываем автомобили для выбора
        System.out.println("Автомобили в коллекции:");
        int showCount = Math.min(10, cars.size());
        for (int i = 0; i < showCount; i++) {
            System.out.printf((i + 1) + "." + cars.get(i));
        }

        System.out.println("\nВыберите автомобиль для подсчета:");
        System.out.println("1. Выбрать из списка выше");
        System.out.println("2. Задать автомобиль для поиска вручную");
        System.out.println("3. Использовать первый автомобиль");
        System.out.println("4. Отмена");
        System.out.print("Выберите опцию (1-4): ");

        int choice = getMenuChoice(1, 4);
        Car targetCar = null;

        switch (choice) {
            case 1:
                System.out.print("Введите номер автомобиля: " );
                int carNum = getMenuChoice(1, showCount);
                targetCar = cars.get(carNum - 1);
                break;

            case 2:
                targetCar = createCarForSearch();
                if (targetCar == null) {
                    System.out.println("Ошибка создания автомобиля.");
                    return;
                }
                break;

            case 3:
                targetCar = cars.get(0);
                break;

            case 4:
                return;
        }

        if (targetCar == null) {
            System.out.println("Не выбран автомобиль для подсчета.");
            return;
        }
        int checkCount = showCount;
        for (int i = 0; i < checkCount; i++) {
            Car currentCar = cars.get(i);
            boolean isEqual = targetCar.equals(currentCar);
        }
        System.out.println("\nЗАПУСК МНОГОПОТОЧНОГО ПОИСКА...");
        int count = ParallelCarCounter.countOccurrences(cars, targetCar);
        System.out.println("\nРЕЗУЛЬТАТ ПОДСЧЕТА:");
        System.out.println("=".repeat(40));
        System.out.println("Автомобиль: " + targetCar);
        System.out.println("Размер коллекции: " + cars.size());
        System.out.println("Найдено вхождений: " + count);

        if (count == 0) {
            System.out.println("\nТакого автомобиля нет в коллекции.");
        }else {
            System.out.println("Показать все найденные автомобили? (да/нет):");
            String answer = in.nextLine().trim().toLowerCase();
            if (answer.equals("да") || answer.equals("yes")) {
                System.out.println("\nНАЙДЕННЫЕ АВТОМОБИЛИ:");
                System.out.println("=".repeat(50));

                int foundCount = 0;
                for (int i = 0; i < cars.size(); i++) {
                    if (cars.get(i).equals(targetCar)) {
                        foundCount++;
                        System.out.printf("%4d. %s (позиция: %d)\n",
                                foundCount, cars.get(i), i + 1);
                    }
                }
                if (foundCount == 0) {
                    System.out.println("Автомобили не найдены (хотя счетчик показал > 0)");
                }
            }
            CustomList<Car> foundCars = new MyArrayList<>();
            for (int i = 0; i < cars.size(); i++) {
                if (cars.get(i).equals(targetCar)) {
                    foundCars.add(cars.get(i));
                }
            }
        }
    }

    private static Car createCarForSearch() {
        System.out.println("\nСОЗДАНИЕ АВТОМОБИЛЯ ДЛЯ ПОИСКА");
        System.out.println("-".repeat(30));

        try {
            String powerStr, model, yearStr;
            do {
                System.out.print("Мощность: ");
                powerStr = in.nextLine();
                if (!CarValidator.validatePower(powerStr)) {
                    System.out.println("Некорректная мощность. Допустимо: 1-2000 л.с.");
                }
            } while (!CarValidator.validatePower(powerStr));
            int power = Integer.parseInt(powerStr);

            // Ввод модели с валидацией
            do {
                System.out.print("Модель: ");
                model = in.nextLine();
                if (!CarValidator.validateModel(model)) {
                    System.out.println("Модель не может быть пустой.");
                }
            } while (!CarValidator.validateModel(model));

            // Ввод года с валидацией
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
        boolean addToExisting = false;
        if (cars.size() > 0) {
            System.out.print("\nТекущая коллекция содержит " + cars.size() + " автомобилей.");
            System.out.print(" Добавить к существующим? (да/нет): ");
            String answer = in.nextLine().trim().toLowerCase();
            addToExisting = answer.equals("да") || answer.equals("yes") ||
                    answer.equals("y") || answer.equals("д");
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
                if (filename.isEmpty()) {
                    filename = "cars.txt";
                }
                loadedCars = DataLoader.loadFromFile(filename);
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

        System.out.print("\nСохранить отсортированные данные в файл? (да/нет): ");
        String saveAnswer = in.nextLine().trim().toLowerCase();
        if (saveAnswer.equals("да") || saveAnswer.equals("yes")) {
            System.out.print("Введите имя файла (Enter для стандартного): ");
            String filename = in.nextLine().trim();

            SaveSortedToFile.saveSortedToFile(carsCopy, filename);
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
        System.out.println("   - Год: 1900-2025");

        System.out.println("\n4. ФУНКЦИОНАЛЬНОСТЬ:");
        System.out.println("   - 3 способа ввода данных");
        System.out.println("   - стратегия сортировки");
        System.out.println("   - Многопоточный подсчет автомобилей");
        System.out.println("   - Работа с файлами");
        System.out.println("   - Сохранение отсортированных данных");
        System.out.println("   - Валидация всех входных данных");
        System.out.println("   - Запуск Тестов");
    }
}


