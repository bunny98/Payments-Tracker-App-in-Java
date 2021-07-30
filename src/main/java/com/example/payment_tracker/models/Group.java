package com.example.payment_tracker.models;

import com.example.payment_tracker.databases.ExpensesDB;
import com.example.payment_tracker.databases.GroupDB;
import com.example.payment_tracker.databases.UserDB;

import java.util.*;

public class Group {
    public final String id;
    public final String title;
    public List<User> users;
    public Map<String, Balance> userBalances;

    public Group(String title, List<User> users) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.users = users;
    }

    public void addUser(User user){
        this.deleteUser(user.id);
        users.add(user);
    }

    public void deleteUser(String userId){
        users.removeIf(user -> user.id.equals(userId));
    }

    public void calculateUserBalances(){
        List<Expense> expenseList = ExpensesDB.getInstance().getGroupExpenses(this.id);
        Map<String, Double> cummulativeUserBalances = new HashMap<>();
        for(var expense: expenseList){
            Map<String, Double> currExpenseUserBalances = expense.getUserBalances();
            currExpenseUserBalances.forEach((userId, amount)->{
                double newAmount = amount;
                if(cummulativeUserBalances.containsKey(userId)){
                    newAmount += cummulativeUserBalances.get(userId);
                }
                cummulativeUserBalances.put(userId, newAmount);
            });
        }
        calculateGroupGraph(cummulativeUserBalances);
    }

    private void calculateGroupGraph(Map<String, Double> cummulativeUserBalances){
        PriorityQueue<Balance> positiveHeap = new PriorityQueue<Balance>
                                        (1, new BalanceComparator());
        PriorityQueue<Balance> negativeHeap = new PriorityQueue<Balance>
                                        (1, new BalanceComparator());

        cummulativeUserBalances.forEach((key, value)->{
            if(value < 0){
                negativeHeap.add(new Balance(key, Math.abs(value)));
            }else{
                positiveHeap.add(new Balance(key, Math.abs(value)));
            }
        });

        Map<String, Balance> res = new HashMap<>();
        while(!positiveHeap.isEmpty() && !negativeHeap.isEmpty()){
            Balance pTop = positiveHeap.poll();
            Balance nTop = negativeHeap.poll();

            res.put(nTop.userId, new Balance(pTop.userId, nTop.amount));

            if(pTop.amount > nTop.amount){
                positiveHeap.add(new Balance(pTop.userId, pTop.amount - nTop.amount));
            }
        }

        this.userBalances = res;
    }

    public void printGroupGraph(){
        userBalances.forEach((userId, balance)->{
            User currUser = UserDB.getInstance().getUser(userId);
            User owedToUser = UserDB.getInstance().getUser(balance.userId);
            double amount = balance.amount;

            System.out.printf("%s OWES %s AMOUNT: %f \n", currUser.name, owedToUser.name, amount);

        });
        System.out.println("=====================");
    }
}
