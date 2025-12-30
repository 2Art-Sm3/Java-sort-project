package ru.sortproject.model;

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
}
