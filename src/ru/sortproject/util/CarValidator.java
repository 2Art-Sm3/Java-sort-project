package ru.sortproject.util;

public class CarValidator {
    public static boolean validatePower(String powerStr) {
        try {
            int power = Integer.parseInt(powerStr);
            return power >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateModel(String modelStr) {
        return modelStr != null && !modelStr.trim().isEmpty();
    }

    public static boolean validateYear(String yearStr) {
        try {
            int year = Integer.parseInt(yearStr);
            return year >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
