package com.progex.tracker.user.resource;

import com.progex.tracker.user.dto.User;
import com.progex.tracker.user.entity.UserEntity;
import com.progex.tracker.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(@RequestParam(value = "offset", required = true) int offset,
                                                  @RequestParam(value = "limit", required = true) int limit) {

        LOGGER.info("Retrieving all users. offset=[{}] limit=[{}].", offset, limit);

        List<UserEntity> userEntities = userService.getAllUsers(offset, limit);
        List<User> users = userEntities.stream()
                .map(User::toDto).collect(Collectors.toList());

        LOGGER.info("Returning all users. count=[{}].", userEntities.size());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable long userId) {

        LOGGER.info("Retrieving a user for id=[{}].", userId);

        UserEntity userEntity = userService.getUserById(userId);
        User user = User.toDto(userEntity);

        LOGGER.info("Returning a user for id=[{}].", userEntity.getId());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {

        LOGGER.info("Creating a new user: [{}].", user);

        UserEntity userEntity = userService.createNewUser(user);
        User createdUser = User.toDto(userEntity);

        LOGGER.info("New user created successfully, id: [{}].", userEntity.getId());

        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser() {
        return null;
    }

}
