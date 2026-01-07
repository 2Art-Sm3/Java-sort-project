package ru.sortproject.test;

import ru.sortproject.model.Car;
import ru.sortproject.strategy.BubbleSortStrategy;
import ru.sortproject.strategy.SorterContext;
import ru.sortproject.util.CarComparator;

public class TestLauncher3 {
    public static void main(String[] args) {
        System.out.println("Test sorting by three fields\n");

        Car[] cars = createTestCars();

        System.out.println("Original array:");
        printCars(cars);

        SorterContext<Car> sorter = new SorterContext<>();
        sorter.setStrategy(new BubbleSortStrategy<>());

        CarComparator comparator = new CarComparator();

        sorter.executeSort(cars, comparator);

        System.out.println("\nSorted array:");
        printCars(cars);

        System.out.println("\nCheck");
        checkSortingCorrectness(cars, comparator);

        System.out.println("\nTest edge cases");
        testSpecialCases();
    }

    private static Car[] createTestCars() {
        return new Car[] {
                new Car.Builder().setPower(200).setModel("BMW").setYear(2020).build(),
                new Car.Builder().setPower(150).setModel("Audi").setYear(2021).build(),

                new Car.Builder().setPower(120).setModel("Toyota").setYear(2018).build(),
                new Car.Builder().setPower(120).setModel("Honda").setYear(2022).build(),

                new Car.Builder().setPower(100).setModel("Kia").setYear(2020).build(),
                new Car.Builder().setPower(100).setModel("Hyundai").setYear(2020).build(),

                new Car.Builder().setPower(180).setModel("Mercedes").setYear(2019).build(),
                new Car.Builder().setPower(150).setModel("Volkswagen").setYear(2021).build()
        };
    }

    private static void printCars(Car[] cars) {
        for (int i = 0; i < cars.length; i++) {
            Car car = cars[i];
            System.out.printf("%d. %s - %d hp, %d year%n",
                    i + 1,
                    car.getModel(),
                    car.getPower(),
                    car.getYear());
        }
    }

    private static void checkSortingCorrectness(Car[] cars, CarComparator comparator) {
        boolean correctlySorted = true;

        for (int i = 0; i < cars.length - 1; i++) {
            Car current = cars[i];
            Car next = cars[i + 1];

            int compareResult = comparator.compare(current, next);

            if (compareResult > 0) {
                System.out.printf("ERROR: element %d is greater than element %d%n", i, i + 1);
                System.out.printf("   %s (%d hp, %d) > %s (%d hp, %d)%n",
                        current.getModel(), current.getPower(), current.getYear(),
                        next.getModel(), next.getPower(), next.getYear());
                correctlySorted = false;
            }
        }

        if (correctlySorted) {
            System.out.println("PASS");

            System.out.println("\nSorting order:");
            System.out.println("1. By power (ascending)");
            System.out.println("2. If powers are equal - by year (ascending)");
            System.out.println("3. If power and year are equal - by model (alphabetical)");
        }
    }

    private static void testSpecialCases() {
        System.out.println("\n1. Test with empty array:");
        Car[] emptyArray = new Car[0];
        try {
            new BubbleSortStrategy<Car>().sort(emptyArray, new CarComparator());
            System.out.println("PASS");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n2. Test with single element array:");
        Car[] singleElement = {
                new Car.Builder().setPower(100).setModel("Test").setYear(2023).build()
        };
        try {
            new BubbleSortStrategy<Car>().sort(singleElement, new CarComparator());
            System.out.println("PASS");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\n3. Test with identical elements:");
        Car[] sameCars = {
                new Car.Builder().setPower(150).setModel("Same").setYear(2020).build(),
                new Car.Builder().setPower(150).setModel("Same").setYear(2020).build(),
                new Car.Builder().setPower(150).setModel("Same").setYear(2020).build()
        };
        try {
            new BubbleSortStrategy<Car>().sort(sameCars, new CarComparator());
            System.out.println("PASS");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("\nAll tests completed.");
    }
}