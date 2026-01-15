package ru.sortproject.util;

public class CarValidator {

    private CarValidator() {}

    public static boolean validatePower(String powerStr) {
        if (powerStr == null || powerStr.isEmpty()) return false;
        try {
            int power = Integer.parseInt(powerStr.trim());
            return power > 0 && power <= 3000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateModel(String modelStr) {
        if (modelStr == null) return false;
        String trimmed = modelStr.trim();
        return trimmed.length() >= 2 && trimmed.length() <= 50;
    }

    public static boolean validateYear(String yearStr) {
        if (yearStr == null || yearStr.isEmpty()) return false;
        try {
            int year = Integer.parseInt(yearStr.trim());
            return year >= 1960 && year <= 2025;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
