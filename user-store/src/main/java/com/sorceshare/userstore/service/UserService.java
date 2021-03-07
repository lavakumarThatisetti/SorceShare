package com.sorceshare.userstore.service;

import com.sorceshare.userstore.model.UpdateUserPassword;
import com.sorceshare.userstore.model.UserCreate;
import com.sorceshare.userstore.model.User;


public interface UserService {

    UserCreate createUser(final User user);

    UserCreate updateUser(long userId, final User user);

    UserCreate updateUserPassword(long userId, final UpdateUserPassword updateUserPassword);

    UserCreate getUser(long userId);

    String deactivateUser(long userId);
}
