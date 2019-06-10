package com.progex.tracker.user.service.impl;

import com.progex.tracker.user.repo.UserRepository;
import com.progex.tracker.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


   /* @Override
    public User createNewUser(UserDto user) {

        LOGGER.info("==> Creating a new user. name: [{}].", user.getName());

        User userEntity = user.toEntity();
        User savedUser = userRepository.save(userEntity);

        LOGGER.info("<== New user created. name: [{}], id: [{}].", savedUser.getName(), savedUser.getId());

        return savedUser;

    }*/

/*
    @Override
    public User updateUser(long userId, UserDto user) {

        LOGGER.info("==> Updating a user for id: [{}].", userId);

        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.warn("### UserDto not found for id: [{}].", userId);
                    throw Exceptions.getUserNotFoundException(userId);
                });

        userEntity.setName(user.getName());
        userEntity.setPhone(user.getPhone());

        */
/* We do not update role list here *//*


        User savedUser = userRepository.save(userEntity);

        LOGGER.info("<== UserDto updated for id: [{}] name: [{}].", savedUser.getId(), savedUser.getName());

        return savedUser;

    }
*/

    /*@Override
    public User getUserById(long userId) {

        LOGGER.info("==> Retrieving a user for id: [{}].", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.warn("### UserDto not found for id: [{}].", userId);
                    throw Exceptions.getUserNotFoundException(userId);
                });

        LOGGER.info("<== Returning a user for id: [{}].", user.getId());

        return user;

    }

    @Override
    public List<User> getAllUsers(int offset, int limit) {

        LOGGER.info("==> Retrieving all users by offset=[{}] and limit=[{}].", offset, limit);

        List<User> userEntities = userRepository.findAllUsers(offset, limit);

        LOGGER.info("<== Returning [{}] users.", userEntities.size());

        return userEntities;

    }*/

}
