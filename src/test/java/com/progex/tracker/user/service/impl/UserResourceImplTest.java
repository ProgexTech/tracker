package com.progex.tracker.user.service.impl;

import com.progex.tracker.user.repo.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static junit.framework.TestCase.assertEquals;

import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserResourceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

/*    @Test
    public void shouldReturnUserWhenSaving() {
        UserDto user = new UserDto() {{
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};

        when(userRepository.save(user.toEntity()))
                .thenReturn(user.toEntity());

        User userEntity = userService.createNewUser(user);
        assertEquals(user.getName(), userEntity.getName());
        assertEquals(user.getPhone(), userEntity.getPhone());
        assertEquals(user.getAddress(), userEntity.getAddress());
        assertEquals(user.getRoles(), userEntity.getRoles());
    }*/

   /* @Test
    public void shouldReturnValidUserWhenCallingGetByIdWithAValidId() {
        User testEntity = new User() {{
            this.setId(1L);
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(testEntity));

        User userEntity = userService.getUserById(1L);
        assertEquals(testEntity.getId(), userEntity.getId());
        assertEquals(testEntity.getName(), userEntity.getName());
        assertEquals(testEntity.getPhone(), userEntity.getPhone());
        assertEquals(testEntity.getAddress(), userEntity.getAddress());
        assertEquals(testEntity.getRoles(), userEntity.getRoles());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenCallingGetByIdWithAnInvalidId() {
        User testEntity = new User() {{
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
        UserDto user = new UserDto() {{
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};
        User testEntity = new User() {{
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

        User userEntity = userService.updateUser(1L, user);
        assertEquals(testEntity.getId(), userEntity.getId());
        assertEquals(testEntity.getName(), userEntity.getName());
        assertEquals(testEntity.getPhone(), userEntity.getPhone());
        assertEquals(testEntity.getAddress(), userEntity.getAddress());
        assertEquals(testEntity.getRoles(), userEntity.getRoles());
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenCallingUpdateWithAnInvalidId() {
        UserDto user = new UserDto() {{
            this.setName("John");
            this.setPhone("0778909854");
            this.setAddress("Test Address");
            this.setRoles(Arrays.asList("manager", "supervisor"));
        }};
        User testEntity = new User() {{
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
    }*/

   /* @Test
    public void shouldReturnCustomerListWhenCallingGetAllCustomers() {
        when(userRepository.findAllUsers(0, 10))
                .thenReturn(Arrays.asList(
                        new User(),
                        new User(),
                        new User()
                ));

        List<User> userEntities = userService.getAllUsers(0, 10);
        assertEquals(3, userEntities.size());
    }*/

}
