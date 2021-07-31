package com.example.payment_tracker.algorithm;

import com.example.payment_tracker.databases.UserDB;
import com.example.payment_tracker.models.Balance;
import com.example.payment_tracker.models.BalanceComparator;
import com.example.payment_tracker.models.Expense;
import com.example.payment_tracker.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class HeapGraphAlgorithm implements IGraphAlgorithm{
    public Map<String, Balance> userBalances;
    private Map<String, Double> cummulativeUserBalances;

    @Override
    public void init(List<User> userList) {
         this.cummulativeUserBalances = new HashMap<>();
         userList.forEach((user -> this.cummulativeUserBalances.put(user.id, 0.0)));
    }

    @Override
    public void addUser(User user) {
        cummulativeUserBalances.put(user.id, (double) 0);
    }

    @Override
    public void deleteUser(String userId) {
         cummulativeUserBalances.remove(userId);
    }

    @Override
    public void computeGraph(Expense expense) {
        // ADDING CURRENT EXPENSE TO CUMULATIVE SUM OF BALANCES
        Map<String, Double> currExpenseUserBalances = expense.getUserBalances();
        currExpenseUserBalances.forEach((userId, amount)->{
            double newAmount = amount + cummulativeUserBalances.get(userId);;
            cummulativeUserBalances.put(userId, newAmount);
        });

        /* USING TWO MAX HEAPS TO CALCULATE LEAST(NEARLY) NUMBER OF
           TRANSACTIONS REQUIRED TO SETTLE ALL EXPENSES */
        PriorityQueue<Balance> positiveHeap = new PriorityQueue<>
                (1, new BalanceComparator());
        PriorityQueue<Balance> negativeHeap = new PriorityQueue<>
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

            double amountTransferred = Math.min(pTop.amount, nTop.amount);
            double amountDiff = pTop.amount - nTop.amount;
            if(amountDiff > 0){
                positiveHeap.add(new Balance(pTop.userId, amountDiff));
            }else if(amountDiff < 0){
                negativeHeap.add(new Balance(nTop.userId, Math.abs(amountDiff)));
            }

            res.put(nTop.userId, new Balance(pTop.userId, amountTransferred));
        }

        this.userBalances = res;
    }

    @Override
    public void printGraph() {
        System.out.println("=====================");
        userBalances.forEach((userId, balance)->{
            User currUser = UserDB.getInstance().getUser(userId);
            User owedToUser = UserDB.getInstance().getUser(balance.userId);
            double amount = balance.amount;

            System.out.printf("%s OWES %s --> %.2f \n", currUser.name, owedToUser.name, amount);

        });
    }
}
