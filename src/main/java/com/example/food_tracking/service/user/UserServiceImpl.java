package com.example.food_tracking.service.user;

import com.example.food_tracking.dto.DishDto;
import com.example.food_tracking.dto.FoodIntakeDto;
import com.example.food_tracking.dto.MeetDailyDto;
import com.example.food_tracking.dto.UserDto;
import com.example.food_tracking.mapper.UserMapper;
import com.example.food_tracking.model.user.Gender;
import com.example.food_tracking.model.user.Purpose;
import com.example.food_tracking.model.user.User;
import com.example.food_tracking.repository.user.UserRepository;
import com.example.food_tracking.service.dish.DishService;
import com.example.food_tracking.service.food_intake.FoodIntakeService;
import com.example.food_tracking.utils.CalculationCaloriesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FoodIntakeService foodIntakeService;

    @Override
    public UserDto createUser(UserDto user) {
        User mapped = UserMapper.map(user);
        User saved = userRepository.save(mapped);
        return UserMapper.map(saved);
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        return UserMapper.map(user);
    }

    @Override
    public UserDto getByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return UserMapper.map(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = userRepository.findAll();
        return UserMapper.mapToDtoList(allUsers);
    }

    @Override
    public UserDto updateUser(UserDto upUserDto) {
        User upUser = UserMapper.map(upUserDto);
        User updatedUser = userRepository.save(upUser);
        return UserMapper.map(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    @Override
    public MeetDailyDto isMeetDailyCalories(Long id, LocalDate date) {
        Double dailyCalorieRequirement = getCalorieIntakeByUserId(id);
        List<FoodIntakeDto> foodIntakesByUserAndDate = foodIntakeService.getFoodIntakesByUserIdAndDate(id, date);
        List<Long> ids = foodIntakesByUserAndDate.stream()
                .map(x -> x.getDishId())
                .collect(Collectors.toList());
        Map<Long, Integer> dishesWithCount = foodIntakesByUserAndDate.stream()
                .collect(Collectors.groupingBy(x -> x.getDishId(), Collectors.collectingAndThen(Collectors.toList(), List::size)));
        Double res = foodIntakeService.getCurrentCalories(dishesWithCount);
        String message = res <= dailyCalorieRequirement ?
                "Пользователь уложился в дневную норму калорий" :
                "Пользователь не уложился в дневную норму калорий";
        MeetDailyDto meetDailyDto = new MeetDailyDto(dailyCalorieRequirement, res, message);
        return meetDailyDto;
    }

    @Override
    public Double getCalorieIntakeByUserId(Long userId) {
        UserDto user = getById(userId);
        return CalculationCaloriesUtil.calculateCalories(user.getWeight(),
                user.getHeight(),
                user.getAge(),
                Gender.byName(user.getGender()),
                Purpose.byName(user.getPurpose()));
    }
}
