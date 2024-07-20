package com.cse360.Users;

public class Employee {
    protected String name;
    protected String id;

    Employee(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getName(){
        return name;
    }
}
