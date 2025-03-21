package com.example.food_tracking.model.user;

import com.example.food_tracking.model.food_intake.FoodIntake;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "table_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false, columnDefinition = "smallint CHECK (age > 0 AND age < 100)")
    private Byte age;
    @Column(nullable = false, columnDefinition = "double precision CHECK(weight > 10 AND weight < 500)")
    private Double weight;
    @Column(nullable = false, columnDefinition = "smallint CHECK (height > 50 AND height < 230)")
    private Short height;
    @Column(nullable = false)
    private Purpose purpose;
    @Column(nullable = false)
    private Gender gender;

    @OneToMany(mappedBy = "user")
    private Set<FoodIntake> foodIntakes;

    public User(Long id) {
        this.id = id;
    }
}
