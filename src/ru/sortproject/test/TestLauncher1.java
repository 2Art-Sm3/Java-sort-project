package ru.sortproject.test;

import ru.sortproject.structure.MyArrayList;

public class TestLauncher1 {
    public static void main(String[] args) {

        MyArrayList<String> list = new MyArrayList<>();

        list.add("Артем");
        list.add("Артем");
        list.add("dd");

        System.out.println(list);
        System.out.println(list.size());

        System.out.println(list.get(2));
        list.update(0, "CCJCh");

        System.out.println(list);

    }
}
