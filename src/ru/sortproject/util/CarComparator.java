package ru.sortproject.util;

import ru.sortproject.model.Car;
import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

    @Override
    public int compare(Car car1, Car car2) {
        int power1 = car1.getPower();
        int power2 = car2.getPower();

        if (power1 != power2) {
            return Integer.compare(power1, power2);
        }

        int year1 = car1.getYear();
        int year2 = car2.getYear();

        if (year1 != year2) {
            return Integer.compare(year1, year2);
        }

        String model1 = car1.getModel();
        String model2 = car2.getModel();

        if (model1 == null && model2 == null) {
            return 0;
        }
        if (model1 == null) {
            return -1;
        }
        if (model2 == null) {
            return 1;
        }

        return model1.compareTo(model2);
    }
}