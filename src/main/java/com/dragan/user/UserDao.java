package com.dragan.user;

import com.dragan.exceptions.ResourceNotFoundException;

import java.util.Objects;
import java.util.UUID;

public class UserDao {

    User[] users = {
            new User(UUID.randomUUID(), "Dragan Saric"),
            new User(UUID.randomUUID(), "Max Musterman")
    };


    public User getUserById(UUID uuid) throws ResourceNotFoundException {
        for (User user : users) {
            if (user != null && Objects.equals(uuid, user.getUuid())) {
                return user;
            }
        }
        throw new ResourceNotFoundException("User whit UUID: " + uuid + " not found");
    }

    public User[] getUsers() {
        return users;
    }

    public boolean userExists(UUID uuid) throws ResourceNotFoundException {
        for (User user : users) {
            if (user != null && Objects.equals(uuid, user.getUuid())) {
                return true;
            }
        }
        throw new ResourceNotFoundException("User whit UUID: " + uuid + " not found");
    }

}
