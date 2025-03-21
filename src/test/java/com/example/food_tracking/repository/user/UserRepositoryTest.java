package com.example.food_tracking.repository.user;

import com.example.food_tracking.model.dish.Dish;
import com.example.food_tracking.model.user.Gender;
import com.example.food_tracking.model.user.Purpose;
import com.example.food_tracking.model.user.User;
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
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void save() {
        User user = User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build();
        User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertEquals(user.getEmail(), savedUser.getEmail());
    }

    @Test
    void save_emailExist() {
        userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User newUserWithExistEmail = User.builder()
                .name("Ann")
                .email("john@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(newUserWithExistEmail));
    }

    @Test
    void findById() {
        User expected = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User actual = userRepository.findById(expected.getId()).orElseThrow();
        assertEquals(expected, actual);
    }

    @Test
    void findById_throwsNoSuchElementException() {
        assertThrows(NoSuchElementException.class, () -> userRepository.findById(1L).orElseThrow());
    }

    @Test
    void findById_differentId() {
        User user1 = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User user2 = userRepository.save(User.builder()
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build());
        assertNotEquals(userRepository.findById(user1.getId()), userRepository.findById(user2.getId()));
    }

    @Test
    void findAll() {
        User user1 = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User user2 = userRepository.save(User.builder()
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build());
        List<User> expected = Arrays.asList(user1, user2);
        assertEquals(expected, userRepository.findAll());
    }

    @Test
    void findAll_EmptyRepository() {
        List<User> expected = Collections.emptyList();
        assertEquals(expected, userRepository.findAll());
    }

    @Test
    void findByEmail() {
        User expected = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        User actual = userRepository.findByEmail(expected.getEmail());
        assertEquals(expected, actual);
    }

    @Test
    void findByEmail_NotExistEmail() {
        User user = userRepository.findByEmail("john@gmail.example");
        assertNull(user);
    }

    @Test
    void update() {
        User user = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        user.setName("Ann");
        user.setEmail("ann@gmail.example");
        User updatedUser = userRepository.save(user);
        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals("ann@gmail.example", updatedUser.getEmail());
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
        userRepository.deleteById(user.getId());
        assertThrows(NoSuchElementException.class, () -> userRepository.findById(user.getId()).orElseThrow());
    }

    @Test
    void deleteAll() {
        userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        userRepository.save(User.builder()
                .name("Ann")
                .email("ann@gmail.example")
                .age((byte) 37)
                .weight(66.0)
                .height((short) 150)
                .purpose(Purpose.WEIGHT_GAIN)
                .gender(Gender.FEMALE)
                .build());
        userRepository.deleteAll();
        List<User> expected = Collections.emptyList();
        assertEquals(expected, userRepository.findAll());
    }

    @Test
    void deleteAll_EmptyRepository() {
        List<User> expected = Collections.emptyList();
        userRepository.deleteAll();
        assertEquals(expected, userRepository.findAll());
    }

    @Test
    void deleteByEmail() {
        User user = userRepository.save(User.builder()
                .name("John")
                .email("john@gmail.example")
                .age((byte) 23)
                .weight(72.0)
                .height((short) 171)
                .purpose(Purpose.WEIGHT_MAINTENANCE)
                .gender(Gender.MALE)
                .build());
        userRepository.deleteByEmail(user.getEmail());
        assertThrows(NoSuchElementException.class, () -> userRepository.findById(user.getId()).orElseThrow());
    }
}