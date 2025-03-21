package com.example.food_tracking.controller;

import com.example.food_tracking.dto.DailyReportDto;
import com.example.food_tracking.dto.FoodIntakeDto;
import com.example.food_tracking.dto.MeetDailyDto;
import com.example.food_tracking.dto.UserDto;
import com.example.food_tracking.model.user.User;
import com.example.food_tracking.service.food_intake.FoodIntakeService;
import com.example.food_tracking.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final FoodIntakeService foodIntakeService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam String email) {
        return new ResponseEntity<>(userService.getByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.updateUser(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserByEmail(@RequestParam String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllUsers() {
        userService.deleteAllUsers();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/dailyReport")
    public ResponseEntity<DailyReportDto> getDailyReport(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return new ResponseEntity<>(foodIntakeService.getDailyReportByUserId(id, date), HttpStatus.OK);
    }

    @GetMapping("/{id}/meetDaily")
    public ResponseEntity<MeetDailyDto> isMeetDaily(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return new ResponseEntity<>(userService.isMeetDailyCalories(id, date), HttpStatus.OK);
    }

    @GetMapping("/{id}/foodDiary")
    public ResponseEntity<Map<LocalDate, List<FoodIntakeDto>>> getFoodDiary(@PathVariable Long id) {
        return new ResponseEntity<>(foodIntakeService.getFoodDiary(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/calorieIntake")
    public ResponseEntity<Double> getCalorieIntake(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getCalorieIntakeByUserId(id), HttpStatus.OK);
    }
}
