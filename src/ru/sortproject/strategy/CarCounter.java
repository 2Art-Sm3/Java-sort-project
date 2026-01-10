package ru.sortproject.strategy;

import ru.sortproject.model.Car;

import java.util.List;

public interface CarCounter {
    int countReplay(Car targetCar, List<Car> cars);
    String getCounterName();
}
