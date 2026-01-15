package ru.sortproject.util;

import ru.sortproject.structure.MyArrayList;

import java.util.stream.Collector;

public class MyArrayListCollector {

    private MyArrayListCollector() {}

    public static <T> Collector<T, MyArrayList<T>, MyArrayList<T>> carCollector() {
        return Collector.of(
                MyArrayList::new,
                MyArrayList::add,
                (list1, list2) -> { list1.addAll(list2); return list1; } // объединение
        );
    }
}
