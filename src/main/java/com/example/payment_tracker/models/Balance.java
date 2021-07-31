package com.example.payment_tracker.models;

import java.util.Comparator;

public class Balance {
    public final String userId;
    public final double amount;

    public Balance(String userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }
}


