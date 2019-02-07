package com.progex.tracker.user.service;

import com.progex.tracker.user.dto.User;
import com.progex.tracker.user.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity createNewUser(User user);

    UserEntity updateUser(long userId, User user);

    UserEntity getUserById(long userId);

    List<UserEntity> getAllUsers(int offset, int limit);

}
