package com.example.food_tracking.repository.dish;

import com.example.food_tracking.model.dish.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DishRepositoryTest {
    @Autowired
    private DishRepository dishRepository;

    @BeforeEach
    void setUp() {
        dishRepository.deleteAll();
    }

    @Test
    void save() {
        Dish newDish = Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build();
        Dish savedDish = dishRepository.save(newDish);
        assertNotNull(savedDish);
        assertEquals(savedDish.getName(), newDish.getName());
    }

    @Test
    void save_nameExist() {
        dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        Dish newDishWithExistName = Dish.builder()
                .name("Цезарь")
                .calories(342.0)
                .proteins(16.3)
                .carbohydrates(2.6)
                .fats(29.7)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> dishRepository.save(newDishWithExistName));
    }

    @Test
    void findById() {
        Dish expected = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        Dish actual = dishRepository.findById(expected.getId()).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    void findById_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> dishRepository.findById(1L).orElseThrow());
    }

    @Test
    void findById_differentId() {
        Dish dish1 = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        Dish dish2 = dishRepository.save(Dish.builder()
                .name("Омлет с сыром")
                .calories(342.0)
                .proteins(16.3)
                .carbohydrates(2.6)
                .fats(29.7)
                .build());
        assertNotEquals(dishRepository.findById(dish1.getId()), dishRepository.findById(dish2.getId()));
    }

    @Test
    void findAll() {
        Dish dish1 = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        Dish dish2 = dishRepository.save(Dish.builder()
                .name("Омлет с сыром")
                .calories(342.0)
                .proteins(16.3)
                .carbohydrates(2.6)
                .fats(29.7)
                .build());
        List<Dish> expected = Arrays.asList(dish1, dish2);
        assertEquals(expected, dishRepository.findAll());
    }

    @Test
    void findAll_emptyRepository() {
        List<Dish> expected = Collections.emptyList();
        assertEquals(expected, dishRepository.findAll());
    }

    @Test
    void update() {
        Dish dish = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        dish.setName("Омлет с сыром");
        Dish updatedDish = dishRepository.save(dish);
        assertNotNull(updatedDish);
        assertEquals(dish.getId(), updatedDish.getId());
        assertEquals("Омлет с сыром", updatedDish.getName());
    }

    @Test
    void deleteById() {
        Dish dish = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        dishRepository.deleteById(dish.getId());
        assertThrows(NoSuchElementException.class, () -> dishRepository.findById(dish.getId()).orElseThrow());
    }

    @Test
    void deleteAll() {
        dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        dishRepository.save(Dish.builder()
                .name("Омлет с сыром")
                .calories(342.0)
                .proteins(16.3)
                .carbohydrates(2.6)
                .fats(29.7)
                .build());
        dishRepository.deleteAll();
        List<Dish> expected = Collections.emptyList();
        assertEquals(expected, dishRepository.findAll());
    }

    @Test
    void deleteAll_emptyRepository() {
        List<Dish> expected = Collections.emptyList();
        dishRepository.deleteAll();
        assertEquals(expected, dishRepository.findAll());
    }

    @Test
    void findAllByIdIn() {
        Dish dish1 = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        Dish dish2 = dishRepository.save(Dish.builder()
                .name("Омлет с сыром")
                .calories(342.0)
                .proteins(16.3)
                .carbohydrates(2.6)
                .fats(29.7)
                .build());
        Dish dish3 = dishRepository.save(Dish.builder()
                .name("Рагу из овощей")
                .calories(147.2)
                .proteins(4.5)
                .carbohydrates(15.4)
                .fats(7.9)
                .build());
        List<Dish> expected = Arrays.asList(dish1, dish2);
        List<Long> ids = Arrays.asList(dish1.getId(), dish2.getId());
        assertEquals(expected, dishRepository.findAllByIdIn(ids));
    }

    @Test
    void findAllByIdIn_emptyRepository() {
        List<Dish> expected = Collections.emptyList();
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        assertEquals(expected, dishRepository.findAllByIdIn(ids));
    }
}