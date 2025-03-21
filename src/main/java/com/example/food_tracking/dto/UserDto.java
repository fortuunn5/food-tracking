package com.example.food_tracking.dto;

import com.example.food_tracking.model.user.Gender;
import com.example.food_tracking.model.user.Purpose;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Byte age;
    private Double weight;
    private Short height;
    private String purpose;
    private String gender;

    public UserDto(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UserDto userDto)) return false;
        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(age, userDto.age) && Objects.equals(weight, userDto.weight) && Objects.equals(height, userDto.height) && Objects.equals(purpose, userDto.purpose) && Objects.equals(gender, userDto.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, age, weight, height, purpose, gender);
    }
}
