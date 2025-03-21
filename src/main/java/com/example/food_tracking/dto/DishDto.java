package com.example.food_tracking.dto;

import com.example.food_tracking.model.dish.Dish;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DishDto {
    private Long id;
    private String name;
    private Double calories;
    private Double proteins;
    private Double carbohydrates;
    private Double fats;

    public DishDto(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DishDto dishDto)) return false;
        return Objects.equals(id, dishDto.id) && Objects.equals(name, dishDto.name) && Objects.equals(calories, dishDto.calories) && Objects.equals(proteins, dishDto.proteins) && Objects.equals(carbohydrates, dishDto.carbohydrates) && Objects.equals(fats, dishDto.fats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, calories, proteins, carbohydrates, fats);
    }
}
