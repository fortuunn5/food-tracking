package com.example.food_tracking.model.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Gender {
    MALE ("Мужчина"),
    FEMALE ("Женщина");

    private final String gender;

    public static Gender byName(String name) {
        for (Gender gen : Gender.values()) {
            if (gen.gender.equals(name)) {
                return gen;
            }
        }
        throw new IllegalArgumentException("Такого пола не существует");
    }
}
