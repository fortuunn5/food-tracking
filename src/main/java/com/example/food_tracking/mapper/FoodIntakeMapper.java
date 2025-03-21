package com.example.food_tracking.mapper;

import com.example.food_tracking.dto.FoodIntakeDto;
import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.model.food_intake.FoodIntake;
import com.example.food_tracking.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class FoodIntakeMapper {
    public static FoodIntake map(FoodIntakeDto foodIntakeDto) {
        return FoodIntake.builder()
                .id(foodIntakeDto.getId())
                .user(new User(foodIntakeDto.getUserId()))
                .dish(new Dish(foodIntakeDto.getDishId()))
                .date(foodIntakeDto.getDate())
                .build();
    }

    public static FoodIntakeDto map(FoodIntake foodIntake) {
        return FoodIntakeDto.builder()
                .id(foodIntake.getId())
                .userId(foodIntake.getUser().getId())
                .dishId(foodIntake.getDish().getId())
                .date(foodIntake.getDate())
                .build();
    }

    public static List<FoodIntake> map(List<FoodIntakeDto> foodIntakeDtoList) {
        return foodIntakeDtoList.stream().map(FoodIntakeMapper::map).collect(Collectors.toList());
    }

    public static List<FoodIntakeDto> mapToDtoList(List<FoodIntake> foodIntakeList) {
        return foodIntakeList.stream().map(FoodIntakeMapper::map).collect(Collectors.toList());
    }
}
