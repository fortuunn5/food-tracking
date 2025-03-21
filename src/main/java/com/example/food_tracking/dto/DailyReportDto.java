package com.example.food_tracking.dto;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DailyReportDto {
    private Double sumCalories;
    private List<FoodIntakeDto> foodIntakeList;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DailyReportDto that)) return false;
        return Objects.equals(sumCalories, that.sumCalories) && Objects.equals(foodIntakeList, that.foodIntakeList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sumCalories, foodIntakeList);
    }
}
