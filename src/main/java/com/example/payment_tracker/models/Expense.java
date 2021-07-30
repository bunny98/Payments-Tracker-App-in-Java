package com.example.payment_tracker.models;

import com.example.payment_tracker.databases.ExpensesDB;
import com.example.payment_tracker.databases.GroupDB;

import java.util.*;

public class Expense {
    public final String id;
    public final String groupId;
    public final String title;
    private final Map<String, Double> userBalances;

    public Map<String, Double> getUserBalances() {
        return userBalances;
    }

    public Expense(Group group, String title, String userId, Integer amount) {
        this.id = UUID.randomUUID().toString();
        this.groupId = group.id;
        this.title = title;
        this.userBalances = calculateUserBalances(group.id, userId, amount);
        ExpensesDB.getInstance().addExpense(this);
        group.calculateUserBalances();
    }

    private Map<String, Double> calculateUserBalances(String groupId, String userId, Integer amount){
        List<User> userList = GroupDB.getInstance().getGroup(groupId).users;
        double dividedAmount = -1 * (double) amount/userList.size();
        List<Double> balanceAmounts = new ArrayList<>();
        for(int i = 0; i<userList.size(); ++i){
            double currAmt = dividedAmount;
            if(userList.get(i).id.equals(userId)){
                currAmt += amount;
            }
            balanceAmounts.add(currAmt);
        }
        Map<String, Double> userBalances = new HashMap<>();
        for(int i = 0; i<userList.size(); ++i){
            userBalances.put(userList.get(i).id, balanceAmounts.get(i));
        }
        return userBalances;
    }


}
