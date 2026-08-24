package com.dragan.user;

import java.util.UUID;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User[] getUsers() {
        return userDao.getUsers();
    }

    public User getUserById(UUID uuid) {
        return userDao.getUserById(uuid);
    }

}
