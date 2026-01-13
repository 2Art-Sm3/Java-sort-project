package ru.sortproject.test;

import ru.sortproject.model.Car;
import ru.sortproject.structure.MyArrayList;
import ru.sortproject.strategy.BubbleSortStrategy;
import ru.sortproject.strategy.SelectionSortStrategy;
import ru.sortproject.strategy.InsertionSortStrategy;
import ru.sortproject.strategy.SortStrategy;
import ru.sortproject.util.CarComparator;

import java.util.function.Supplier;

public class TestLauncher3 {
    public static void main(String[] args) {
        System.out.println("Test sorting by three fields\n");
        testAllAlgorithms();
        testEdgeCases();
        System.out.println("\nAll tests completed.");
    }

    private static void testAllAlgorithms() {
        testAlgorithm(BubbleSortStrategy::new, "Bubble Sort");
        testAlgorithm(SelectionSortStrategy::new, "Selection Sort");
        testAlgorithm(InsertionSortStrategy::new, "Insertion Sort");
    }

    private static void testAlgorithm(Supplier<SortStrategy<Car>> strategySupplier, String algorithmName) {
        System.out.println("\n=== " + algorithmName + " ===");
        MyArrayList<Car> cars = createTestCars();
        System.out.println("Original list:");
        printCars(cars);

        strategySupplier.get().sort(cars, new CarComparator());

        System.out.println("\nSorted list:");
        printCars(cars);

        System.out.println("\nChecking sorting correctness");
        checkSortingCorrectness(cars);
    }

    private static void testEdgeCases() {
        System.out.println("\n=== EDGE CASES ===");

        SortStrategy<Car> strategy = new BubbleSortStrategy<>();
        CarComparator comparator = new CarComparator();

        System.out.println("\n1. Empty list:");
        testEdgeCase(strategy, new MyArrayList<>(), comparator, true);

        System.out.println("\n2. Single element:");
        testEdgeCase(strategy, createSingleElementList(), comparator, true);

        System.out.println("\n3. Identical elements:");
        testEdgeCase(strategy, createIdenticalElementsList(), comparator, true);

        System.out.println("\n4. Null list:");
        testNullCase("null list", () -> strategy.sort(null, comparator));

        System.out.println("\n5. Null comparator:");
        testNullCase("null comparator", () -> strategy.sort(createSingleElementList(), null));
    }

    private static void testEdgeCase(SortStrategy<Car> strategy, MyArrayList<Car> list,
                                     CarComparator comparator, boolean expectSuccess) {
        try {
            strategy.sort(list, comparator);
            if (expectSuccess) {
                System.out.println("PASS - Handled correctly");
            } else {
                System.out.println("FAIL - Should have thrown exception");
            }
        } catch (Exception e) {
            if (expectSuccess) {
                System.out.println("FAIL - Unexpected exception: " + e.getMessage());
            } else {
                System.out.println("PASS - Correctly rejected: " + e.getMessage());
            }
        }
    }

    private static void testNullCase(String caseName, Runnable test) {
        try {
            test.run();
            System.out.println("FAIL - Shoould have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("FAIL - Wrong exception type: " + e.getClass().getSimpleName());
        }
    }


    private static MyArrayList<Car> createTestCars() {
        MyArrayList<Car> list = new MyArrayList<>();

        list.add(new Car.Builder().setPower(200).setModel("BMW").setYear(2020).build());
        list.add(new Car.Builder().setPower(150).setModel("Audi").setYear(2021).build());
        list.add(new Car.Builder().setPower(120).setModel("Toyota").setYear(2018).build());
        list.add(new Car.Builder().setPower(120).setModel("Honda").setYear(2022).build());
        list.add(new Car.Builder().setPower(100).setModel("Kia").setYear(2020).build());
        list.add(new Car.Builder().setPower(100).setModel("Hyundai").setYear(2020).build());
        list.add(new Car.Builder().setPower(180).setModel("Mercedes").setYear(2019).build());
        list.add(new Car.Builder().setPower(150).setModel("Volkswagen").setYear(2021).build());

        return list;
    }

    private static MyArrayList<Car> createSingleElementList() {
        MyArrayList<Car> list = new MyArrayList<>();
        list.add(new Car.Builder().setPower(100).setModel("Test").setYear(2023).build());
        return list;
    }

    private static MyArrayList<Car> createIdenticalElementsList() {
        MyArrayList<Car> list = new MyArrayList<>();
        Car template = new Car.Builder().setPower(150).setModel("Same").setYear(2020).build();
        list.add(template);
        list.add(template);
        list.add(template);
        return list;
    }

    private static void printCars(MyArrayList<Car> cars) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            System.out.printf("%d. %s - %d hp, %d year%n",
                    i + 1, car.getModel(), car.getPower(), car.getYear());
        }
    }

    private static void checkSortingCorrectness(MyArrayList<Car> cars) {
        CarComparator comparator = new CarComparator();
        boolean correctlySorted = true;

        for (int i = 0; i < cars.size() - 1; i++) {
            Car current = cars.get(i);
            Car next = cars.get(i + 1);

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
            System.out.println("PASS - All elements are sorted correctly");
            System.out.println("\nSorting order:");
            System.out.println("1. By power (ascending)");
            System.out.println("2. If powers are equal - by year (ascending)");
            System.out.println("3. If power and year are equal - by model (alphabetical)");
        } else {
            System.out.println("FAIL - Sorting errors detected");
        }
    }
}