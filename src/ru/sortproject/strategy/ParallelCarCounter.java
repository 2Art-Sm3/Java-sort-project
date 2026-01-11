package ru.sortproject.strategy;

import ru.sortproject.model.Car;
import ru.sortproject.structure.CustomList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ParallelCarCounter {
    public static <T> int countOccurrences(CustomList<T> list, T target) {
        if (list == null || list.size() == 0 || target == null) {
            return 0;
        }

        // Для маленьких коллекций используем быстрый последовательный поиск
        if (list.size() < 1000) {
            return countSequentially(list, target);
        }

        // Используем ForkJoinPool для распараллеливания
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new CountingTask<>(list, target, 0, list.size()));
    }

    //Последовательный подсчет (для маленьких коллекций)

    private static <T> int countSequentially(CustomList<T> list, T target) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (target.equals(list.get(i))) {
                count++;
            }
        }
        return count;
    }

    private static class CountingTask<T> extends RecursiveTask<Integer> {
        // Порог для переключения на последовательный подсчет
        private static final int THRESHOLD = 1000;

        private final CustomList<T> list;
        private final T target;
        private final int start;
        private final int end;

        CountingTask(CustomList<T> list, T target, int start, int end) {
            this.list = list;
            this.target = target;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            int length = end - start;

            // Если диапазон маленький, считаем последовательно
            if (length <= THRESHOLD) {
                return computeDirectly();
            }

            // Делим задачу на две подзадачи
            int middle = start + length / 2;

            CountingTask<T> leftTask = new CountingTask<>(list, target, start, middle);
            CountingTask<T> rightTask = new CountingTask<>(list, target, middle, end);

            // Запускаем левую задачу асинхронно
            leftTask.fork();

            // Выполняем правую задачу в текущем потоке
            int rightResult = rightTask.compute();

            // Ждем результат левой задачи
            int leftResult = leftTask.join();

            // Суммируем результаты
            return leftResult + rightResult;
        }

        private int computeDirectly() {
            int count = 0;
            for (int i = start; i < end; i++) {
                if (target.equals(list.get(i))) {
                    count++;
                }
            }
            return count;
        }
    }
}
