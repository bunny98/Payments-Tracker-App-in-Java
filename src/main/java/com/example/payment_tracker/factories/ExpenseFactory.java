package com.example.payment_tracker.factories;

import com.example.payment_tracker.databases.ExpensesDB;
import com.example.payment_tracker.models.Expense;
import com.example.payment_tracker.models.Group;

public class ExpenseFactory {
    private static final ExpensesDB expenseDB = ExpensesDB.getInstance();

    // CREATES AN EXPENSE OBJECT, SAVES IT IN EXPENSE DB AND RECALCULATES THE GROUP PAYMENT GRAPH
    public static Expense createObject(Group group, String title, String userId, int amount){
        Expense expense = new Expense(group.id, title, userId, amount);
        expenseDB.addExpense(expense);
        group.calculateUserBalances(expense);
        return expense;
    }
}
