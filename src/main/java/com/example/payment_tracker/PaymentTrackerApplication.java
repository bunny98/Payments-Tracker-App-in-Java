package com.example.payment_tracker;

import com.example.payment_tracker.databases.ExpensesDB;
import com.example.payment_tracker.databases.GroupDB;
import com.example.payment_tracker.databases.UserDB;
import com.example.payment_tracker.models.Expense;
import com.example.payment_tracker.models.Group;
import com.example.payment_tracker.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PaymentTrackerApplication {

	public static void main(String[] args) {
//		SpringApplication.run(PaymentTrackerApplication.class, args);

		//CREATE USERS
		User user1 = new User("KAVYANSH");
		User user2 = new User("AKHIL");
		User user3 = new User("ANURAG");
		User user4 = new User("YASH");
		List<User> userList = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));
		UserDB.getInstance().addBulk(userList);

		//CREATE GROUP
		Group group = new Group("NEW GROUP", userList);
		GroupDB.getInstance().addGroup(group);

		//CREATE EXPENSES AND SEE HOW MUCH EACH PERSON OWES AT THE END OF EACH EXPENSE
		Expense expense1 = new Expense(group, "TRAIN", user1.id, 500);
		ExpensesDB.getInstance().addExpense(expense1);
		group.calculateUserBalances();
		group.printGroupGraph();

		Expense expense2 = new Expense(group, "FOOD", user2.id, 800);
		ExpensesDB.getInstance().addExpense(expense2);
		group.calculateUserBalances();
		group.printGroupGraph();

		Expense expense3 = new Expense(group, "TRAVELLER", user3.id, 1500);
		ExpensesDB.getInstance().addExpense(expense3);
		group.calculateUserBalances();
		group.printGroupGraph();

		Expense expense4 = new Expense(group, "MOJO PIZZA", user4.id, 900);
		ExpensesDB.getInstance().addExpense(expense4);
		group.calculateUserBalances();
		group.printGroupGraph();
	}

}
