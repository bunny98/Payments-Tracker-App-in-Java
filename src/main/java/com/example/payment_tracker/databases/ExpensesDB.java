package com.example.payment_tracker.databases;

import com.example.payment_tracker.models.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpensesDB {
    private static ExpensesDB instance;
    private List<Expense> expenseList;

    private ExpensesDB(){
        this.expenseList = new ArrayList<>();
    }

    public static ExpensesDB getInstance(){
        if(ExpensesDB.instance == null){
            ExpensesDB.instance = new ExpensesDB();
        }
        return ExpensesDB.instance;
    }

    public void addExpense(Expense expense){
        deleteExpense(expense.id);
        expenseList.add(expense);
    }

    public void deleteExpense(String id){
        expenseList.removeIf(expense -> expense.id.equals(id));
    }

    public List<Expense> getGroupExpenses(String groupId){
        List<Expense> result = new ArrayList<>();
        for(var expense : expenseList) {
            if (expense.groupId.equals(groupId)) {
                result.add(expense);
            }
        }
        return result;
    }

}
