package com.progex.tracker.user.service.impl;

import com.progex.tracker.exceptions.EntityNotFoundException;
import com.progex.tracker.user.dto.User;
import com.progex.tracker.user.entity.UserEntity;
import com.progex.tracker.user.repo.UserRepository;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserResourceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void shouldReturnUserWhenSaving() {
        User user = new User() {{
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};

        when(userRepository.save(user.toEntity()))
                .thenReturn(user.toEntity());

        UserEntity userEntity = userService.createNewUser(user);
        assertEquals(user.getName(), userEntity.getName());
        assertEquals(user.getPhone(), userEntity.getPhone());
        assertEquals(user.getAddress(), userEntity.getAddress());
        assertEquals(user.getRoles(), userEntity.getRoles());
    }

    @Test
    public void shouldReturnValidUserWhenCallingGetByIdWithAValidId() {
        UserEntity testEntity = new UserEntity() {{
            this.setId(1L);
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));

        UserEntity userEntity = userService.getUserById(1L);
        assertEquals(testEntity.getId(), userEntity.getId());
        assertEquals(testEntity.getName(), userEntity.getName());
        assertEquals(testEntity.getPhone(), userEntity.getPhone());
        assertEquals(testEntity.getAddress(), userEntity.getAddress());
        assertEquals(testEntity.getRoles(), userEntity.getRoles());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenCallingGetByIdWithAnInvalidId() {
        UserEntity testEntity = new UserEntity() {{
            this.setId(1L);
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));

        userService.getUserById(2L);
    }

    @Test
    public void shouldReturnUpdatedEntityWhenCallingUpdateWithAValidId() {
        User user = new User() {{
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};
        UserEntity testEntity = new UserEntity() {{
            this.setId(1L);
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));
        when(userRepository.save(testEntity))
                .thenReturn(testEntity);

        UserEntity userEntity = userService.updateUser(1L, user);
        assertEquals(testEntity.getId(), userEntity.getId());
        assertEquals(testEntity.getName(), userEntity.getName());
        assertEquals(testEntity.getPhone(), userEntity.getPhone());
        assertEquals(testEntity.getAddress(), userEntity.getAddress());
        assertEquals(testEntity.getRoles(), userEntity.getRoles());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenCallingUpdateWithAnInvalidId() {
        User user = new User() {{
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};
        UserEntity testEntity = new UserEntity() {{
            this.setId(1L);
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));
        when(userRepository.save(testEntity))
                .thenReturn(testEntity);

        userService.updateUser(2L, user);
    }

    @Test
    public void shouldReturnCustomerListWhenCallingGetAllCustomers() {
        when(userRepository.findAllUsers(0, 10))
                .thenReturn(Arrays.asList(
                        new UserEntity(),
                        new UserEntity(),
                        new UserEntity()
                ));

        List<UserEntity> userEntities = userService.getAllUsers(0, 10);
        assertEquals(3, userEntities.size());
    }

}
