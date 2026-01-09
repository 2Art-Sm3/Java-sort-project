package ru.sortproject.test;

import ru.sortproject.model.Car;
import ru.sortproject.strategy.EvenOddSortStrategy;
import ru.sortproject.structure.MyArrayList;

public class TestLauncher1 {
    public static void main(String[] args) {

        MyArrayList<String> list = new MyArrayList<>(2); // Маленький размер для проверки grow()

        // 1. Проверка добавления и расширения
        list.add("Первый");
        list.add("Второй");
        System.out.println("Размер коллекции: " + list.size());
        list.add("Третий"); // Тут должен сработать grow()

        System.out.println("Размер коллекции: " + list.size()); // Должно быть 3
        System.out.println("Содержимое (через toString): " + list);

        // 2. Проверка итератора через for-each
        System.out.println("Проверка итератора:");
        int count = 0;
        for (String s : list) {
            System.out.println("Элемент " + count + ": " + s);
            count++;
        }

        // 3. Ручная проверка итератора (как он работает "под капотом")
        java.util.Iterator<String> it = list.iterator();
        if (it.hasNext() && it.next().equals("Первый")) {
            System.out.println("Ручная проверка: OK");
        }

        MyArrayList<Car> cars = new MyArrayList<>();
        cars.add(new Car.Builder().setPower(150).setModel("A").build()); // Четное
        cars.add(null);                                                  // Null
        cars.add(new Car.Builder().setPower(101).setModel("B").build()); // Нечетное
        cars.add(new Car.Builder().setPower(50).setModel("C").build());  // Четное

        System.out.println(cars);

        EvenOddSortStrategy strategy = new EvenOddSortStrategy();
        strategy.sort(cars, null);

        System.out.println(cars);

    }
}
