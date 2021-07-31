package com.example.payment_tracker.models;

import java.util.Comparator;

public class BalanceComparator implements Comparator<Balance> {

    @Override
    public int compare(Balance o1, Balance o2) {
        if (o1.amount < o2.amount)
            return 1;
        else if (o1.amount > o2.amount)
            return -1;
        return 0;
    }
}
