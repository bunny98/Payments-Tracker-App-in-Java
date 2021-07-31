package com.example.payment_tracker.databases;
import com.example.payment_tracker.models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupDB {
    private static GroupDB instance;
    private List<Group> groupList;

    private GroupDB(){
        this.groupList = new ArrayList<>();
    }

    public static GroupDB getInstance(){
        if(GroupDB.instance == null){
            GroupDB.instance = new GroupDB();
        }
        return GroupDB.instance;
    }

    public Group getGroup(String id){
        Group result = null;
        for(var group : groupList){
            if(group.id.equals(id)){
                result = group;
                break;
            }
        }
        return result;
    }

    public void addGroup(Group group){
        deleteGroup(group.id);
        groupList.add(group);
    }

    public void deleteGroup(String id){
        groupList.removeIf(group -> group.id.equals(id));
    }
}
