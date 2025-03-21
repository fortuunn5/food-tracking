package com.example.food_tracking.model.food_intake;

import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(nullable = false)
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FoodIntake that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(dish, that.dish) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, dish, date);
    }
}
