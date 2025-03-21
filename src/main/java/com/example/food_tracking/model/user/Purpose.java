package com.example.food_tracking.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Purpose {
    WEIGHT_LOSS ("Похудение", 0.9),
    WEIGHT_MAINTENANCE ("Поддержание веса", 1),
    WEIGHT_GAIN ("Набор массы", 1.1);

    private final String purpose;
    private final double coefficient;

    public static Purpose byName(String name) {
        for (Purpose pur : Purpose.values()) {
            if (pur.purpose.equals(name)) {
                return pur;
            }
        }
        throw new IllegalArgumentException("Такой цели не существует");
    }
}
