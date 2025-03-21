package com.example.food_tracking.service.user;

import com.example.food_tracking.dto.MeetDailyDto;
import com.example.food_tracking.mapper.FoodIntakeMapper;
import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.model.food_intake.FoodIntake;
import com.example.food_tracking.model.user.Gender;
import com.example.food_tracking.model.user.Purpose;
import com.example.food_tracking.model.user.User;
import com.example.food_tracking.repository.user.UserRepository;
import com.example.food_tracking.service.food_intake.FoodIntakeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private FoodIntakeService foodIntakeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(UserServiceImpl.class);
    }

    @Test
    void isMeetDailyCalories() {
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
                .date(LocalDate.of(2023, 11, 18))
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        List<FoodIntake> foodIntakes = Arrays.asList(foodIntake1, foodIntake2, foodIntake3, foodIntake4);
        when(foodIntakeService.getFoodIntakesByUserIdAndDate(user.getId(), LocalDate.now())).thenReturn(FoodIntakeMapper.mapToDtoList(foodIntakes));
        Map<Long, Integer> dishesWithCount = foodIntakeService.getFoodIntakesByUserIdAndDate(user.getId(), LocalDate.now()).stream()
                .collect(Collectors.groupingBy(x -> x.getDishId(), Collectors.collectingAndThen(Collectors.toList(), List::size)));
        when(foodIntakeService.getCurrentCalories(dishesWithCount)).thenReturn(692.2);
        MeetDailyDto expected = MeetDailyDto.builder()
                .message("Пользователь уложился в дневную норму калорий")
                .calorieCurrent(692.2)
                .calorieIntake(1742.86)
                .build();
        assertEquals(expected, userService.isMeetDailyCalories(user.getId(), LocalDate.now()));
    }

    @Test
    void getCalorieIntakeByUserId() {
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
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        assertEquals(1742.86, userService.getCalorieIntakeByUserId(1L));
    }
}