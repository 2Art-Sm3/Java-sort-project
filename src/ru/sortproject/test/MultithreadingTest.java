package ru.sortproject.test;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;
import ru.sortproject.util.ParallelCarCounter;
import ru.sortproject.structure.MyArrayList;

//Простой ручной тест для многопоточного счетчика

public class MultithreadingTest {

    public static void main(String[] args) {
        System.out.println("🔧 ПРОСТОЙ ТЕСТ МНОГОПОТОЧНОГО СЧЕТЧИКА\n");

        System.out.println("1. Создаем тестовый список автомобилей:");
        CustomList<Car> cars = new MyArrayList<>();

        // Создаем целевой автомобиль
        Car targetCar = new Car.Builder()
                .setModel("Toyota Camry")
                .setPower(180)
                .setYear(2020)
                .build();

        System.out.println("   Целевой автомобиль: " + targetCar);

        // Добавляем автомобили в список
        cars.add(targetCar);
        cars.add(new Car.Builder()
                .setModel("Honda Accord")
                .setPower(200)
                .setYear(2021)
                .build());
        cars.add(targetCar);
        cars.add(targetCar);
        cars.add(new Car.Builder()
                .setModel("BMW X5")
                .setPower(300)
                .setYear(2022)
                .build());
        cars.add(targetCar);

        System.out.println("   Всего автомобилей в списке: " + cars.size());

        // Показываем список
        System.out.println("\n2. Список автомобилей:");
        for (int i = 0; i < cars.size(); i++) {
            System.out.println("   " + (i+1) + ". " + cars.get(i));
        }

        System.out.println("\n3. Запускаем многопоточный подсчет:");
        int result = ParallelCarCounter.countOccurrences(cars, targetCar);

        System.out.println("\n4. Результат:");
        System.out.println("   Найдено совпадений: " + result);
        System.out.println("   Ожидалось: 4");

        if (result == 4) {
            System.out.println("\n✅ ТЕСТ ПРОЙДЕН! Многопоточный счетчик работает правильно.");
        } else {
            System.out.println("\n❌ ТЕСТ НЕ ПРОЙДЕН! Ожидалось 4, получено " + result);
        }

        // Дополнительные простые тесты
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ДОПОЛНИТЕЛЬНЫЕ ТЕСТЫ:");

        // Тест с пустым списком
        System.out.println("\n5. Тест с пустым списком:");
        CustomList<Car> emptyList = new MyArrayList<>();
        int emptyResult = ParallelCarCounter.countOccurrences(emptyList, targetCar);
        System.out.println("   Результат: " + emptyResult + " (должно быть 0)");

        // Тест с одним элементом
        System.out.println("\n6. Тест с одним элементом:");
        CustomList<Car> singleList = new MyArrayList<>();
        singleList.add(targetCar);
        int singleResult = ParallelCarCounter.countOccurrences(singleList, targetCar);
        System.out.println("   Результат: " + singleResult + " (должно быть 1)");

        // Тест без совпадений
        System.out.println("\n7. Тест без совпадений:");
        CustomList<Car> noMatchList = new MyArrayList<>();
        Car differentCar = new Car.Builder()
                .setModel("Lada")
                .setPower(90)
                .setYear(2010)
                .build();
        noMatchList.add(differentCar);
        noMatchList.add(differentCar);
        int noMatchResult = ParallelCarCounter.countOccurrences(noMatchList, targetCar);
        System.out.println("   Результат: " + noMatchResult + " (должно быть 0)");

        // Проверяем все результаты
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ИТОГИ ТЕСТИРОВАНИЯ:");

        boolean allTestsPassed = true;

        if (result != 4) {
            System.out.println("Основной тест не пройден");
            allTestsPassed = false;
        } else {
            System.out.println("Основной тест пройден");
        }

        if (emptyResult != 0) {
            System.out.println("Тест с пустым списком не пройден");
            allTestsPassed = false;
        } else {
            System.out.println("Тест с пустым списком пройден");
        }

        if (singleResult != 1) {
            System.out.println("Тест с одним элементом не пройден");
            allTestsPassed = false;
        } else {
            System.out.println("Тест с одним элементом пройден");
        }

        if (noMatchResult != 0) {
            System.out.println("Тест без совпадений не пройден");
            allTestsPassed = false;
        } else {
            System.out.println("Тест без совпадений пройден");
        }

        System.out.println("\n" + "=".repeat(50));
        if (allTestsPassed) {
            System.out.println("ВСЕ ТЕСТЫ УСПЕШНО ПРОЙДЕНЫ!");
        } else {
            System.out.println("НЕКОТОРЫЕ ТЕСТЫ НЕ ПРОЙДЕНЫ!");
        }
    }
}
