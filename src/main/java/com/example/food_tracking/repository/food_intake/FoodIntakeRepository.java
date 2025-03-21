package com.example.food_tracking.repository.food_intake;

import com.example.food_tracking.model.food_intake.FoodIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface FoodIntakeRepository extends JpaRepository<FoodIntake, Long> {
    List<FoodIntake> findAllByUserIdAndDate(Long userId, LocalDate date);
    List<FoodIntake> findAllByUserId(Long userId);
}
