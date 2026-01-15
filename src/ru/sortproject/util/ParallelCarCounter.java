package ru.sortproject.util;

import ru.sortproject.structure.CustomList;

import java.util.concurrent.*;

public class ParallelCarCounter {
    public static <T> int countOccurrences(CustomList<T> list, T target) {
        if (list == null || list.size() == 0 || target == null) {
            return 0;
        }
        // Используем ForkJoinPool для распараллеливания
        ForkJoinPool pool = ForkJoinPool.commonPool();
        return pool.invoke(new CountingTask<>(list, target, 0, list.size()));
    }

    private static class CountingTask<T> extends RecursiveTask<Integer> {
        private static final int THRESHOLD = 100;
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

        protected Integer compute() {
            int length = end - start;

            // Если элементов немного
            if (length <= THRESHOLD) {
                int count = 0;
                for (int i = start; i < end; i++) {
                    if (target.equals(list.get(i))) count++;
                }
                return count;
            }

            //Иначе -- Делим задачу на две подзадачи
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
    }
}