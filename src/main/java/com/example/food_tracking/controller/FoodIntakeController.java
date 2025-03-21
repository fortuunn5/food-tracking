package com.example.food_tracking.controller;

import com.example.food_tracking.dto.FoodIntakeDto;
import com.example.food_tracking.service.food_intake.FoodIntakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/food_intake")
public class FoodIntakeController {
    private final FoodIntakeService foodIntakeService;

    @PostMapping
    public ResponseEntity<FoodIntakeDto> createFoodIntake(@RequestBody FoodIntakeDto foodIntake) {
        return new ResponseEntity<>(foodIntakeService.createFoodIntake(foodIntake), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FoodIntakeDto> getFoodIntakeById(@RequestBody Long id) {
        return new ResponseEntity<>(foodIntakeService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FoodIntakeDto>> getAllFoodIntakes() {
        return new ResponseEntity<>(foodIntakeService.getAllFoodIntakes(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<FoodIntakeDto> updateFoodIntake(@RequestBody FoodIntakeDto foodIntake) {
        return new ResponseEntity<>(foodIntakeService.updateFoodIntake(foodIntake), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFoodIntakeById(@RequestBody Long id) {
        foodIntakeService.deleteFoodIntake(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> deleteAllFoodIntakes() {
        foodIntakeService.deleteAllFoodIntakes();
        return ResponseEntity.ok().build();
    }
}
