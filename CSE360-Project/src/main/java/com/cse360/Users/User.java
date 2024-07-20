package com.cse360.Users;

import java.util.ArrayList;

import com.cse360.Order;

public class User {
    public enum USERTYPE {
        STUDENT, CHEF, OPA
    }
    public String ID, NAME;
    public USERTYPE TYPE;

    public User(String id, String name, USERTYPE type){
        this.ID = id;
        this.TYPE = type;
        this.NAME = name;
    }
}
