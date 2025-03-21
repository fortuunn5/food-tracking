package com.example.food_tracking.repository.food_intake;

import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.model.food_intake.FoodIntake;
import com.example.food_tracking.model.user.Gender;
import com.example.food_tracking.model.user.Purpose;
import com.example.food_tracking.model.user.User;
import com.example.food_tracking.repository.dish.DishRepository;
import com.example.food_tracking.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class FoodIntakeRepositoryTest {
    @Autowired
    private FoodIntakeRepository foodIntakeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DishRepository dishRepository;

    @BeforeEach
    void setUp() {
        foodIntakeRepository.deleteAll();
        userRepository.deleteAll();
        dishRepository.deleteAll();
    }

    @Test
    void save() {
        User user = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        Dish dish = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        FoodIntake newFoodIntake = FoodIntake.builder()
                .user(user)
                .dish(dish)
                .date(LocalDate.now())
                .build();
        FoodIntake savedFoodIntake = foodIntakeRepository.save(newFoodIntake);
        assertNotNull(savedFoodIntake.getId());
        assertEquals(newFoodIntake.getId(), savedFoodIntake.getId());
    }

    @Test
    void findById() {
        User user = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        Dish dish = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        FoodIntake newFoodIntake = foodIntakeRepository.save(FoodIntake.builder()
                .user(user)
                .dish(dish)
                .date(LocalDate.now())
                .build());
        FoodIntake actual = foodIntakeRepository.findById(newFoodIntake.getId()).orElseThrow();
        assertEquals(newFoodIntake, actual);
    }

    @Test
    void findById_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> foodIntakeRepository.findById(1L).orElseThrow());
    }

    @Test
    void findById_differentId() {
        User user = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        Dish dish = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        FoodIntake foodIntake1 = foodIntakeRepository.save(FoodIntake.builder()
                .user(user)
                .dish(dish)
                .date(LocalDate.now())
                .build());
        FoodIntake foodIntake2 = foodIntakeRepository.save(FoodIntake.builder()
                .user(user)
                .dish(dish)
                .date(LocalDate.now())
                .build());
        assertNotEquals(foodIntakeRepository.findById(foodIntake1.getId()), foodIntakeRepository.findById(foodIntake2.getId()));
        assertEquals(foodIntake1.getUser(), foodIntake2.getUser());
        assertEquals(foodIntake1.getDish(), foodIntake2.getDish());
    }

    @Test
    void findAll() {
        User john = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User ann = userRepository.save(User.builder()
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build());
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
        FoodIntake foodIntake1 = foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish1)
                .date(LocalDate.now())
                .build());
        FoodIntake foodIntake2 = foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish2)
                .date(LocalDate.now())
                .build());
        FoodIntake foodIntake3 = foodIntakeRepository.save(FoodIntake.builder()
                .user(ann)
                .dish(dish3)
                .date(LocalDate.now())
                .build());
        List<FoodIntake> expected = Arrays.asList(foodIntake1, foodIntake2, foodIntake3);
        List<FoodIntake> actual = foodIntakeRepository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void findAll_emptyRepository() {
        List<FoodIntake> expected = new ArrayList<>();
        List<FoodIntake> actual = foodIntakeRepository.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void update() {
        User john = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User ann = userRepository.save(User.builder()
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build());
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
        FoodIntake newFoodIntake = foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish1)
                .date(LocalDate.now())
                .build());
        newFoodIntake.setUser(ann);
        newFoodIntake.setDish(dish2);
        FoodIntake upFoodIntake = foodIntakeRepository.save(newFoodIntake);
        assertEquals(newFoodIntake.getId(), upFoodIntake.getId());
        assertEquals(newFoodIntake.getUser(), upFoodIntake.getUser());
        assertEquals(newFoodIntake.getDish(), upFoodIntake.getDish());
    }

    @Test
    void deleteById() {
        User user = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        Dish dish = dishRepository.save(Dish.builder()
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build());
        FoodIntake newFoodIntake = foodIntakeRepository.save(FoodIntake.builder()
                .user(user)
                .dish(dish)
                .date(LocalDate.now())
                .build());
        foodIntakeRepository.deleteById(newFoodIntake.getId());
        assertThrows(NoSuchElementException.class, () -> foodIntakeRepository.findById(newFoodIntake.getId()).orElseThrow());
    }

    @Test
    void deleteAll() {
        User john = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User ann = userRepository.save(User.builder()
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build());
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
        foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish1)
                .date(LocalDate.now())
                .build());
        foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish2)
                .date(LocalDate.now())
                .build());
        foodIntakeRepository.save(FoodIntake.builder()
                .user(ann)
                .dish(dish3)
                .date(LocalDate.now())
                .build());
        foodIntakeRepository.deleteAll();
        List<FoodIntake> expected = Collections.emptyList();
        assertEquals(expected, foodIntakeRepository.findAll());
    }

    @Test
    void deleteAll_emptyRepository() {
        List<FoodIntake> expected = Collections.emptyList();
        foodIntakeRepository.deleteAll();
        assertEquals(expected, foodIntakeRepository.findAll());
    }

    @Test
    void findAllByUserIdAndDate() {
        User john = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User ann = userRepository.save(User.builder()
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build());
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
        FoodIntake foodIntake1 = foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish1)
                .date(LocalDate.now())
                .build());
        FoodIntake foodIntake2 = foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish2)
                .date(LocalDate.now())
                .build());
        FoodIntake foodIntake3 = foodIntakeRepository.save(FoodIntake.builder()
                .user(ann)
                .dish(dish3)
                .date(LocalDate.now())
                .build());
        FoodIntake foodIntake4 = foodIntakeRepository.save(FoodIntake.builder()
                .user(ann)
                .dish(dish2)
                .date(LocalDate.of(2023, 11, 18))
                .build());
        List<FoodIntake> expected = Arrays.asList(foodIntake1, foodIntake2);
        assertEquals(expected, foodIntakeRepository.findAllByUserIdAndDate(john.getId(), LocalDate.now()));
    }

    @Test
    void findAllByUserId() {
        User john = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User ann = userRepository.save(User.builder()
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build());
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
        FoodIntake foodIntake1 = foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish1)
                .date(LocalDate.now())
                .build());
        FoodIntake foodIntake2 = foodIntakeRepository.save(FoodIntake.builder()
                .user(john)
                .dish(dish2)
                .date(LocalDate.of(2023, 11, 18))
                .build());
        FoodIntake foodIntake3 = foodIntakeRepository.save(FoodIntake.builder()
                .user(ann)
                .dish(dish3)
                .date(LocalDate.now())
                .build());
        FoodIntake foodIntake4 = foodIntakeRepository.save(FoodIntake.builder()
                .user(ann)
                .dish(dish2)
                .date(LocalDate.of(2020, 3, 6))
                .build());
        List<FoodIntake> expected = Arrays.asList(foodIntake1, foodIntake2);
        assertEquals(expected, foodIntakeRepository.findAllByUserId(john.getId()));
    }
}