package ru.sortproject.util;

import ru.sortproject.model.Car;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

    @Override
    public int compare(Car c1, Car c2) {

        if (c1.getPower() != c2.getPower()) {
            return Integer.compare(c1.getPower(), c2.getPower());
        }
        if (c1.getYear() != c2.getYear()) {
            return Integer.compare(c1.getYear(), c2.getYear());
        }
        return c1.getModel().compareTo(c2.getModel());
    }
}
