package com.dragan.user;

import java.util.UUID;

public class User {
    private UUID uuid;
    private String name;

    public User() {
    }

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }
}
