package ru.sortproject.model;

import java.util.Objects;

public class Car {
    private int power;
    private String model;
    private int year;

    private Car() {}

    public static class Builder {
        private final Car car = new Car();

        public Builder setPower(int power) { car.power = power; return this; }
        public Builder setModel(String model) { car.model = model; return this; }
        public Builder setYear(int year) { car.year = year; return this; }

        public Car build() { return car; }
    }

    public int getPower() {
        return power;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Car{" +
                "power=" + power +
                ", model='" + model + '\'' +
                ", year=" + year +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return power == car.power &&
                year == car.year &&
                Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(power, model, year);
    }
}
