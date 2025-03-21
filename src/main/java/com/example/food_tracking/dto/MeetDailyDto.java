package com.example.food_tracking.dto;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetDailyDto {
    private Double calorieIntake;
    private Double calorieCurrent;
    private String message;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MeetDailyDto that)) return false;
        return Objects.equals(calorieIntake, that.calorieIntake) && Objects.equals(calorieCurrent, that.calorieCurrent) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(calorieIntake, calorieCurrent, message);
    }
}
