package com.example.food_tracking.service.food_intake;

import com.example.food_tracking.dto.DailyReportDto;
import com.example.food_tracking.dto.DishDto;
import com.example.food_tracking.dto.FoodIntakeDto;
import com.example.food_tracking.mapper.DishMapper;
import com.example.food_tracking.mapper.FoodIntakeMapper;
import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.model.food_intake.FoodIntake;
import com.example.food_tracking.model.user.Gender;
import com.example.food_tracking.model.user.Purpose;
import com.example.food_tracking.model.user.User;
import com.example.food_tracking.repository.food_intake.FoodIntakeRepository;
import com.example.food_tracking.service.dish.DishServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodIntakeServiceImplTest {

    @InjectMocks
    private FoodIntakeServiceImpl foodIntakeService;
    @Mock
    private FoodIntakeRepository foodIntakeRepository;
    @Mock
    private DishServiceImpl dishService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(FoodIntakeServiceImpl.class);
    }

    @Test
    void getFoodDiary() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build();
        User user2 = User.builder()
                .id(2L)
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build();
        Dish dish1 = Dish.builder()
                .id(1L)
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build();
        Dish dish2 = Dish.builder()
                .id(2L)
                .name("Омлет с сыром")
                .calories(342.0)
                .proteins(16.3)
                .carbohydrates(2.6)
                .fats(29.7)
                .build();
        Dish dish3 = Dish.builder()
                .id(3L)
                .name("Рагу из овощей")
                .calories(147.2)
                .proteins(4.5)
                .carbohydrates(15.4)
                .fats(7.9)
                .build();
        FoodIntake foodIntake1 = FoodIntake.builder()
                .id(1L)
                .user(user)
                .dish(dish1)
                .date(LocalDate.now())
                .build();
        FoodIntake foodIntake2 = FoodIntake.builder()
                .id(2L)
                .user(user)
                .dish(dish2)
                .date(LocalDate.now())
                .build();
        FoodIntake foodIntake3 = FoodIntake.builder()
                .id(3L)
                .user(user)
                .dish(dish3)
                .date(LocalDate.of(2020, 1, 1))
                .build();
        FoodIntake foodIntake4 = FoodIntake.builder()
                .id(4L)
                .user(user)
                .dish(dish2)
                .date(LocalDate.of(2020, 1, 1))
                .build();
        FoodIntake foodIntake5 = FoodIntake.builder()
                .id(5L)
                .user(user)
                .dish(dish3)
                .date(LocalDate.of(2020, 1, 4))
                .build();
        FoodIntake foodIntake6 = FoodIntake.builder()
                .id(5L)
                .user(user2)
                .dish(dish3)
                .date(LocalDate.of(2020, 1, 4))
                .build();
        when(foodIntakeRepository.findAllByUserId(user.getId())).thenReturn(List.of(foodIntake1, foodIntake2, foodIntake3, foodIntake4, foodIntake5));
        Map<LocalDate, List<FoodIntakeDto>> expected = new HashMap<>();
        expected.put(LocalDate.of(2020, 1, 1), FoodIntakeMapper.mapToDtoList(List.of(foodIntake3, foodIntake4)));
        expected.put(LocalDate.of(2020, 1, 4), FoodIntakeMapper.mapToDtoList(List.of(foodIntake5)));
        expected.put(LocalDate.now(), FoodIntakeMapper.mapToDtoList(List.of(foodIntake1, foodIntake2)));
        Map<LocalDate, List<FoodIntakeDto>> actual = foodIntakeService.getFoodDiary(user.getId());
        assertEquals(expected.size(), actual.size());
        for (LocalDate date : expected.keySet()) {
            assertEquals(expected.get(date), actual.get(date));
        }
    }

    @Test
    void getDailyReportByUserId() {
        User user = User.builder()
                .id(1L)
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build();
        User user2 = User.builder()
                .id(2L)
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build();
        Dish dish1 = Dish.builder()
                .id(1L)
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build();
        Dish dish2 = Dish.builder()
                .id(2L)
                .name("Омлет с сыром")
                .calories(342.0)
                .proteins(16.3)
                .carbohydrates(2.6)
                .fats(29.7)
                .build();
        Dish dish3 = Dish.builder()
                .id(3L)
                .name("Рагу из овощей")
                .calories(147.2)
                .proteins(4.5)
                .carbohydrates(15.4)
                .fats(7.9)
                .build();
        FoodIntake foodIntake1 = FoodIntake.builder()
                .id(1L)
                .user(user)
                .dish(dish1)
                .date(LocalDate.now())
                .build();
        FoodIntake foodIntake2 = FoodIntake.builder()
                .id(2L)
                .user(user)
                .dish(dish2)
                .date(LocalDate.now())
                .build();
        FoodIntake foodIntake3 = FoodIntake.builder()
                .id(3L)
                .user(user)
                .dish(dish3)
                .date(LocalDate.now())
                .build();
        FoodIntake foodIntake4 = FoodIntake.builder()
                .id(4L)
                .user(user)
                .dish(dish2)
                .date(LocalDate.now())
                .build();
        FoodIntake foodIntake5 = FoodIntake.builder()
                .id(5L)
                .user(user)
                .dish(dish3)
                .date(LocalDate.now())
                .build();
        FoodIntake foodIntake6 = FoodIntake.builder()
                .id(5L)
                .user(user)
                .dish(dish3)
                .date(LocalDate.now())
                .build();
        List<FoodIntake> foodIntakeList = List.of(foodIntake1, foodIntake2, foodIntake3, foodIntake4, foodIntake5, foodIntake6);
        when(foodIntakeRepository.findAllByUserIdAndDate(user.getId(), LocalDate.now())).thenReturn(foodIntakeList);
        List<DishDto> dishes = DishMapper.mapToDtoList(List.of(dish1, dish2, dish3));
        when(dishService.getDishesByIds(List.of(1L, 2L, 3L))).thenReturn(dishes);
        DailyReportDto dailyReportDto = DailyReportDto.builder()
                .sumCalories(1328.6)
                .foodIntakeList(FoodIntakeMapper.mapToDtoList(foodIntakeList))
                .build();
        assertEquals(dailyReportDto, foodIntakeService.getDailyReportByUserId(user.getId(), LocalDate.now()));
    }

    @Test
    void getCurrentCalories() {
        Dish dish1 = Dish.builder()
                .id(1L)
                .name("Цезарь")
                .calories(203.0)
                .proteins(8.2)
                .carbohydrates(6.7)
                .fats(16.5)
                .build();
        Dish dish2 = Dish.builder()
                .id(2L)
                .name("Омлет с сыром")
                .calories(342.0)
                .proteins(16.3)
                .carbohydrates(2.6)
                .fats(29.7)
                .build();
        Dish dish3 = Dish.builder()
                .id(3L)
                .name("Рагу из овощей")
                .calories(147.2)
                .proteins(4.5)
                .carbohydrates(15.4)
                .fats(7.9)
                .build();
        Map<Long, Integer> dishesWithCount = new HashMap<>();
        dishesWithCount.put(1L, 1);
        dishesWithCount.put(2L, 1);
        dishesWithCount.put(3L, 1);
        when(dishService.getDishesByIds(List.of(dish1.getId(), dish2.getId(), dish3.getId()))).thenReturn(DishMapper.mapToDtoList(List.of(dish1, dish2, dish3)));
        Double expected = 692.2;
        assertEquals(expected, foodIntakeService.getCurrentCalories(dishesWithCount));
    }
}