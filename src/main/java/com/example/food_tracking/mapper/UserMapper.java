package com.example.food_tracking.mapper;

import com.example.food_tracking.dto.UserDto;
import com.example.food_tracking.model.user.Gender;
import com.example.food_tracking.model.user.Purpose;
import com.example.food_tracking.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User map(UserDto userDto) {

        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .age(userDto.getAge())
                .weight(userDto.getWeight())
                .height(userDto.getHeight())
                .purpose(Purpose.byName(userDto.getPurpose()))
                .gender(Gender.byName(userDto.getGender()))
                .build();
    }

    public static UserDto map(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .age(user.getAge())
                .weight(user.getWeight())
                .height(user.getHeight())
                .purpose(user.getPurpose().getPurpose())
                .gender(user.getGender().getGender())
                .build();
    }

    public static List<User> map(List<UserDto> userDtoList) {
        return userDtoList.stream().map(UserMapper::map).collect(Collectors.toList());
    }

    public static List<UserDto> mapToDtoList(List<User> userList) {
        return userList.stream().map(UserMapper::map).collect(Collectors.toList());
    }
}
