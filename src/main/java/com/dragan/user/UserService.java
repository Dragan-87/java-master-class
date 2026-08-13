package com.dragan.user;


import com.dragan.exceptions.ResourceNotFoundException;

import java.util.Scanner;
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
        while (true) {
            try {
                return userDao.getUserById(uuid);
            } catch (ResourceNotFoundException e) {
                System.out.println("User UUID not found, try again:");
                Scanner scanner = new Scanner(System.in);
                getUserById(UUID.fromString(scanner.nextLine()));
            }
        }
    }

}
