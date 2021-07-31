package com.example.payment_tracker.models;
import com.example.payment_tracker.algorithm.IGraphAlgorithm;

import java.util.*;

public class Group {
    private final IGraphAlgorithm graphAlgorithm;
    public final String id;
    public final String title;
    public List<User> users;

    public Group(IGraphAlgorithm graphAlgorithm, String title, List<User> users) {
        this.id = UUID.randomUUID().toString();
        this.graphAlgorithm = graphAlgorithm;
        this.title = title;
        this.users = users;
        this.graphAlgorithm.init(users);
    }

    public void addUser(User user){
        this.deleteUser(user.id);
        users.add(user);
        this.graphAlgorithm.addUser(user);
    }

    public void deleteUser(String userId){
        users.removeIf(user -> user.id.equals(userId));
        this.graphAlgorithm.deleteUser(userId);
    }

    public void computeGraph(Expense expense){
        this.graphAlgorithm.computeGraph(expense);
    }

    public void printGroupGraph(){
        this.graphAlgorithm.printGraph();
    }
}
