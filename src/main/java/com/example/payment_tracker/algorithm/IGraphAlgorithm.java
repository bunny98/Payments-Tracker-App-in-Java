package com.example.payment_tracker.algorithm;

import com.example.payment_tracker.models.Expense;
import com.example.payment_tracker.models.User;

import java.util.List;

public interface IGraphAlgorithm {
    public void init(List<User> userList);
    public void addUser(User user);
    public void deleteUser(String userId);
    public void computeGraph(Expense expense);
    public void printGraph();
}
