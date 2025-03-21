package com.example.food_tracking.utils;

import com.example.food_tracking.model.user.Gender;
import com.example.food_tracking.model.user.Purpose;

public class CalculationCaloriesUtil {
    public static Double calculateCalories(Double weight, Short height, Byte age, Gender gender, Purpose purpose) {
        Double calorieIntake = gender.getGender().equals(Gender.MALE.getGender()) ?
                88.36 + (13.4 * weight) + (4.8 * height) - (5.7 * age) :
                447.6 + (9.2 * weight) + (3.1 * height) - (4.3 * age);
        return Math.round(calorieIntake * purpose.getCoefficient() * 100.0) / 100.0;
    }
}