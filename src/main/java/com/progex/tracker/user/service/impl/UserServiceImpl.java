package com.progex.tracker.user.service.impl;

import com.progex.tracker.customer.entity.CustomerEntity;
import com.progex.tracker.exceptions.Exceptions;
import com.progex.tracker.user.dto.User;
import com.progex.tracker.user.entity.UserEntity;
import com.progex.tracker.user.repo.UserRepository;
import com.progex.tracker.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserEntity createNewUser(User user) {

        LOGGER.info("==> Creating a new user. name: [{}].", user.getName());

        UserEntity userEntity = user.toEntity();
        UserEntity savedUser = userRepository.save(userEntity);

        LOGGER.info("<== New user created. name: [{}], id: [{}].", savedUser.getName(), savedUser.getId());

        return savedUser;

    }

    @Override
    public UserEntity updateUser(long userId, User user) {

        LOGGER.info("==> Updating a user for id: [{}].", userId);

        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.warn("### User not found for id: [{}].", userId);
                    throw Exceptions.getUserNotFoundException(userId);
                });

        userEntity.setName(user.getName());
        userEntity.setPhone(user.getPhone());

        /* We do not update role list here */

        UserEntity savedUser = userRepository.save(userEntity);

        LOGGER.info("<== User updated for id: [{}].", savedUser.getName(), savedUser.getId());

        return savedUser;

    }

    @Override
    public UserEntity getUserById(long userId) {

        LOGGER.info("==> Retrieving a user for id: [{}].", userId);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    LOGGER.warn("### User not found for id: [{}].", userId);
                    throw Exceptions.getUserNotFoundException(userId);
                });

        LOGGER.info("<== Returning a user for id: [{}].", user.getId());

        return user;

    }

    @Override
    public List<UserEntity> getAllUsers(int offset, int limit) {

        LOGGER.info("==> Retrieving all users by offset=[{}] and limit=[{}].", offset, limit);

        List<UserEntity> userEntities = userRepository.findAllUsers(offset, limit);

        LOGGER.info("<== Returning [{}] users.", userEntities.size());

        return userEntities;

    }

}
