package ru.sortproject.util;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;
import ru.sortproject.structure.MyArrayList;

public class CarListUtil {

    private CarListUtil() {}

    public static CustomList<Car> copyList(CustomList<Car> cars) {
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
}
