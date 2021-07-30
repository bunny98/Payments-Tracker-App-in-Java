package com.example.payment_tracker.models;

import com.example.payment_tracker.databases.UserDB;

import java.util.UUID;

public class User {
    public final String id;
    public final String name;

    public User(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }
}
