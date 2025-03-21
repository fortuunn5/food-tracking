package com.example.food_tracking.service.food_intake;

import com.example.food_tracking.dto.DailyReportDto;
import com.example.food_tracking.dto.FoodIntakeDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface FoodIntakeService {
    FoodIntakeDto createFoodIntake(FoodIntakeDto foodIntake);
    FoodIntakeDto getById(Long id);
    List<FoodIntakeDto> getAllFoodIntakes();
    FoodIntakeDto updateFoodIntake(FoodIntakeDto foodIntake);
    void deleteFoodIntake(Long id);
    void deleteAllFoodIntakes();
    List<FoodIntakeDto> getFoodIntakesByUserIdAndDate(Long id, LocalDate date);
    Map<LocalDate, List<FoodIntakeDto>> getFoodDiary(Long userId);
    DailyReportDto getDailyReportByUserId(Long userId, LocalDate date);
    Double getCurrentCalories(Map<Long, Integer> ids);
}
