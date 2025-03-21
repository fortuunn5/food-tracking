package com.example.food_tracking.service.dish;

import com.example.food_tracking.dto.DishDto;
import com.example.food_tracking.mapper.DishMapper;
import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.repository.dish.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;

    @Override
    public DishDto createDish(DishDto newDishDto) {
        Dish newDish = DishMapper.map(newDishDto);
        Dish createdDish = dishRepository.save(newDish);
        return DishMapper.map(createdDish);
    }

    @Override
    public DishDto getById(Long id) {
        Dish dish = dishRepository.findById(id).orElseThrow();
        return DishMapper.map(dish);
    }

    @Override
    public List<DishDto> getAllDishes() {
        List<Dish> allDishes = dishRepository.findAll();
        return DishMapper.mapToDtoList(allDishes);
    }

    @Override
    public DishDto updateDish(DishDto upDishDto) {
        Dish upDish = DishMapper.map(upDishDto);
        Dish updatedDish = dishRepository.save(upDish);
        return DishMapper.map(updatedDish);
    }

    @Override
    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public void deleteAllDishes() {
        dishRepository.deleteAll();
    }

    @Override
    public List<DishDto> getDishesByIds(List<Long> ids) {
        List<Dish> dishesByIds = dishRepository.findAllByIdIn(ids);
        return DishMapper.mapToDtoList(dishesByIds);
    }
}
