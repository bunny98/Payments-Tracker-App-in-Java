package com.example.payment_tracker;

import com.example.payment_tracker.databases.GroupDB;
import com.example.payment_tracker.databases.UserDB;
import com.example.payment_tracker.factories.ExpenseFactory;
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

		//CREATE USERS AND SAVE THEM IN THE USER DATABASE
		User user1 = new User("KAVYANSH");
		User user2 = new User("AKHIL");
		User user3 = new User("ANURAG");
		User user4 = new User("YASH");
		List<User> userList = new ArrayList<>(Arrays.asList(user1, user2, user3, user4));
		UserDB.getInstance().addBulk(userList);

		//CREATE GROUP AND SAVE IT IN GROUP DATABASE
		Group group = new Group("NEW GROUP", userList);
		GroupDB.getInstance().addGroup(group);

		//CREATE EXPENSES AND SEE HOW MUCH EACH PERSON OWES AT THE END OF EACH EXPENSE

		/* FOR THE GROUP NAMED 'NEW GROUP',
		THE USER 'USER1' MADE A PAYMENT OF 500 RS.,
		WHICH WILL BE DIVIDED AMONGST ALL THE MEMBERS OF THE GROUP EQUALLY. */
		ExpenseFactory.createObject(group, "TRAIN", user1.id, 500);
		group.printGroupGraph();

		ExpenseFactory.createObject(group, "FOOD", user2.id, 800);
		group.printGroupGraph();

		ExpenseFactory.createObject(group, "TRAVELLER", user3.id, 1500);
		group.printGroupGraph();

		ExpenseFactory.createObject(group, "MOJO PIZZA", user4.id, 900);
		group.printGroupGraph();
	}

}
