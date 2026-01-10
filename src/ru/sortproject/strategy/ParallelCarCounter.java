package ru.sortproject.strategy;

import ru.sortproject.model.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelCarCounter implements CarCounter {
    private final int threadCount;
    private final ExecutorService executor;

    public ParallelCarCounter(int threadCount) {
        this.threadCount = Math.max(1, threadCount);
        this.executor = Executors.newFixedThreadPool(this.threadCount);
    }

    public ParallelCarCounter() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public int countOccurrences(Car targetCar, List<Car> cars) {
        if (targetCar == null || cars == null || cars.isEmpty()) {
            return 0;
        }

        int chunkSize = Math.max(1, cars.size() / threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger totalCount = new AtomicInteger(0);

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = (i == threadCount - 1) ? cars.size() : start + chunkSize;

            executor.submit(() -> {
                try {
                    int localCount = 0;
                    for (int j = start; j < end && j < cars.size(); j++) {
                        if (cars.get(j).equals(targetCar)) {
                            localCount++;
                        }
                    }
                    totalCount.addAndGet(localCount);
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Counting interrupted", e);
        }

        return totalCount.get();
    }

    public int countOccurrencesWithFutures(Car targetCar, List<Car> cars) {
        if (targetCar == null || cars == null || cars.isEmpty()) {
            return 0;
        }

        int chunkSize = Math.max(1, cars.size() / threadCount);
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            final int start = i * chunkSize;
            final int end = (i == threadCount - 1) ? cars.size() : start + chunkSize;

            Callable<Integer> task = () -> {
                int count = 0;
                for (int j = start; j < end && j < cars.size(); j++) {
                    if (cars.get(j).equals(targetCar)) {
                        count++;
                    }
                }
                return count;
            };

            futures.add(executor.submit(task));
        }

        int totalCount = 0;
        for (Future<Integer> future : futures) {
            try {
                totalCount += future.get();
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error getting future result", e);
            }
        }

        return totalCount;
    }

    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public int countReplay(Car targetCar, List<Car> cars) {
        return 0;
    }

    @Override
    public String getCounterName() {
        return String.format("Parallel Counter (%d threads)", threadCount);
    }

    public int getThreadCount() {
        return threadCount;
    }
}
