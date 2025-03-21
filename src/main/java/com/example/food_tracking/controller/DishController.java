package com.example.food_tracking.controller;

import com.example.food_tracking.dto.DishDto;
import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.service.dish.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;

    @PostMapping
    public ResponseEntity<DishDto> createDish(@RequestBody DishDto dish) {
        return new ResponseEntity<>(dishService.createDish(dish), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DishDto> getDishById(@PathVariable Long id) {
        return new ResponseEntity<>(dishService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<DishDto>> getAllDishes() {
        return new ResponseEntity<>(dishService.getAllDishes(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<DishDto> updateDish(@RequestBody DishDto dish) {
        return new ResponseEntity<>(dishService.updateDish(dish), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDishById(@PathVariable Long id) {
        dishService.deleteDish(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllDishes() {
        dishService.deleteAllDishes();
        return ResponseEntity.ok().build();
    }
}
