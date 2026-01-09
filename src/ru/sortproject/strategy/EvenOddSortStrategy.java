package ru.sortproject.strategy;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;
import ru.sortproject.structure.MyArrayList;

import java.util.Comparator;

public class EvenOddSortStrategy implements SortStrategy<Car> {
    @Override
    public void sort(CustomList<Car> list, Comparator<Car> ignored) {

        if (list == null) {
            throw new IllegalArgumentException("List is null");
        }

        CustomList<Car> evens = new MyArrayList<>();

        for (Car car : list) {
            if (car != null && car.getPower() % 2 == 0) {
                evens.add(car);
            }
        }

        if (evens.size() < 1)
            return;

        BubbleSortStrategy<Car> bubble = new BubbleSortStrategy<>();
        bubble.sort(evens, (c1, c2) -> Integer.compare(c1.getPower(), c2.getPower()));

        int count = 0;

        for (int i = 0; i < list.size(); i++) {

            if (list.get(i) == null || list.get(i).getPower() % 2 != 0)
                continue;

            list.update(i, evens.get(count++));
        }
    }
}
