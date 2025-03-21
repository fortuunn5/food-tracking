package com.example.food_tracking.service.user;

import com.example.food_tracking.dto.FoodIntakeDto;
import com.example.food_tracking.dto.MeetDailyDto;
import com.example.food_tracking.dto.UserDto;
import com.example.food_tracking.model.user.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UserService {
    UserDto createUser(UserDto user);
    UserDto getById(Long id);
    UserDto getByEmail(String email);
    List<UserDto> getAllUsers();
    UserDto updateUser(UserDto user);
    void deleteUser(Long id);
    void deleteUserByEmail(String email);
    void deleteAllUsers();
    MeetDailyDto isMeetDailyCalories(Long id, LocalDate date);
    Double getCalorieIntakeByUserId(Long userId);
}