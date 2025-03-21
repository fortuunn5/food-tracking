package com.example.food_tracking.service.food_intake;

import com.example.food_tracking.dto.DailyReportDto;
import com.example.food_tracking.dto.DishDto;
import com.example.food_tracking.dto.FoodIntakeDto;
import com.example.food_tracking.mapper.FoodIntakeMapper;
import com.example.food_tracking.model.food_intake.FoodIntake;
import com.example.food_tracking.repository.food_intake.FoodIntakeRepository;
import com.example.food_tracking.service.dish.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodIntakeServiceImpl implements FoodIntakeService {
    private final FoodIntakeRepository foodIntakeRepository;
    private final DishService dishService;

    @Override
    public FoodIntakeDto createFoodIntake(FoodIntakeDto newFoodIntakeDto) {
        FoodIntake newFoodIntake = FoodIntakeMapper.map(newFoodIntakeDto);
        FoodIntake createdFoodIntake = foodIntakeRepository.save(newFoodIntake);
        return FoodIntakeMapper.map(createdFoodIntake);
    }

    @Override
    public FoodIntakeDto getById(Long id) {
        FoodIntake foodIntake = foodIntakeRepository.findById(id).orElseThrow();
        return FoodIntakeMapper.map(foodIntake);
    }

    @Override
    public List<FoodIntakeDto> getAllFoodIntakes() {
        List<FoodIntake> allFoodIntakes = foodIntakeRepository.findAll();
        return FoodIntakeMapper.mapToDtoList(allFoodIntakes);
    }

    @Override
    public FoodIntakeDto updateFoodIntake(FoodIntakeDto upFoodIntakeDto) {
        FoodIntake upFoodIntake = FoodIntakeMapper.map(upFoodIntakeDto);
        FoodIntake updatedFoodIntake = foodIntakeRepository.save(upFoodIntake);
        return FoodIntakeMapper.map(updatedFoodIntake);
    }

    @Override
    public void deleteFoodIntake(Long id) {
        foodIntakeRepository.deleteById(id);
    }

    @Override
    public void deleteAllFoodIntakes() {
        foodIntakeRepository.deleteAll();
    }

    @Override
    public List<FoodIntakeDto> getFoodIntakesByUserIdAndDate(Long userId, LocalDate date) {
        List<FoodIntake> foodIntakesByUserAndDate = foodIntakeRepository.findAllByUserIdAndDate(userId, date);
        return FoodIntakeMapper.mapToDtoList(foodIntakesByUserAndDate);
    }

    @Override
    public Map<LocalDate, List<FoodIntakeDto>> getFoodDiary(Long userId) {
        List<FoodIntakeDto> allFoodIntakes = FoodIntakeMapper.mapToDtoList(foodIntakeRepository.findAllByUserId(userId));
        Map<LocalDate, List<FoodIntakeDto>> foodDiary = allFoodIntakes.stream()
                .collect(Collectors.groupingBy(FoodIntakeDto::getDate));
        return foodDiary;
    }

    @Override
    public DailyReportDto getDailyReportByUserId(Long userId, LocalDate date) {
        List<FoodIntakeDto> allFoodIntakes = getFoodIntakesByUserIdAndDate(userId, date);
        Map<Long, Integer> dishesWithCount = allFoodIntakes.stream()
                .collect(Collectors.groupingBy(x -> x.getDishId(), Collectors.collectingAndThen(Collectors.toList(), List::size)));
        Double caloriesSum = getCurrentCalories(dishesWithCount);
        return new DailyReportDto(caloriesSum, allFoodIntakes);
    }

    @Override
    public Double getCurrentCalories(Map<Long, Integer> ids) {
        List<DishDto> dishes = dishService.getDishesByIds(new ArrayList<>(ids.keySet()));
        Double calorieSum = dishes.stream()
                .mapToDouble(x -> ids.get(x.getId()) * x.getCalories())
                .sum();
        return Math.round(calorieSum * 100) / 100.0;
    }
}