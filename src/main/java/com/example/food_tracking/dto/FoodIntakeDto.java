package com.example.food_tracking.dto;


import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.model.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodIntakeDto {
    private Long id;
    private Long userId;
    private Long dishId;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FoodIntakeDto that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(dishId, that.dishId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, dishId, date);
    }
}
