package com.progex.tracker.user.resource;

import com.progex.tracker.user.dto.UserDto;
import com.progex.tracker.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

   /* @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value = "offset", required = true) int offset,
                                                  @RequestParam(value = "limit", required = true) int limit) {

        LOGGER.info("Retrieving all users. offset=[{}] limit=[{}].", offset, limit);

        List<User> userEntities = userService.getAllUsers(offset, limit);
        List<UserDto> users = userEntities.stream()
                .map(UserDto::toDto).collect(Collectors.toList());

        LOGGER.info("Returning all users. count=[{}].", userEntities.size());

        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable long userId) {

        LOGGER.info("Retrieving a user for id=[{}].", userId);

        User userEntity = userService.getUserById(userId);
        UserDto user = UserDto.toDto(userEntity);

        LOGGER.info("Returning a user for id=[{}].", userEntity.getId());

        return ResponseEntity.ok(user);
    }*/

  /*  @PostMapping("/users")
    public ResponseEntity<UserDto> createNewUser(@Valid @RequestBody UserDto user) {

        LOGGER.info("Creating a new user: [{}].", user);

        User userEntity = userService.createNewUser(user);
        UserDto createdUser = UserDto.toDto(userEntity);

        LOGGER.info("New user created successfully, id: [{}].", userEntity.getId());

        return ResponseEntity.ok(createdUser);
    }*/

    @PutMapping("/users")
    public ResponseEntity<UserDto> updateUser() {
        return null;
    }

}
