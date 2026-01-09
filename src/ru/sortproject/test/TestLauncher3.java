package ru.sortproject.test;

import ru.sortproject.model.Car;
import ru.sortproject.structure.MyArrayList;
import ru.sortproject.strategy.BubbleSortStrategy;
import ru.sortproject.strategy.SelectionSortStrategy;
import ru.sortproject.strategy.InsertionSortStrategy;
import ru.sortproject.strategy.SorterContext;
import ru.sortproject.util.CarComparator;

public class TestLauncher3 {
    public static void main(String[] args) {
        System.out.println("Test sorting by three fields\n");

        System.out.println("=== TEST BUBBLE SORT ===");
        testSortingAlgorithm("Bubble Sort", new BubbleSortStrategy<>());

        System.out.println("\n=== TEST SELECTION SORT ===");
        testSortingAlgorithm("Selection Sort", new SelectionSortStrategy<>());

        System.out.println("\n=== TEST INSERTION SORT ===");
        testSortingAlgorithm("Insertion Sort", new InsertionSortStrategy<>());

        System.out.println("\nTest edge cases");
        testSpecialCases();
    }

    private static void testSortingAlgorithm(String algorithmName, Object strategy) {
        MyArrayList<Car> cars = createTestCars();

        System.out.println("Original list:");
        printCars(cars);

        SorterContext<Car> sorter = new SorterContext<>();

        if (strategy instanceof BubbleSortStrategy) {
            sorter.setStrategy((BubbleSortStrategy<Car>) strategy);
        } else if (strategy instanceof SelectionSortStrategy) {
            sorter.setStrategy((SelectionSortStrategy<Car>) strategy);
        } else if (strategy instanceof InsertionSortStrategy) {
            sorter.setStrategy((InsertionSortStrategy<Car>) strategy);
        }

        CarComparator comparator = new CarComparator();

        sorter.executeSort(cars, comparator);

        System.out.println("\nSorted list (" + algorithmName + "):");
        printCars(cars);

        System.out.println("\nCheck sorting correctness");
        checkSortingCorrectness(cars, comparator);
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

    private static void printCars(MyArrayList<Car> cars) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            System.out.printf("%d. %s - %d hp, %d year%n",
                    i + 1,
                    car.getModel(),
                    car.getPower(),
                    car.getYear());
        }
    }

    private static void checkSortingCorrectness(MyArrayList<Car> cars, CarComparator comparator) {
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
        }
    }

    private static void testSpecialCases() {
        System.out.println("\n1. Test with empty list:");
        MyArrayList<Car> emptyList = new MyArrayList<>();
        try {
            new BubbleSortStrategy<Car>().sort(emptyList, new CarComparator());
            System.out.println("Bubble Sort: PASS - Empty list handled correctly");
        } catch (Exception e) {
            System.out.println("Bubble Sort ERROR: " + e.getMessage());
        }

        try {
            new SelectionSortStrategy<Car>().sort(emptyList, new CarComparator());
            System.out.println("Selection Sort: PASS - Empty list handled correctly");
        } catch (Exception e) {
            System.out.println("Selection Sort ERROR: " + e.getMessage());
        }

        try {
            new InsertionSortStrategy<Car>().sort(emptyList, new CarComparator());
            System.out.println("Insertion Sort: PASS - Empty list handled correctly");
        } catch (Exception e) {
            System.out.println("Insertion Sort ERROR: " + e.getMessage());
        }

        System.out.println("\n2. Test with single element list:");
        MyArrayList<Car> singleElement = new MyArrayList<>();
        singleElement.add(new Car.Builder().setPower(100).setModel("Test").setYear(2023).build());

        try {
            new BubbleSortStrategy<Car>().sort(singleElement, new CarComparator());
            System.out.println("Bubble Sort: PASS - Single element list handled correctly");
        } catch (Exception e) {
            System.out.println("Bubble Sort ERROR: " + e.getMessage());
        }

        try {
            new SelectionSortStrategy<Car>().sort(singleElement, new CarComparator());
            System.out.println("Selection Sort: PASS - Single element list handled correctly");
        } catch (Exception e) {
            System.out.println("Selection Sort ERROR: " + e.getMessage());
        }

        try {
            new InsertionSortStrategy<Car>().sort(singleElement, new CarComparator());
            System.out.println("Insertion Sort: PASS - Single element list handled correctly");
        } catch (Exception e) {
            System.out.println("Insertion Sort ERROR: " + e.getMessage());
        }

        System.out.println("\n3. Test with identical elements:");
        MyArrayList<Car> sameCars = new MyArrayList<>();
        sameCars.add(new Car.Builder().setPower(150).setModel("Same").setYear(2020).build());
        sameCars.add(new Car.Builder().setPower(150).setModel("Same").setYear(2020).build());
        sameCars.add(new Car.Builder().setPower(150).setModel("Same").setYear(2020).build());

        try {
            new BubbleSortStrategy<Car>().sort(sameCars, new CarComparator());
            System.out.println("Bubble Sort: PASS - Identical elements handled correctly");
        } catch (Exception e) {
            System.out.println("Bubble Sort ERROR: " + e.getMessage());
        }

        try {
            new SelectionSortStrategy<Car>().sort(sameCars, new CarComparator());
            System.out.println("Selection Sort: PASS - Identical elements handled correctly");
        } catch (Exception e) {
            System.out.println("Selection Sort ERROR: " + e.getMessage());
        }

        try {
            new InsertionSortStrategy<Car>().sort(sameCars, new CarComparator());
            System.out.println("Insertion Sort: PASS - Identical elements handled correctly");
        } catch (Exception e) {
            System.out.println("Insertion Sort ERROR: " + e.getMessage());
        }

        System.out.println("\n4. Test null list:");
        try {
            new BubbleSortStrategy<Car>().sort(null, new CarComparator());
            System.out.println("Bubble Sort ERROR: Should throw exception for null list");
        } catch (IllegalArgumentException e) {
            System.out.println("Bubble Sort PASS - Null list correctly rejected: " + e.getMessage());
        }

        try {
            new SelectionSortStrategy<Car>().sort(null, new CarComparator());
            System.out.println("Selection Sort ERROR: Should throw exception for null list");
        } catch (IllegalArgumentException e) {
            System.out.println("Selection Sort PASS - Null list correctly rejected: " + e.getMessage());
        }

        try {
            new InsertionSortStrategy<Car>().sort(null, new CarComparator());
            System.out.println("Insertion Sort ERROR: Should throw exception for null list");
        } catch (IllegalArgumentException e) {
            System.out.println("Insertion Sort PASS - Null list correctly rejected: " + e.getMessage());
        }

        System.out.println("\n5. Test null comparator:");
        MyArrayList<Car> testList = new MyArrayList<>();
        testList.add(new Car.Builder().setPower(100).setModel("Test").setYear(2023).build());

        try {
            new BubbleSortStrategy<Car>().sort(testList, null);
            System.out.println("Bubble Sort ERROR: Should throw exception for null comparator");
        } catch (IllegalArgumentException e) {
            System.out.println("Bubble Sort PASS - Null comparator correctly rejected: " + e.getMessage());
        }

        try {
            new SelectionSortStrategy<Car>().sort(testList, null);
            System.out.println("Selection Sort ERROR: Should throw exception for null comparator");
        } catch (IllegalArgumentException e) {
            System.out.println("Selection Sort PASS - Null comparator correctly rejected: " + e.getMessage());
        }

        try {
            new InsertionSortStrategy<Car>().sort(testList, null);
            System.out.println("Insertion Sort ERROR: Should throw exception for null comparator");
        } catch (IllegalArgumentException e) {
            System.out.println("Insertion Sort PASS - Null comparator correctly rejected: " + e.getMessage());
        }

        System.out.println("\nAll tests completed.");
    }
}