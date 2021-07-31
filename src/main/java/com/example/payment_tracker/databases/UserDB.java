package com.example.payment_tracker.databases;
import com.example.payment_tracker.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDB {
    private static UserDB instance;
    private List<User> userList;

    private UserDB(){
        this.userList = new ArrayList<>();
    }

    public static UserDB getInstance(){
        if(UserDB.instance == null){
            UserDB.instance = new UserDB();
        }
        return UserDB.instance;
    }

    public User getUser(String id){
        User res = null;
        for(var user: userList){
            if(user.id.equals(id)){
                res = user;
                break;
            }
        }
        return res;
    }

    public void addUser(User user){
        deleteUser(user.id);
        userList.add(user);
    }

    public void addBulk(List<User> userList){
        userList.forEach((user)->addUser(user));
    }

    public void deleteUser(String id){
        userList.removeIf(user -> user.id.equals(id));
    }

}
