package com.example.food_tracking.model.dish;

import com.example.food_tracking.model.food_intake.FoodIntake;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private Double calories;
    @Column(nullable = false)
    private Double proteins;
    @Column(nullable = false)
    private Double carbohydrates;
    @Column(nullable = false)
    private Double fats;

    @OneToMany(mappedBy = "dish")
    private Set<FoodIntake> foodIntakes;

    public Dish(Long id) {
        this.id = id;
    }

    public Dish(String name, Double calories, Double proteins, Double carbohydrates, Double fats) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }
}
